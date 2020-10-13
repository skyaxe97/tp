package seedu.lifeasier.ui;

import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.timetable.Timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ScheduleUi {
    public void displayWeekSchedule(LocalDate startOfWeek, TaskList tasks) {
        for (int i = 0; i < 7; i++) {
            Timetable.displayDayOfWeek(i);
            displayDaySchedule(startOfWeek.plus(i, ChronoUnit.DAYS), tasks);
            System.out.println();
        }
    }

    public void displayDaySchedule(LocalDate date, TaskList tasks) {
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task t = tasks.getTask(i);
            LocalDateTime startDateTime = t.getStart();
            LocalDateTime endDateTime = t.getEnd();
            if (startDateTime.toLocalDate().equals(date)) {
                printWithScheduleFormat(t, startDateTime, endDateTime);
            }
        }
    }

    public void printWithScheduleFormat(Task t, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String startDateTimeString = getTimeStamp(startDateTime);
        String endDateTimeString = (endDateTime == null) ? "      " : ("-" + getTimeStamp(endDateTime));
        System.out.println(startDateTimeString + endDateTimeString + "  " + t.getDescription());
    }

    public String getTimeStamp(LocalDateTime timedItem) {
        return timedItem.toLocalTime().toString();
    }

    public int getTaskCountForToday(TaskList tasks, LocalDate date) {
        int count = 0;
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task t = tasks.getTask(i);
            if (t.getStart().toLocalDate().equals(date)) {
                count++;
            }
        }
        return count;
    }
}
