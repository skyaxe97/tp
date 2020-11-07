package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LessonTest {
    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");

    @Test
    void moveAndUpdateRecurrences() {
        Lesson lesson = new Lesson("lesson", sampleTime1, sampleTime2, 1);
        LocalDate day = LocalDate.parse("2020-11-12");
        LocalDateTime newDay = LocalDateTime.parse("2020-11-18T11:11");
        lesson.moveAndUpdateRecurrences(day);
        assertEquals(lesson.getStart(), newDay);
    }

    @Test
    void isDuplicate() {
        Lesson lesson1 = new Lesson("lesson", sampleTime1, sampleTime2, 1);
        assertTrue(lesson1.isDuplicate("lesson", sampleTime1, sampleTime2, 1));
        assertTrue(lesson1.isDuplicate("lesson", sampleTime1, sampleTime2, 2));
    }
}