package seedu.lifeasier.commands;

import seedu.lifeasier.notes.EmptyNoteListException;
import seedu.lifeasier.notes.TitleNotFoundException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.notes.NoteCommandFunctions;
import seedu.lifeasier.parser.Parser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditNotesCommand extends Command {
    private static Logger logger = Logger.getLogger(EditNotesCommand.class.getName());
    private String title;

    public EditNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, Parser parser, String title) throws TitleNotFoundException {
        logger.log(Level.INFO, "Start for finding title in note list");

        int matchNumber = NoteCommandFunctions.checkNumberOfNoteMatches(notes, title);
        int noteNumber = NoteCommandFunctions.findNoteNumber(notes, title);


        logger.log(Level.INFO, "End for finding title in note list");

        switch (matchNumber) {
        case 0:     // no matches
            logger.log(Level.SEVERE, "No matches for title");
            throw new TitleNotFoundException();
        case 1:
            logger.log(Level.INFO, "One match found");
            System.out.println(notes.get(noteNumber).toString());
            ui.showConfirmEditMessage();
            editNote(ui, parser, notes, noteNumber, ui.readCommand());
            break;
        default:
            logger.log(Level.INFO, "Multiple matches found");
            ui.showMultipleMatchesFoundMessage();

            logger.log(Level.INFO, "Start of printing all matching notes");
            ui.printMultipleNoteMatches(notes, title);
            logger.log(Level.INFO, "End of printing all matching notes");

            noteNumber = Integer.parseInt(ui.readCommand()) - 1;
            NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);

            System.out.println(notes.get(noteNumber).toString());
            ui.showConfirmEditMessage();
            editNote(ui, parser, notes, noteNumber, ui.readCommand());
        }

    }

    private void editNote(Ui ui, Parser parser, NoteList notes, int noteNumber, String input) {
        if (parser.parseUserInputYesOrNo(input, ui).equals("Y")) {
            logger.log(Level.INFO, "Y is inputted");
            ui.showEditWhichPartMessage();
            input = parser.parseUserInputTOrD(input, ui);
            changeTitleOrDescription(ui, parser, notes, noteNumber, input);
        } else {
            logger.log(Level.INFO, "N is inputted");
            ui.showNoteNotEditedMessage();
        }

    }

    private void changeTitleOrDescription(Ui ui, Parser parser, NoteList notes, int noteNumber, String input) {
        if (input.trim().equals("T")) {
            logger.log(Level.INFO, "T is inputted");
            System.out.println("Current Title: " + notes.get(noteNumber).getTitle());
            ui.showEditTitleMessage();
            input = parser.checkIfEmpty(ui, ui.readCommand());
            notes.get(noteNumber).setTitle(input);
            logger.log(Level.INFO, "Title is changed");
            System.out.println("OK! Your title is now: " + notes.get(noteNumber).getTitle());
        } else {
            logger.log(Level.INFO, "D is inputted");
            System.out.println("Current description:\n" + notes.get(noteNumber).getDescription());
            ui.showEditDescriptionMessage();
            input = parser.checkIfEmpty(ui, ui.readCommand());
            notes.get(noteNumber).setDescription(input);
            logger.log(Level.INFO, "Description is changed");
            System.out.println("OK! Your description is now: " + notes.get(noteNumber).getDescription());
        }
        ui.printSeparator();
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {
        try {
            logger.log(Level.INFO, "Start of EditNotesCommand");
            ui.printSeparator();
            NoteCommandFunctions.checkEmptyList(notes);
            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, parser, title);
            } else {
                ui.showSelectWhichNoteToEditMessage();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                ui.printAllNotes(notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());
                NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);

                System.out.println(notes.get(noteNumber - 1).toString());
                ui.showConfirmEditMessage();
                editNote(ui, parser, notes, noteNumber - 1, ui.readCommand());

            }
            storage.saveNote();
            logger.log(Level.INFO, "End of EditNotesCommand");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showInvalidNumberMessage();
        } catch (TitleNotFoundException e) {
            logger.log(Level.SEVERE, "Input title does not match any of the note titles");
            ui.showNoTitleFoundMessage();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatMessage();
        } catch (EmptyNoteListException e) {
            ui.showEmptyNoteListMessage();
        }
    }
}
