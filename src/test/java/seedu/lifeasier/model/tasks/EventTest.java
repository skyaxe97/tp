package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");
    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");
    private final LocalDateTime sampleTime3 = LocalDateTime.parse("2020-12-12T12:13");

    @Test
    void getType() {
        Event event = new Event("event", sampleTime1, sampleTime2);
        assertEquals(event.getType(), "event");
    }

    @Test
    void getStart() {
        Event event = new Event("event", sampleTime1, sampleTime2);
        assertEquals(event.getStart(), sampleTime1);
    }

    @Test
    void getEnd() {
        Event event = new Event("Eevent", sampleTime1, sampleTime2);
        assertEquals(event.getEnd(), sampleTime2);
    }

    @Test
    void setStart() {
        Event event = new Event("event", sampleTime1, sampleTime3);
        event.setStart(sampleTime2);
        assertEquals(event.getStart(), sampleTime2);
    }

    @Test
    void testToString() {
        Event event = new Event("event", sampleTime1, sampleTime2, 2);
        assertEquals(event.toString(), "Event: event (11 Nov 2020, 11:11 to 12 Dec 2020, 12:12),"
            + " repeats weekly 2 times");
    }

    @Test
    void moveAndUpdateRecurrences() {
        Event event = new Event("event", sampleTime1, sampleTime2, 1);
        LocalDate day = LocalDate.parse("2020-11-12");
        LocalDateTime newDay = LocalDateTime.parse("2020-11-18T11:11");
        event.moveAndUpdateRecurrences(day);
        assertEquals(event.getStart(), newDay);
    }

    @Test
    void isDuplicate() {
        Event event1 = new Event("event", sampleTime1, sampleTime2, 1);
        assertTrue(event1.isDuplicate("event", sampleTime1, sampleTime2, 1));
        assertTrue(event1.isDuplicate("event", sampleTime1, sampleTime2, 2));
    }
}