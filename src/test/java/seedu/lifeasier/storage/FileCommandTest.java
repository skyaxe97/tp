package seedu.lifeasier.storage;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.notes.Note;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileCommandTest {

    public static final String EXPECTED_STRING_OUTPUT_NOTES =
            "Cats are awesome!=-=Cats are so cute, they are the best :D" + System.lineSeparator();

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private FileCommand fileCommand;
    private NoteStorage noteStorage;
    private TaskStorage taskStorage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public FileCommandTest() {
        this.fileCommand = new FileCommand();
        this.noteStorage = new NoteStorage();
        this.taskStorage = new TaskStorage();
        startTime = fileCommand.convertToLocalDateTime("09-04-21T18:00");
        endTime = fileCommand.convertToLocalDateTime("09-04-21T18:00");
    }

    @Test
    void convertToLocalDateTime_inputValidDateTime_correctOutput() {
        String formattedDateTime =
                fileCommand.convertToLocalDateTime("09-04-21T18:00").format(DATE_TIME_FORMATTER);
        assertEquals("09-04-21 18:00", formattedDateTime);
    }

    @Test
    void convertToLocalDateTIme_invalidDateTime_defaultDateTime() {
        String formattedDateTime =
                fileCommand.convertToLocalDateTime("09-23-21T18:00").format(DATE_TIME_FORMATTER);
        assertEquals("31-12-99 00:00", formattedDateTime);
    }

    @Test
    void convertNoteToString_newNote_correctStringOutput() {
        Note note = new Note("Cats are awesome!  ", "  Cats are so cute, they are the best :D  ");
        String output = noteStorage.convertNoteToString(note);
        assertEquals(EXPECTED_STRING_OUTPUT_NOTES, output);
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