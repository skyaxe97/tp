package seedu.lifeasier.commands;

import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DisplayScheduleCommand extends Command {

    private boolean isDisplayWeek;

    public DisplayScheduleCommand(String toDisplay) {
        this.isDisplayWeek = toDisplay.equals("week");
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks) {
        LocalDate currDate = LocalDate.now();
        if (isDisplayWeek) {
            displayWeekSchedule(currDate, tasks);
        } else {
            displayDaySchedule(currDate, tasks);
        }
    }

    public static void displayWeekSchedule(LocalDate startOfWeek, TaskList tasks) {
        for (int i = 0; i < 7; i++) {
            System.out.println(getCurrDayOfWeek(i));
            displayDaySchedule(startOfWeek.plus(i, ChronoUnit.DAYS), tasks);
            System.out.println();
        }
    }

    public static void displayDaySchedule(LocalDate date, TaskList tasks) {
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task t = tasks.getTask(i);
            if (t.getDateTime().toLocalDate().equals(date)) {
                System.out.println(getTimeStamp(t.getDateTime()) + " " + t.toString());
            }
        }
    }

    public static String getTimeStamp(LocalDateTime timedItem) {
        return timedItem.toLocalTime().toString();
    }

    public static DayOfWeek getCurrDayOfWeek(int dayCount) {
        LocalDateTime currDay = LocalDateTime.now().plus(dayCount, ChronoUnit.DAYS);
        return currDay.getDayOfWeek();
    }
}
