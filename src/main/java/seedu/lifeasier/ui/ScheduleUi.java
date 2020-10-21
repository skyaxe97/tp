package seedu.lifeasier.ui;

import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The ScheduleUi class handles all schedule-related displays.
 */
public class ScheduleUi {
    private static final TimetableUi timetable = TimetableUi.getInstance();

    /**
     * Displays the home screen to the user.
     */
    public void showHome(TaskList tasks) {
        timetable.showTimetable(tasks);
        System.out.println();
        displayUpcomingDeadlines(tasks);
    }

    private void displayUpcomingDeadlines(TaskList tasks) {
        LocalDate dateAfterOneWeek = LocalDate.now().plus(1, ChronoUnit.WEEKS);

        System.out.println("Here are your upcoming deadlines this week:");
        int id = 1;

        for (Task task : tasks.getTaskList()) {
            if (task instanceof Deadline && task.isHappeningBefore(dateAfterOneWeek)) {
                System.out.println((id) + task.toString());
                id++;
            }
        }
    }

    public void displayWeekSchedule(TaskList tasks) {
        timetable.showTimetable(tasks);
        displayUpcomingDeadlines(tasks);
    }

    public void displayDaySchedule(LocalDate date, TaskList tasks) {
        tasks.sort();
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

    public static String getDayOfWeek(LocalDateTime datePointer) {
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
