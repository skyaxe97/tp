package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.Lesson;
import seedu.lifeasier.tasks.Task;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayScheduleCommandTest {
    private static final LocalDateTime LESSON_SAMPLE_START = LocalDateTime.parse("2020-10-14T13:00");
    private static final LocalDateTime LESSON_SAMPLE_END = LocalDateTime.parse("2020-10-14T14:00");
    private static final Task LESSON_SAMPLE = new Lesson("CS2113", LESSON_SAMPLE_START, LESSON_SAMPLE_END);

    @Test
    void getStart_LessonObject_StartTime() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        LocalDateTime start = dsc.getStart(LESSON_SAMPLE);
        assertEquals(LocalDateTime.parse("2020-10-14T13:00"), start);
    }

    @Test
    void getTimeStamp_LocalDateTime_TimeString() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        LocalDateTime start = dsc.getStart(LESSON_SAMPLE);
        String timestamp = dsc.getTimeStamp(start);
        assertEquals("13:00", timestamp);
    }
}