package seedu.lifeasier.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected static int taskCounter = 0;

    private static final String TIME_FORMAT = "%02d:00";

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        taskCounter++;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbol
    }

    public void markAsDone() {
        this.isDone = true;
        taskCounter--;
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return isDone;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }

    public abstract LocalDateTime getStart();

    public abstract LocalDateTime getEnd();

    public boolean isWithinTimeSlot(int timeSlotStartHour) {
        return startsBeforeOrAt(timeSlotStartHour) && endsAtOrAfter(timeSlotStartHour + 1);
    }

    public boolean startsBeforeOrAt(int hour) {
        return getRoundedDownStartHour(getStart().toLocalTime()) <= hour;
    }

    public int getRoundedDownStartHour(LocalTime startTime) {
        return startTime.getHour();
    }

    public boolean endsAtOrAfter(int hour) {
        return getRoundedUpEndHour(getEnd().toLocalTime()) >= hour;
    }

    public int getRoundedUpEndHour(LocalTime endTime) {
        int endHour = endTime.getHour();
        LocalTime adjustedEndTime = LocalTime.parse(String.format(TIME_FORMAT, endHour));

        if (endTime.equals(adjustedEndTime)) {
            return endHour;
        } else {
            return endHour + 1;
        }
    }

    public boolean isHappeningOn(LocalDate date) {
        return getStart().toLocalDate().equals(date);
    }
}
