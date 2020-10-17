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

public class ShowNotesCommand extends Command {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    private String title;

    public ShowNotesCommand(String title) {
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

        switch (matchNumber) {
        case 0:     // no matches
            logger.log(Level.SEVERE, "No matches for title");
            throw new TitleNotFoundException();
        case 1:
            logger.log(Level.INFO, "One match found");
            System.out.println(notes.get(noteNumber).toString());
            break;
        default:
            logger.log(Level.INFO, "Multiple matches found");
            ui.showMultipleMatchesFoundMessage();

            logger.log(Level.INFO, "Start of printing all matching notes");
            NoteCommandFunctions.printMultipleMatches(notes, title);
            logger.log(Level.INFO, "End of printing all matching notes");

            noteNumber = Integer.parseInt(ui.readCommand());
            NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);
            System.out.println(notes.get(noteNumber - 1).toString());
        }
        logger.log(Level.INFO, "End for finding title in note list");
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {
        try {
            logger.log(Level.INFO, "Start of ShowNotesCommand");
            ui.printSeparator();
            NoteCommandFunctions.checkEmptyList(notes);
            if (title.trim().length() > 0) {        // title is already inputted
                findTitle(ui, notes, title);
            } else {
                ui.showSelectWhichNoteToViewMessage();

                logger.log(Level.INFO, "Start of printing all notes in the list");
                NoteCommandFunctions.printAllNotes(notes);
                logger.log(Level.INFO, "End of printing all notes in the list");

                int noteNumber = Integer.parseInt(ui.readCommand());

                NoteCommandFunctions.checkForIndexOutOfBounds(notes, noteNumber);
                System.out.println(notes.get(noteNumber - 1).toString());

            }
            ui.printSeparator();
            logger.log(Level.INFO, "End of ShowNotesCommand");
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
            logger.log(Level.SEVERE, "Note List is empty");
            ui.showEmptyNoteListMessage();
        }
    }

}
