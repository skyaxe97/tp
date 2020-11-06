package seedu.lifeasier.model.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lesson extends Task {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");
    private static final String PARAM_LESSON = "lesson";

    protected LocalDateTime start;
    protected LocalDateTime end;
    private String type = PARAM_LESSON;

    public Lesson(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public Lesson(String description, LocalDateTime start, LocalDateTime end, int recurrences) {
        super(description);
        this.start = start;
        this.end = end;
        this.recurrences = recurrences;
    }

    public Lesson(Task task, int editNumber) {
        super(task, editNumber);
        this.start = ((Lesson) task).start;
        this.end = ((Lesson) task).end;
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
        return "Lesson: " + super.toString() + " (" + start.format(format) + " to " + end.format(format) + "), "
                + "repeats weekly " + recurrences + " times";
    }

    /**
     * Moves a recurring lesson 7 days forward, and decrements remaining recurrences by 1.
     */
    @Override
    public void moveAndUpdateRecurrences(LocalDate day) {
        while (this.start.toLocalDate().isBefore(day)) {
            decrementRecurrences(1);
            start = start.plusDays(7);
            end = end.plusDays(7);
        }
    }
}
