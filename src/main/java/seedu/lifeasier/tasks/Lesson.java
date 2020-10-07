package seedu.lifeasier.tasks;

import java.time.LocalDateTime;


public class Lesson extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Lesson(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public Lesson(String description, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(description);
        this.start = start;
        this.end = end;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return ".[E]" + super.toString() + " (" + start + "  " + end + ")";
    }
}