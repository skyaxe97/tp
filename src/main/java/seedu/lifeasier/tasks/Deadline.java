package seedu.lifeasier.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    protected LocalDateTime by;
    private String type = "deadline";

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, int recurrences) {
        super(description);
        this.by = by;
        this.recurrences = recurrences;
    }

    public Deadline(Task task, int editNumber) {
        super(task, editNumber);
        this.by = ((Deadline) task).by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public LocalDateTime getStart() {
        return getBy();
    }

    @Override
    public LocalDateTime getEnd() {
        return null;
    }

    @Override
    public void setStart(LocalDateTime start) {
        this.by = start;
    }

    @Override
    public void setEnd(LocalDateTime end) {
    }

    @Override
    public String toString() {
        return "Deadline: " + super.toString() + " by (" + by.format(format) + "), "
                + "repeats weekly " + recurrences + " times";
    }

    /**
     * Moves a recurring deadline 7 days forward, and decrements remaining recurrences by 1.
     */
    @Override
    public void moveAndUpdateRecurrences() {
        decrementRecurrences(1);
        by = by.plusDays(7);
    }
}
