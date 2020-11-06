package seedu.lifeasier.model.tasks;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public void moveAndUpdateRecurrences(LocalDate day) {
        while (this.by.toLocalDate().isBefore(day)) {
            decrementRecurrences(1);
            by = by.plusDays(7);
        }
    }

    /**
     * Returns true if a similar or duplicate Deadline already exists in the schedule.
     *
     * @param description Description of Deadline to be checked.
     * @param by By DateTime of Deadline to be checked.
     * @param recurrences Recurrences of Deadline to be checked.
     * @return True if a similar or duplicate Deadline already exists in the schedule.
     */
    public boolean isDuplicate(String description, LocalDateTime by, int recurrences) {

        LocalTime existingBy = this.by.toLocalTime();
        DayOfWeek existingDay = this.by.getDayOfWeek();

        LocalTime newBy = by.toLocalTime();
        DayOfWeek newDay = by.getDayOfWeek();

        return (this.description.equals(description)
                && existingBy == newBy
                && existingDay == newDay
                && ((this.recurrences > 0 && recurrences > 0) || this.by.equals(by)));
    }
}
