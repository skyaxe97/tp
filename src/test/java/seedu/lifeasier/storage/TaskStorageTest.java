package seedu.lifeasier.storage;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Event;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskStorageTest {

    private TaskStorage taskStorage;
    private FileCommand fileCommand;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TaskStorageTest() {
        this.taskStorage = new TaskStorage();
        this.fileCommand = new FileCommand();
        startTime = fileCommand.convertToLocalDateTime("09-04-21T18:00");
        endTime = fileCommand.convertToLocalDateTime("09-04-21T18:00");
    }

    @Test
    void convertDeadlineToString_invalidClass_ClassCastException() {
        Event testEvent = new Event("Test event", startTime, endTime);

        assertThrows(ClassCastException.class, () -> {
            taskStorage.convertDeadlineToString(testEvent, "deadline");
        });
    }

    @Test
    void convertEventToString_invalidClass_ClassCastException() {
        Deadline testDeadline = new Deadline("Test Deadline", startTime);

        assertThrows(ClassCastException.class, () -> {
            taskStorage.convertEventToString(testDeadline, "event");
        });
    }

    @Test
    void convertLessonToString_invalidClass_ClassCastException() {
        Deadline testDeadline = new Deadline("Test Deadline", startTime);

        assertThrows(ClassCastException.class, () -> {
            taskStorage.convertLessonToString(testDeadline, "lesson");
        });
    }

}