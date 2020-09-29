package seedu.lifeasier;
import seedu.notes.*;

/**
 * The Command class will handle all the commands input from the user
 */
public class Command {

    private String commandType;
    private boolean isTerminated;
    private NoteList notes;

    public Command(String commandType) {
        this.commandType = commandType;
        this.isTerminated = false;
    }

    /**
     * Executes the user command
     */
    public void execute(Ui ui) {
        switch (commandType) {
        case "addNotes":
            ui.showNoteTitleMessage();
            String noteTitle = ui.readCommand();
            ui.showNoteDescriptionMessage();
            String noteDescription = ui.readCommand();
            notes.addNotes(new Note(noteTitle,noteDescription));

        case "exit":
            setExit();
            break;
        default:
            ui.showInvalidCommandError();
            break;
        }
    }

    public void setExit() {
        isTerminated = true;
    }

    /**
     * Returns status of whether the program has been terminated
     */
    public boolean isFinished() {
        return isTerminated;
    }
}
