package seedu.lifeasier.commands;
import seedu.lifeasier.Ui;
import seedu.lifeasier.notes.NoteList;


/**
 * The Command class will handle all the commands input from the user
 */
public abstract class Command {

    protected boolean isTerminated;

    public Command() {
        this.isTerminated = false;
    }

    public Command(String commandType) {
        this.isTerminated = false;
    }

    /**
     * Executes the user command
     */
    public abstract void execute(Ui ui, NoteList notes);

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
