package seedu.lifeasier.tasks;

import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;

    private static final String TYPE = "deadline";

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
    public String getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return ".[D]" + super.toString() + " (" + by + ")";
    }
}