package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteHistory;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskHistory;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

public class InvalidCommand extends Command {

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {

    }
}
