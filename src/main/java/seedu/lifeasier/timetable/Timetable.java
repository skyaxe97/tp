package seedu.lifeasier.timetable;

import seedu.lifeasier.tasks.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Timetable {
    private static final String TIME_COLUMN_NAME = "TIME";
    private static final String TIME_FORMAT ="%02d:00";
    private static final int DEFAULT_START_HOUR = 7;
    private static final int DEFAULT_END_HOUR = 18;

    private static final String ROW_FORMAT = "|%-11s| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |";
    private static final String ROW_SEPARATOR = "+-----------+----------------+----------------+----------------+----------------+----------------+----------------+----------------+";


    public void generateTimetable(TaskList tasks) {
        LocalTime[] timeRange = getTimeRange(tasks);
        int firstHour = timeRange[0].getHour();
        int lastHour = timeRange[1].getHour();

        //int hourDifference = getHourDifference(getTimeRange(tasks)[0], getTimeRange(tasks)[1]);
        int currHour = firstHour;
        while (currHour < lastHour){
            for (int dayCount = 0; dayCount < 7; dayCount++) {

            }
            currHour++;
        }
        String leftAlignFormat = "| %-2s | %-4d |%n";

        System.out.format("+-----------------+------+%n");
        System.out.format("| Column name     | ID   |%n");
        System.out.format("+-----------------+------+%n");
        for (int i = 0; i < 5; i++) {
            System.out.format(leftAlignFormat, "some data" + i, i * i);
        }
        System.out.format("+-----------------+------+%n");
    }

    public static void displayDayOfWeek(int i) {
        LocalDateTime datePointer = LocalDateTime.now().plus(i, ChronoUnit.DAYS);
        System.out.println(datePointer.getDayOfWeek());
    }

    private static int getHourDifference(LocalTime time1, LocalTime time2) {
        return time1.getHour() - time2.getHour();
    }

    public LocalTime[] getTimeRange(TaskList tasks) {
        LocalTime defaultEarliestTime = LocalTime.parse(String.format(TIME_FORMAT, DEFAULT_START_HOUR));
        LocalTime defaultLatestTime = LocalTime.parse(String.format(TIME_FORMAT, DEFAULT_END_HOUR));

        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task task = tasks.getTask(i);
            LocalTime earliestTime = task.getStart().toLocalTime();
            LocalTime latestTime = task.getEnd().toLocalTime();

            if (earliestTime.isBefore(defaultEarliestTime)) {
                defaultEarliestTime = earliestTime;
            }

            if (latestTime.isAfter(defaultLatestTime)) {
                defaultLatestTime = earliestTime;
            }
        }
        return new LocalTime[] {defaultEarliestTime, defaultLatestTime};
    }
}
