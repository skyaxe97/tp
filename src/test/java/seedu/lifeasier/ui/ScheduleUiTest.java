package seedu.lifeasier.ui;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.lifeasier.ui.ScheduleUi.getDayOfWeek;

class ScheduleUiTest {
    public static final TaskList EMPTY_TASK_LIST = new TaskList();
    public static final TaskList NONEMPTY_TASK_LIST = new TaskList();
    public static final LocalDateTime SAMPLE_DATETIME = LocalDateTime.parse("2020-10-15T09:00");
    public static final LocalDate SAMPLE_DATE = SAMPLE_DATETIME.toLocalDate();

    private static final ScheduleUi scheduleUiTest = new ScheduleUi();

    @Test
    void getTaskCountForToday_emptyTaskList_Zero() {
        int taskCount = scheduleUiTest.getTaskCountForToday(ScheduleUiTest.EMPTY_TASK_LIST, SAMPLE_DATE);
        assertEquals(0, taskCount);
    }

    @Test
    void getTaskCountForToday_TaskListWithFiveItems_Five() {
        for (int i = 0; i < 5; i++) {
            NONEMPTY_TASK_LIST.addDeadline("test" + i, SAMPLE_DATETIME);
        }

        int taskCount = scheduleUiTest.getTaskCountForToday(ScheduleUiTest.NONEMPTY_TASK_LIST, SAMPLE_DATE);
        assertEquals(5, taskCount);
    }

    @Test
    void getTimeStamp_SampleDateTime_TimeString() {
        String timeString = scheduleUiTest.getTimeStamp(SAMPLE_DATETIME);
        assertEquals("09:00", timeString);
    }

    @Test
    void getDayOfWeek_SampleDateTime_Thursday() {
        String dayOfWeek = getDayOfWeek(SAMPLE_DATETIME);
        assertEquals("THURSDAY", dayOfWeek);
    }
}