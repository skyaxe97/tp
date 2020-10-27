package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteCommandFunctions;
import seedu.lifeasier.model.notes.EmptyNoteListException;
import seedu.lifeasier.model.notes.TitleNotFoundException;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditNotesCommand extends Command {
    private static Logger logger = Logger.getLogger(EditNotesCommand.class.getName());
    private String title;

    public EditNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, Parser parser,
                           String title, NoteHistory noteHistory) throws TitleNotFoundException {
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

            ui.showEditWhichPartMessage();
            changeTitleOrDescription(ui, parser, notes, noteNumber, ui.readCommand(), noteHistory);
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
            ui.showEditWhichPartMessage();

            changeTitleOrDescription(ui, parser, notes, noteNumber, ui.readCommand(), noteHistory);
        }

    }

    private void changeTitleOrDescription(Ui ui, Parser parser, NoteList notes, int noteNumber, String input,
                                          NoteHistory noteHistory) {
        logger.log(Level.INFO, "Temporarily hold details of this Note");

        input = parser.parseUserInputTOrD(input, ui);
        Note oldCopyOfNote = noteHistory.getCurrCopyOfNoteToEdit(notes, noteNumber);
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

        noteHistory.pushOldCopy(oldCopyOfNote, ui);
        logger.log(Level.INFO, "Push old copy of Note into noteHistory");

        ui.printSeparator();
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            logger.log(Level.INFO, "Start of EditNotesCommand");
            ui.printSeparator();
            NoteCommandFunctions.checkEmptyList(notes);
            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, parser, title, noteHistory);
            } else {
                ui.showSelectWhichNoteToEditMessage();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                ui.printAllNotes(notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());
                NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);

                System.out.println(notes.get(noteNumber - 1).toString());
                ui.showEditWhichPartMessage();
                changeTitleOrDescription(ui, parser, notes, noteNumber - 1, ui.readCommand(), noteHistory);

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
