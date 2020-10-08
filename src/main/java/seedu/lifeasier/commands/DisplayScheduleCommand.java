package seedu.lifeasier.commands;

import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Event;
import seedu.lifeasier.tasks.Lesson;
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
            System.out.println(getDayOfWeek(i));
            displayDaySchedule(startOfWeek.plus(i, ChronoUnit.DAYS), tasks);
            System.out.println();
        }
    }

    public static void displayDaySchedule(LocalDate date, TaskList tasks) {
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task t = tasks.getTask(i);
            LocalDateTime startDateTime = getStart(t);
            LocalDateTime endDateTime = getEnd(t);
            if (startDateTime.toLocalDate().equals(date)) {
                printWithScheduleFormat(t, startDateTime, endDateTime);
            }
        }
    }

    public static void printWithScheduleFormat(Task t, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String startDateTimeString = getTimeStamp(startDateTime);
        String endDateTimeString = (endDateTime == null) ? "" : ("-" + getTimeStamp(startDateTime));
        System.out.println(startDateTimeString + endDateTimeString + "   " + t.toScheduleFormatString());
    }

    private static LocalDateTime getStart(Task t) {
        if (t instanceof Lesson) {
            return ((Lesson) t).getStart();
        } else if (t instanceof Event) {
            return ((Event) t).getStart();
        }
        return ((Deadline) t).getBy();
    }

    private static LocalDateTime getEnd(Task t) {
        if (t instanceof Lesson) {
            return ((Lesson) t).getEnd();
        } else if (t instanceof Event) {
            return ((Event) t).getEnd();
        }
        return null;
    }

    public static String getTimeStamp(LocalDateTime timedItem) {
        return timedItem.toLocalTime().toString();
    }

    public static DayOfWeek getDayOfWeek(int dayCount) {
        LocalDateTime dateTime = LocalDateTime.now().plus(dayCount, ChronoUnit.DAYS);
        return dateTime.getDayOfWeek();
    }
}
