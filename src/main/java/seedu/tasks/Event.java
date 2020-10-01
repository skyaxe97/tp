package seedu.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime time;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");

    public LocalDateTime getTime() {
        return this.time;
    }

    public Event(String description, LocalDateTime time, String duration) {
        super(description);
        this.time = time;
    }
    public Event(String description, LocalDateTime time, String duration, boolean isDone) {
        super(description);
        this.time = time;
        this.isDone = isDone;
    }
    @Override
    public String toString() {
        return ".[E]" + super.toString() + " (" + time.format(format) + ")";
    }
}