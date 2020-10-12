package seedu.lifeasier.commands;

import seedu.lifeasier.exceptions.TitleNotFoundException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

/**
 * The Command class will handle all the commands input from the user.
 */
public abstract class Command {

    protected boolean isTerminated;

    public Command() {
        this.isTerminated = false;
    }

    /**
     * Executes the user command.
     */
    public abstract void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) throws
            TitleNotFoundException;

    /**
     * Returns status of whether the program has been terminated.
     */
    public boolean isFinished() {
        return isTerminated;
    }
}
