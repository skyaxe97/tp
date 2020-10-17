package seedu.lifeasier.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected static int taskCounter = 0;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        taskCounter++;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return description;
    }

    public abstract LocalDateTime getStart();

    public abstract LocalDateTime getEnd();

    public abstract void setStart(LocalDateTime start);

    public abstract void setEnd(LocalDateTime end);

    public boolean isWithinTimeSlot(int hour) {
        return startsAfter(hour) && endsBefore(hour + 1);
    }

    public boolean endsBefore(int hour) {
        return getEnd().getHour() >= hour;
    }

    public boolean startsAfter(int hour) {
        return getStart().getHour() <= hour;
    }

    public boolean isHappeningOn(LocalDate date) {
        return getStart().toLocalDate().equals(date);
    }
}
