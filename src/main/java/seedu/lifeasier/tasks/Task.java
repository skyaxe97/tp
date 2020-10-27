package seedu.lifeasier.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Task {
    protected String description;
    protected int recurrences;
    protected int editNumber;

    private static final String TIME_FORMAT = "%02d:00";
    private static final int DEFAULT_EDIT_NUMBER = -999999;

    public Task(String description) {
        this.description = description;
        this.recurrences = 0;
        this.editNumber = DEFAULT_EDIT_NUMBER;
    }

    public Task(Task task, int editNumber) {
        this.description = task.description;
        setEditNumber(editNumber);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEditNumber(int number) {
        this.editNumber = number;
    }

    public int getEditNumber() {
        return editNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getRecurrences() {
        return recurrences;
    }

    public void decrementRecurrences(int by) {
        recurrences = recurrences - by;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return description;
    }

    public abstract LocalDateTime getStart();

    public abstract LocalDateTime getEnd();

    public abstract void setStart(LocalDateTime start);

    public abstract void setEnd(LocalDateTime end);

    public abstract void moveAndUpdateRecurrences(LocalDate day);


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

    public boolean isHappeningBefore(LocalDate date) {
        return getStart().toLocalDate().isBefore(date);
    }
}
