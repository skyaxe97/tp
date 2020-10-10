package seedu.lifeasier.commands;

import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

public class HelpCommand extends Command {

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks) {
        ui.showHelp();
    }
}
