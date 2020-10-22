package seedu.lifeasier.commands;

import seedu.lifeasier.notes.EmptyNoteListException;
import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteCommandFunctions;
import seedu.lifeasier.notes.NoteHistory;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.notes.TitleNotFoundException;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskHistory;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteNotesCommand extends Command {

    private static Logger logger = Logger.getLogger(DeleteNotesCommand.class.getName());
    private String title;
    private boolean isEmpty = true;

    public DeleteNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, String title, NoteHistory noteHistory) throws TitleNotFoundException {
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
            ui.showConfirmDeleteMessage();
            String input = checkConfirmationMessage(ui, ui.readCommand());
            checkIfDelete(ui, notes, noteNumber, input, noteHistory);
            break;
        default:
            logger.log(Level.INFO, "Multiple matches found");
            ui.showMultipleMatchesFoundMessage();

            logger.log(Level.INFO, "Start of printing all matching notes");
            NoteCommandFunctions.printMultipleMatches(ui, notes, title);
            logger.log(Level.INFO, "End of printing all matching notes");

            noteNumber = Integer.parseInt(ui.readCommand());
            NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);
            System.out.println(notes.get(noteNumber - 1).toString());

            ui.showConfirmDeleteMessage();
            input = checkConfirmationMessage(ui, ui.readCommand());
            checkIfDelete(ui, notes, noteNumber, input, noteHistory);
        }

    }

    private void checkIfDelete(Ui ui, NoteList notes, int noteNumber, String input, NoteHistory noteHistory) {
        if (input.trim().equals("Y")) {
            Note oldCopyOfNote = noteHistory.getCurrCopyOfNoteToDelete(notes, noteNumber);
            logger.log(Level.INFO, "Temporarily hold details of this Note");

            notes.remove(noteNumber);
            ui.showNoteDeletedMessage();

            noteHistory.pushOldCopy(oldCopyOfNote, ui);
            logger.log(Level.INFO, "Push old copy of Note into noteHistory");
        } else {
            ui.showNoteNotDeletedMessage();
        }
    }

    private String checkConfirmationMessage(Ui ui, String input) {
        logger.log(Level.INFO, "Start check for Y/N input");
        while (!input.trim().equals("Y") && !input.trim().equals("N")) {
            ui.showInvalidConfirmationMessage();
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
            ui.printSeparator();
            NoteCommandFunctions.checkEmptyList(notes);
            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, title, noteHistory);
            } else {
                ui.showSelectWhichNoteToDeleteMessage();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                NoteCommandFunctions.printAllNotes(ui, notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());
                NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);
                System.out.println(notes.get(noteNumber - 1).toString());

                ui.showConfirmDeleteMessage();
                String input = checkConfirmationMessage(ui, ui.readCommand());
                checkIfDelete(ui, notes, noteNumber -  1, input, noteHistory);

                storage.saveNote();
            }
            ui.printSeparator();
            logger.log(Level.INFO, "End of DeleteNotesCommand");
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
