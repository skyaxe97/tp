package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.EmptyNoteListException;
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

public class ShowNotesCommand extends Command {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    private String title;
    private int arraySize = 10000;

    public ShowNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, String title) throws TitleNotFoundException {
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
            ui.showNotesMessage(notes.get(noteNumber).toString());
            break;
        default:
            logger.log(Level.INFO, "Multiple matches found");
            ui.showMultipleMatchesFoundPrompt();

            logger.log(Level.INFO, "Start of printing all matching notes");
            ui.showMultipleNoteMatchesMessage(notes, title);
            noteMatches = NoteCommandFunctions.storeNoteMatches(notes, title, noteMatches);
            logger.log(Level.INFO, "End of printing all matching notes");

            noteNumber = Integer.parseInt(ui.readCommand()) - 1;
            NoteCommandFunctions.checkForIndexOutOfBounds(noteNumber + 1, noteMatches);
            ui.showNotesMessage(notes.get(noteNumber).toString());
        }

    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            logger.log(Level.INFO, "Start of ShowNotesCommand");
            NoteCommandFunctions.checkEmptyList(notes);

            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, title);

            } else {
                ui.showSelectWhichNoteToViewPrompt();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                ui.showAllNotesMessage(notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());

                NoteCommandFunctions.checkForIndexBeyondSize(notes, noteNumber);
                ui.showNotesMessage(notes.get(noteNumber - 1).toString());
            }
            logger.log(Level.INFO, "End of ShowNotesCommand");

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
            logger.log(Level.SEVERE, "Note List is empty");
            ui.showEmptyNoteListMessage();
        }
    }

}
