package seedu.lifeasier.ui;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.TaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduleUiTest {
    public static final TaskList TEST_TASKS = new TaskList();
    public static final LocalDate SAMPLE_DATE = LocalDateTime.parse("2020-10-15T00:00").toLocalDate();

    @Test
    void getTaskCountForToday_emptyTaskList_Zero() {
        ScheduleUi scheduleUiTest = new ScheduleUi();
        int taskCount = scheduleUiTest.getTaskCountForToday(seedu.lifeasier.ui.ScheduleUiTest.TEST_TASKS, SAMPLE_DATE);
        assertEquals(0, taskCount);
    }
}