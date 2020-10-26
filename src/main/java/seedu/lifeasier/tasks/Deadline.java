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

    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    public Deadline(Task task, int editNumber) {
        super(task, editNumber);
        this.by = ((Deadline) task).by;
        this.isDone = task.isDone;
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
        return "[D] " + super.toString() + " by (" + by.format(format) + ")";
    }
}