package seedu.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lesson extends Task {
    protected LocalDateTime time;
    protected String duration;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");

    public LocalDateTime getTime() {
        return this.time;
    }

    public Lesson(String description, LocalDateTime time, String duration) {
        super(description);
        this.time = time;
        this.duration = duration;
    }
    public Lesson(String description, LocalDateTime time,String duration, boolean isDone) {
        super(description);
        this.time = time;
        this.duration = duration;
        this.isDone = isDone;
    }
    @Override
    public String toString() {
        return ".[E]" + super.toString() + " (" + time.format(format) + ")";
    }
}