package seedu.lifeasier.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lesson extends Task {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    protected LocalDateTime start;
    protected LocalDateTime end;
    private String type = "lesson";

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
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @Override
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "[L] " + super.toString() + " (" + start.format(format) + " to " + end.format(format) + ")";
    }

}