package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.EmptyNoteListException;
import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteCommandFunctions;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.notes.TitleNotFoundException;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteNotesCommand extends Command {

    private static Logger logger = Logger.getLogger(DeleteNotesCommand.class.getName());
    private String title;
    private boolean isEmpty = true;
    private int arraySize = 10000;

    public DeleteNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, String title, NoteHistory noteHistory) throws TitleNotFoundException {
        logger.log(Level.INFO, "Start for finding title in note list");
        int[] noteMatches = new int [arraySize];
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
            ui.showConfirmDeletePrompt();
            String input = checkConfirmationMessage(ui, ui.readCommand());
            checkIfDelete(ui, notes, noteNumber, input, noteHistory);
            break;
        default:
            logger.log(Level.INFO, "Multiple matches found");
            ui.showMultipleMatchesFoundPrompt();

            logger.log(Level.INFO, "Start of printing all matching notes");
            noteMatches = ui.showMultipleNoteMatchesMessage(notes, title, noteMatches);
            logger.log(Level.INFO, "End of printing all matching notes");

            noteNumber = Integer.parseInt(ui.readCommand()) - 1;
            NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber + 1, noteMatches);
            System.out.println(notes.get(noteNumber).toString());

            ui.showConfirmDeletePrompt();
            input = checkConfirmationMessage(ui, ui.readCommand());
            checkIfDelete(ui, notes, noteNumber, input, noteHistory);
        }

    }

    private void checkIfDelete(Ui ui, NoteList notes, int noteNumber, String input, NoteHistory noteHistory) {
        if (input.trim().equals("Y")) {
            logger.log(Level.INFO, "Temporarily hold details of this Note");
            Note oldCopyOfNote = noteHistory.getCurrCopyOfNoteToDelete(notes, noteNumber);
            notes.remove(noteNumber);
            ui.showNoteDeletedMessage();
            noteHistory.pushOldCopy(oldCopyOfNote);
            logger.log(Level.INFO, "Push old copy of Note into noteHistory");
        } else {
            ui.showNoteNotDeletedMessage();
        }
    }

    private String checkConfirmationMessage(Ui ui, String input) {
        logger.log(Level.INFO, "Start check for Y/N input");
        while (!input.trim().equals("Y") && !input.trim().equals("N")) {
            ui.showInvalidConfirmationPrompt();
            input = ui.readCommand();
        }
        logger.log(Level.INFO, "End check for Y/N input");
        return input;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            logger.log(Level.INFO, "Start of DeleteNotesCommand");
            ui.printThickSeparator();
            NoteCommandFunctions.checkEmptyList(notes);
            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, title, noteHistory);
            } else {
                ui.showSelectWhichNoteToDeletePrompt();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                ui.showAllNotesMessage(notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());
                NoteCommandFunctions.checkForIndexBeyondSize(notes, noteNumber);
                System.out.println(notes.get(noteNumber - 1).toString());

                ui.showConfirmDeletePrompt();
                String input = checkConfirmationMessage(ui, ui.readCommand());
                checkIfDelete(ui, notes, noteNumber -  1, input, noteHistory);

            }
            storage.saveNote();
            ui.printThickSeparator();
            logger.log(Level.INFO, "End of DeleteNotesCommand");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showInvalidNumberError();
        } catch (TitleNotFoundException e) {
            logger.log(Level.SEVERE, "Input title does not match any of the note titles");
            ui.showNoTitleFoundError();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatError();
        } catch (EmptyNoteListException e) {
            ui.showEmptyNoteListMessage();
        }
    }
}
