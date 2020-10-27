package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.model.notes.NoteList;

public class ExitCommand extends Command {

    public ExitCommand() {
        this.isTerminated = true;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {

    }
}
