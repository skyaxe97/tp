package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.ScheduleUi;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;

public class DisplayScheduleCommand extends Command {

    private boolean isDisplayWeek;
    private final ScheduleUi scheduleUi = new ScheduleUi();

    private static final String NO_TASKS_TODAY_MESSAGE = "You have nothing on for today!";

    public DisplayScheduleCommand(String toDisplay) {
        this.isDisplayWeek = toDisplay.equals("week");
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        LocalDate currDate = LocalDate.now();

        if (isDisplayWeek) {
            ui.printBlankLine();
            ui.printThickSeparator();
            scheduleUi.showHome(tasks);
            ui.printThickSeparator();
            ui.printBlankLine();

        } else {
            int taskCountForToday = scheduleUi.getTaskCountForToday(tasks, currDate);
            if (taskCountForToday > 0) {
                System.out.println("Here is your schedule for today:");
                scheduleUi.displayDaySchedule(currDate, tasks);
                ui.printThickSeparator();
                ui.printBlankLine();
            } else {
                ui.showNothingScheduledTodayMessage();
            }
        }
    }
}
