package seedu.lifeasier.commands;

import seedu.lifeasier.notes.EmptyNoteListException;
import seedu.lifeasier.notes.TitleNotFoundException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.notes.NoteCommandFunctions;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditNotesCommand extends Command {
    private static Logger logger = Logger.getLogger(EditNotesCommand.class.getName());
    private String title;

    public EditNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, String title) throws TitleNotFoundException {
        logger.log(Level.INFO, "Start for finding title in note list");
        int noteNumber = -1;
        int matchNumber = 0;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                matchNumber++;
                noteNumber = i;
            }
        }
        logger.log(Level.INFO, "End for finding title in note list");

        switch (matchNumber) {
        case 0:     // no matches
            logger.log(Level.SEVERE, "No matches for title");
            throw new TitleNotFoundException();
        case 1:
            logger.log(Level.INFO, "One match found");
            System.out.println(notes.get(noteNumber).toString());
            ui.showConfirmEditMessage();
            parseUserResponse(ui, notes, noteNumber, ui.readCommand());
            break;
        default:
            logger.log(Level.INFO, "Multiple matches found");
            ui.showMultipleMatchesFoundMessage();

            logger.log(Level.INFO, "Start of printing all matching notes");
            NoteCommandFunctions.printMultipleMatches(notes, title);
            logger.log(Level.INFO, "End of printing all matching notes");

            noteNumber = Integer.parseInt(ui.readCommand()) - 1;
            NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);

            System.out.println(notes.get(noteNumber).toString());
            ui.showConfirmEditMessage();
            parseUserResponse(ui, notes, noteNumber, ui.readCommand());
        }

    }

    private void parseUserResponse(Ui ui, NoteList notes, int noteNumber, String input) {
        logger.log(Level.INFO, "Start check for Y/N input");
        while (!input.trim().equals("Y") && !input.trim().equals("N")) {
            ui.showInvalidConfirmationMessage();
            input = ui.readCommand();
        }
        logger.log(Level.INFO, "End check for Y/N input");

        if (input.trim().equals("Y")) {
            logger.log(Level.INFO, "Y is inputted");
            ui.showEditWhichPartMessage();
            while (!input.trim().equals("T") && !input.trim().equals("D")) {
                ui.showInvalidTitleDescriptionConfirmationMessage();
                input = ui.readCommand();
            }
            changeTitleOrDescription(ui, notes, noteNumber, input);
        } else {
            logger.log(Level.INFO, "N is inputted");
            ui.showNoteNotEditedMessage();
        }

    }

    private void changeTitleOrDescription(Ui ui, NoteList notes, int noteNumber, String input) {
        if (input.trim().equals("T")) {
            logger.log(Level.INFO, "T is inputted");
            System.out.println("Current Title: " + notes.get(noteNumber).getTitle());
            ui.showEditTitleMessage();
            input = ui.readCommand();
            notes.get(noteNumber).setTitle(input);
            logger.log(Level.INFO, "Title is changed");
            System.out.println("OK! Your title is now: " + notes.get(noteNumber).getTitle());
            ui.printSeparator();
        } else {
            logger.log(Level.INFO, "D is inputted");
            System.out.println("Current description:\n" + notes.get(noteNumber).getDescription());
            ui.showEditDescriptionMessage();
            input = ui.readCommand();
            notes.get(noteNumber).setDescription(input);
            logger.log(Level.INFO, "Description is changed");
            System.out.println("OK! Your description is now: " + notes.get(noteNumber).getDescription());
            ui.printSeparator();
        }
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        try {
            logger.log(Level.INFO, "Start of EditNotesCommand");
            ui.printSeparator();
            NoteCommandFunctions.checkEmptyList(notes);
            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, title);
            } else {
                ui.showSelectWhichNoteToEditMessage();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                NoteCommandFunctions.printAllNotes(notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());
                NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);

                System.out.println(notes.get(noteNumber - 1).toString());
                ui.showConfirmEditMessage();
                parseUserResponse(ui, notes, noteNumber - 1, ui.readCommand());

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
