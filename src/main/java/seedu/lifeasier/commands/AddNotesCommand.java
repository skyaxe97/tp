package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddNotesCommand extends Command {

    private static Logger logger = Logger.getLogger(AddNotesCommand.class.getName());
    private String title;
    private boolean isEmpty = true;


    public AddNotesCommand(String title) {
        this.title = title;
    }

    public String checkForEmpty(Ui ui) {
        logger.log(Level.INFO, "Start for checking empty description inputs");
        String input = ui.readCommand();
        if (input.trim().length() > 0) {
            isEmpty = false;
        } else {
            ui.showEmptyDescriptionError();
        }
        logger.log(Level.INFO, "End of checking empty description inputs");
        return input;
    }

    private String isValidTitle(Ui ui, String title) {
        String noteTitle = null;

        if (title.trim().length() > 0) {        // title is already inputted
            isEmpty = false;
            noteTitle = title;
        } else {
            ui.showNoteTitlePrompt();
            while (isEmpty) {
                noteTitle = checkForEmpty(ui);
            }
        }
        return noteTitle;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        logger.log(Level.INFO, "Start of AddNotesCommand");
        String noteDescription = null;
        logger.log(Level.INFO, "Start for assigning noteTitle");
        ui.printThickSeparator();
        final String noteTitle = isValidTitle(ui, title);
        logger.log(Level.INFO, "End for assigning noteTitle");

        isEmpty = true;
        logger.log(Level.INFO, "IsEmpty set back to true");
        ui.showNoteDescriptionPrompt();

        logger.log(Level.INFO, "Start for assigning noteDescription");
        while (isEmpty) {
            noteDescription = checkForEmpty(ui);
        }
        logger.log(Level.INFO, "End for assigning noteDescription");

        notes.add(new Note(noteTitle,noteDescription));
        logger.log(Level.INFO, "Note is added");
        ui.showNoteAddedMessage();
        ui.printThickSeparator();

        storage.saveNote();
    }

}
