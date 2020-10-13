package seedu.lifeasier.timetable;

import seedu.lifeasier.tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Timetable {
    private static final String TIME_COLUMN_NAME = "TIME";
    private static final String TIME_FORMAT ="%02d:00";
    private static final int DEFAULT_START_HOUR = 7;
    private static final int DEFAULT_END_HOUR = 18;

//    private static final LocalTime DEFAULT_START_HOUR = LocalTime.parse("07:00");
//    private static final LocalTime DEFAULT_END_HOUR = LocalTime.parse("18:00");

    private static final String ROW_FORMAT = "|%-11s| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |";
    private static final String ROW_SEPARATOR = "+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+";
    private final ArrayList<String> timetable = new ArrayList<>();

    public void showHome(TaskList tasks) {
        generateTimetable(tasks);
        for (String row: timetable) {
            System.out.println(row);
        }
    }

     public void generateTimetable(TaskList tasks) {
        addTimetableHeader();
        String[][] table = new String[2][2];
        


        LocalTime[] timeRange = getWholeDayTimeRange(tasks);
        int firstHour = timeRange[0].getHour();
        int lastHour = timeRange[1].getHour();

        int currHour = firstHour;
        while (currHour < lastHour){
            timetable.add(generateRowString(currHour, tasks));
            currHour++;
        }
    }

    public void addTimetableHeader() {
        timetable.add(ROW_SEPARATOR);
        timetable.add(getColumnTitlesString());
        timetable.add(ROW_SEPARATOR);
    }

    public String getColumnTitlesString() {
        LocalDateTime dateToday = LocalDateTime.now();
        String[] columnTitles = new String[8];
        columnTitles[0] = TIME_COLUMN_NAME;
        for (int i = 0; i < 7; i++) {
            LocalDateTime datePointer = dateToday.plus(i, ChronoUnit.DAYS);
            columnTitles[i + 1] = datePointer.getDayOfWeek().toString();
        }
        return String.format(ROW_FORMAT, (Object[]) columnTitles);
    }

    public String generateRowString(int hour, TaskList tasks) {
        String[] rowContents = generateRowContents(hour, tasks);
        //return String.join(",", rowContents);
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

    public static void displayDayOfWeek(int i) {
        LocalDateTime datePointer = LocalDateTime.now().plus(i, ChronoUnit.DAYS);
        System.out.println(datePointer.getDayOfWeek());
    }

    private static String getTimeSlotString(int startHour) {
        String startHourString = String.format(TIME_FORMAT, startHour);
        String endHourString = String.format(TIME_FORMAT, startHour + 1);
        return startHourString + "-" + endHourString;
    }

    private static String getCellString(LocalDate date, int hour, TaskList tasks) {
        ArrayList<String> cellContents = new ArrayList<>();
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task task = tasks.getTask(i);
            if (!(task instanceof Deadline)) {
                if (task.getStart().toLocalDate().equals(date) && (task.getStart().getHour() <= hour && task.getEnd().getHour() >= (hour + 1))) {
                    cellContents.add(task.getDescription());
                }
            }
        }
        return cellContents.toString().replace("[", "").replace("]", "");
    }

    public LocalTime[] getWholeDayTimeRange(TaskList tasks) {
        LocalTime defaultEarliestTime = LocalTime.parse(String.format(TIME_FORMAT, DEFAULT_START_HOUR));
        LocalTime defaultLatestTime = LocalTime.parse(String.format(TIME_FORMAT, DEFAULT_END_HOUR));

        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task task = tasks.getTask(i);

            if (!(task instanceof Deadline)) {
                LocalTime earliestTime = task.getStart().toLocalTime();
                LocalTime latestTime = task.getEnd().toLocalTime();

                if (earliestTime.isBefore(defaultEarliestTime)) {
                    defaultEarliestTime = earliestTime;
                }

                if (latestTime.isAfter(defaultLatestTime)) {
                    defaultLatestTime = latestTime;
                }
            }
        }
        return new LocalTime[] {defaultEarliestTime, defaultLatestTime};
    }
}
