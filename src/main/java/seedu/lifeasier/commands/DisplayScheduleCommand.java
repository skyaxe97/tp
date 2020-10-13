package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.timetable.Timetable;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.ui.ScheduleUi;
import seedu.lifeasier.notes.NoteList;

import java.time.LocalDate;

public class DisplayScheduleCommand extends Command {

    private boolean isDisplayWeek;
    private final ScheduleUi scheduleUi = new ScheduleUi();
    private final Timetable time = new Timetable();

    public DisplayScheduleCommand(String toDisplay) {
        this.isDisplayWeek = toDisplay.equals("week");
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        LocalDate currDate = LocalDate.now();
        if (isDisplayWeek) {
            scheduleUi.displayWeekSchedule(currDate, tasks);
        } else {
//            if (scheduleUi.getTaskCountForToday(tasks, currDate) == 0) {
//                System.out.println("You have nothing on for today!");
//            } else {
//                System.out.println("Here is your schedule for today:");
//                scheduleUi.displayDaySchedule(currDate, tasks);
//            }
            time.showHome(tasks);
        }
    }
}
