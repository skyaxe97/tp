package seedu.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    public LocalDateTime getBy() {
        return by;
    }


    @Override
    public String toString() {
        return ".[D]" + super.toString() + " (" + by.format(format) + ")";
    }
}