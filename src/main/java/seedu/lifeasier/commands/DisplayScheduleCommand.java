package seedu.lifeasier.commands;

import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

public class DisplayScheduleCommand extends Command {

    private boolean isDisplayWeek;

    public DisplayScheduleCommand(String toDisplay) {
        this.isDisplayWeek = toDisplay.equals("week");
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks) {

    }
}
