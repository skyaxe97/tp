package seedu.lifeasier.ui;

import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ScheduleUi {

    private final TimetableUi timetable = new TimetableUi();

    public void showHome(TaskList tasks) {
        timetable.showTimetable(tasks);
        displayDeadlines(tasks);
    }

    private void displayDeadlines(TaskList tasks) {
        for (Task task : tasks.getTaskList()) {
            if (task instanceof Deadline) {
                System.out.println(task.toString());
            }
        }
    }

    public void displayWeekSchedule(TaskList tasks) {
        timetable.showTimetable(tasks);
    }

    public void displayDaySchedule(LocalDate date, TaskList tasks) {
        for (Task task : tasks.getTaskList()) {
            LocalDateTime startDateTime = task.getStart();
            LocalDateTime endDateTime = task.getEnd();
            if (startDateTime.toLocalDate().equals(date)) {
                printWithScheduleFormat(task, startDateTime, endDateTime);
            }
        }
    }

    public void printWithScheduleFormat(Task task, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String startDateTimeString = getTimeStamp(startDateTime);
        String endDateTimeString = (endDateTime == null) ? "      " : ("-" + getTimeStamp(endDateTime));
        System.out.println(startDateTimeString + endDateTimeString + "  " + task.getDescription());
    }

    public static String getDayOfWeek(int i) {
        LocalDateTime datePointer = LocalDateTime.now().plus(i, ChronoUnit.DAYS);
        return datePointer.getDayOfWeek().toString();
    }

    public String getTimeStamp(LocalDateTime timedItem) {
        return timedItem.toLocalTime().toString();
    }

    public int getTaskCountForToday(TaskList tasks, LocalDate date) {
        int count = 0;
        for (Task task : tasks.getTaskList()) {
            if (task.getStart().toLocalDate().equals(date)) {
                count++;
            }
        }
        return count;
    }
}
