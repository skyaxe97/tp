package seedu.lifeasier.commands;

import seedu.lifeasier.Ui;
import seedu.lifeasier.notes.NoteList;

public class ExitCommand extends Command {

    public ExitCommand() {
        this.isTerminated = true;
    }

    @Override
    public void execute(Ui ui, NoteList notes) {

    }
}
