package seedu.lifeasier.ui;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Lesson;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScheduleUiTest {
    private static final LocalDateTime LESSON_SAMPLE_START = LocalDateTime.parse("2020-10-14T13:00");
    private static final LocalDateTime LESSON_SAMPLE_END = LocalDateTime.parse("2020-10-14T14:00");
    public static final Task LESSON_SAMPLE = new Lesson("CS2113", LESSON_SAMPLE_START, LESSON_SAMPLE_END);

    private static final LocalDateTime DEADLINE_SAMPLE_DATETIME = LocalDateTime.parse("2020-10-16T00:00");
    public static final Task DEADLINE_SAMPLE = new Deadline("CS2113", DEADLINE_SAMPLE_DATETIME);

    public static final TaskList TEST_TASKS = new TaskList();
    public static final LocalDate SAMPLE_DATE = LocalDateTime.parse("2020-10-15T00:00").toLocalDate();

    @Test
    void getStart_LessonObject_StartTime() {
        ScheduleUi scheduleUiTest = new ScheduleUi();
        LocalDateTime start = scheduleUiTest.getStart(seedu.lifeasier.ui.ScheduleUiTest.LESSON_SAMPLE);
        assertEquals(LocalDateTime.parse("2020-10-14T13:00"), start);
    }

    @Test
    void getEnd_LessonObject_EndTime() {
        ScheduleUi scheduleUiTest = new ScheduleUi();
        LocalDateTime end = scheduleUiTest.getEnd(seedu.lifeasier.ui.ScheduleUiTest.LESSON_SAMPLE);
        assertEquals(LocalDateTime.parse("2020-10-14T14:00"), end);
    }

    @Test
    void getEnd_DeadlineObject_NullLocalDateTime() {
        ScheduleUi scheduleUiTest = new ScheduleUi();
        LocalDateTime end = scheduleUiTest.getEnd(ScheduleUiTest.DEADLINE_SAMPLE);
        assertNull(end);
    }

    @Test
    void getTimeStamp_LocalDateTime_TimeString() {
        ScheduleUi scheduleUiTest = new ScheduleUi();
        LocalDateTime start = scheduleUiTest.getStart(seedu.lifeasier.ui.ScheduleUiTest.LESSON_SAMPLE);
        String timestamp = scheduleUiTest.getTimeStamp(start);
        assertEquals("13:00", timestamp);
    }

    @Test
    void getTaskCountForToday_emptyTaskList_Zero() {
        ScheduleUi scheduleUiTest = new ScheduleUi();
        int taskCount = scheduleUiTest.getTaskCountForToday(seedu.lifeasier.ui.ScheduleUiTest.TEST_TASKS, SAMPLE_DATE);
        assertEquals(0, taskCount);
    }
}