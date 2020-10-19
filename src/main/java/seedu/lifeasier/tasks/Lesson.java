package seedu.lifeasier.tasks;

import java.time.LocalDateTime;


public class Lesson extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    private String type = "lesson";

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
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
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return ".[E]" + super.toString() + " (" + start + "  " + end + ")";
    }
}