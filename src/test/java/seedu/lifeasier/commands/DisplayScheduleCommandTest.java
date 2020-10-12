package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Lesson;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayScheduleCommandTest {
    private static final LocalDateTime LESSON_SAMPLE_START = LocalDateTime.parse("2020-10-14T13:00");
    private static final LocalDateTime LESSON_SAMPLE_END = LocalDateTime.parse("2020-10-14T14:00");
    private static final Task LESSON_SAMPLE = new Lesson("CS2113", LESSON_SAMPLE_START, LESSON_SAMPLE_END);

    private static final LocalDateTime DEADLINE_SAMPLE_DATETIME = LocalDateTime.parse("2020-10-16T00:00");
    private static final Task DEADLINE_SAMPLE = new Deadline("CS2113", DEADLINE_SAMPLE_DATETIME);

    private static final TaskList TEST_TASKS = new TaskList();

    @Test
    void getStart_LessonObject_StartTime() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        LocalDateTime start = dsc.getStart(LESSON_SAMPLE);
        assertEquals(LocalDateTime.parse("2020-10-14T13:00"), start);
    }

    @Test
    void getEnd_LessonObject_EndTime() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        LocalDateTime end = dsc.getEnd(LESSON_SAMPLE);
        assertEquals(LocalDateTime.parse("2020-10-14T14:00"), end);
    }

    @Test
    void getEnd_DeadlineObject_NullLocalDateTime() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        LocalDateTime end = dsc.getEnd(DEADLINE_SAMPLE);
        assertEquals(null, end);
    }

    @Test
    void getTimeStamp_LocalDateTime_TimeString() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        LocalDateTime start = dsc.getStart(LESSON_SAMPLE);
        String timestamp = dsc.getTimeStamp(start);
        assertEquals("13:00", timestamp);
    }

    @Test
    void getTaskCountForToday_emptyTaskList_Zero() {
        DisplayScheduleCommand dsc = new DisplayScheduleCommand("");
        int taskCount = dsc.getTaskCountForToday(TEST_TASKS, LocalDateTime.parse("2020-10-15T00:00").toLocalDate());
        assertEquals(0, taskCount);
    }
}