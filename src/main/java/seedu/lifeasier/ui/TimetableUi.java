package seedu.lifeasier.ui;

import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TimetableUi {
    private static final String TIME_COLUMN_NAME = "TIME";
    private static final String TIME_FORMAT = "%02d:00";
    private static final int DEFAULT_START_HOUR = 7;
    private static final int DEFAULT_END_HOUR = 18;

    private static final int DAYS_COLUMN_COUNT = 7;
    private static final int MAX_COLUMN_WIDTH = 15;

    private static final String ROW_FORMAT = "|%-11s| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |";
    private static final String ROW_SEPARATOR = "+-----------+" + "-----------------+".repeat(DAYS_COLUMN_COUNT);

    private ArrayList<String> timetableRows = new ArrayList<>();

    public void showTimetable(TaskList tasks) {
        generateTimetable(tasks);
        System.out.println(ROW_SEPARATOR);
        for (String row: timetableRows) {
            System.out.println(row);
            System.out.println(ROW_SEPARATOR);
        }
    }

    public void generateTimetable(TaskList tasks) {
        timetableRows.add(getColumnTitlesString());

        LocalTime[] timeRange = getTimetableTimeRange(tasks);
        int firstHour = timeRange[0].getHour();
        int lastHour = timeRange[1].getHour();

        int currHour = firstHour;
        while (currHour < lastHour) {
            timetableRows.add(generateRowString(currHour, tasks));
            currHour++;
        }
    }

    public String getColumnTitlesString() {
        String[] columnTitles = new String[8];
        columnTitles[0] = TIME_COLUMN_NAME;
        for (int i = 0; i < 7; i++) {
            columnTitles[i + 1] = ScheduleUi.getDayOfWeek(i);
        }
        return String.format(ROW_FORMAT, (Object[]) columnTitles);
    }

    public String generateRowString(int hour, TaskList tasks) {
        String[] rowContents = generateRowContents(hour, tasks);
        return String.format(ROW_FORMAT, (Object[]) rowContents);
    }

    public String[] generateRowContents(int hour, TaskList tasks) {
        String[] rowContents = new String[8];
        LocalDate todayDate = LocalDate.now();

        rowContents[0] = getTimeSlotString(hour);
        for (int dayIncrement = 0; dayIncrement < 7; dayIncrement++) {
            LocalDate currDate = todayDate.plus(dayIncrement, ChronoUnit.DAYS);
            rowContents[dayIncrement + 1] = getCellString(currDate, hour, tasks);
        }
        return rowContents;
    }

    public String trimToFitTimetableCell(String fullString) {
        if (fullString.length() > MAX_COLUMN_WIDTH) {
            return fullString.substring(0, MAX_COLUMN_WIDTH - 3) + "...";
        }
        return fullString;
    }

    private String getTimeSlotString(int startHour) {
        String startHourString = String.format(TIME_FORMAT, startHour);
        String endHourString = String.format(TIME_FORMAT, startHour + 1);
        return startHourString + "-" + endHourString;
    }

    private String getCellString(LocalDate date, int hour, TaskList tasks) {
        ArrayList<String> cellContents = new ArrayList<>();
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task task = tasks.getTask(i);
            if (!(task instanceof Deadline) && task.isHappeningOn(date) && task.isWithinTimeSlot(hour)) {
                cellContents.add(task.getDescription());
            }
        }
        return trimToFitTimetableCell(cellContents.toString().replace("[", "").replace("]", ""));
    }

    public LocalTime[] getTimetableTimeRange(TaskList tasks) {
        LocalTime defaultEarliestTime = LocalTime.parse(String.format(TIME_FORMAT, DEFAULT_START_HOUR));
        LocalTime defaultLatestTime = LocalTime.parse(String.format(TIME_FORMAT, DEFAULT_END_HOUR));

        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task task = tasks.getTask(i);

            if (!(task instanceof Deadline)) {
                LocalTime currTaskStartTime = task.getStart().toLocalTime();
                LocalTime currTaskEndTime = task.getEnd().toLocalTime();

                defaultEarliestTime = getEarlierTime(defaultEarliestTime, currTaskStartTime);
                defaultLatestTime = getLaterTime(defaultLatestTime, currTaskEndTime);
            }
        }
        return new LocalTime[] {defaultEarliestTime, defaultLatestTime};
    }

    public LocalTime getLaterTime(LocalTime defaultLatestTime, LocalTime endTime) {
        return endTime.isAfter(defaultLatestTime) ? endTime : defaultLatestTime;
    }

    public LocalTime getEarlierTime(LocalTime defaultEarliestTime, LocalTime startTime) {
        return startTime.isAfter(defaultEarliestTime) ? startTime : defaultEarliestTime;
    }
}
