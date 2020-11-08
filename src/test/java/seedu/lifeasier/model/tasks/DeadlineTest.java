package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeadlineTest {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");
    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");

    @Test
    void getBy() {
        Deadline deadline = new Deadline("deadline", sampleTime1);
        assertEquals(deadline.getBy(), sampleTime1);
    }

    @Test
    void getType() {
        Deadline deadline = new Deadline("deadline", sampleTime1);
        assertEquals(deadline.getType(), "deadline");
    }

    @Test
    void getStart() {
        Deadline deadline = new Deadline("deadline", sampleTime1);
        assertEquals(deadline.getStart(), sampleTime1);
    }

    @Test
    void getEnd() {
        Deadline deadline = new Deadline("deadline", sampleTime1);
        assertEquals(deadline.getEnd(), null);
    }

    @Test
    void setStart() {
        Deadline deadline = new Deadline("deadline", sampleTime1);
        deadline.setStart(sampleTime2);
        assertEquals(deadline.getBy(), sampleTime2);
    }

    @Test
    void testToString() {
        Deadline deadline = new Deadline("deadline", sampleTime1, 2);
        assertEquals(deadline.toString(), "Deadline: deadline by (11 Nov 2020, 11:11), repeats weekly 2 times");
    }

    @Test
    void moveAndUpdateRecurrences() {
        Deadline deadline = new Deadline("deadline", sampleTime1, 1);
        LocalDate day = LocalDate.parse("2020-11-12");
        LocalDateTime newDay = LocalDateTime.parse("2020-11-18T11:11");
        deadline.moveAndUpdateRecurrences(day);
        assertEquals(deadline.getBy(), newDay);
    }

    @Test
    void isDuplicate() {
        Deadline deadline1 = new Deadline("deadline", sampleTime1, 1);
        assertTrue(deadline1.isDuplicate("deadline", sampleTime1, 1));
        assertTrue(deadline1.isDuplicate("deadline", sampleTime1, 2));
    }
}