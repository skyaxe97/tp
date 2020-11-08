package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskHistoryTest {
    private final TaskList testTaskList = new TaskList();
    private final TaskHistory testTaskHistory = new TaskHistory();

    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");
    private final Deadline testDeadline = new Deadline("testDeadline", sampleTime1, 0);
    private final Event testEvent = new Event("testEvent", sampleTime1, sampleTime2, 1);
    private final Lesson testLesson = new Lesson("testLesson", sampleTime1, sampleTime2, 3);

    void setUpTaskList() {
        testTaskList.addTask(testDeadline);
        testTaskList.addTask(testEvent);
        testTaskList.addTask(testLesson);
    }

    void resetTaskHistory() {
        while (testTaskHistory.getChangeCount() > 0) {
            testTaskHistory.popLastTask();
        }
    }

    @Test
    void getLastTask_emptyTaskHistory_IndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, testTaskHistory::getLastTask);
    }

    @Test
    void getChangeCount_emptyTaskHistory_0() {
        assertEquals(0, testTaskHistory.getChangeCount());
    }

    @Test
    void getLastTask_TaskHistoryWithOneItem_Deadline() {
        setUpTaskList();
        testTaskList.createTaskMap();

        testTaskList.setMap(1, 0);
        Task copyTestDeadline = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 1);
        int testEditNumber = copyTestDeadline.editNumber;

        testTaskHistory.pushOldCopy(copyTestDeadline);
        Task lastTask = testTaskHistory.getLastTask();

        assertEquals(1, testEditNumber);
        assertEquals(copyTestDeadline, lastTask);

        resetTaskHistory();
    }

    @Test
    void getEditNumber_TaskHistoryWithDistinctEditedItems_PositiveInts() {
        setUpTaskList();
        testTaskList.createTaskMap();

        testTaskList.setMap(1, 0);
        Task copyTestDeadline = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 1);
        testTaskList.setMap(1, 1);
        Task copyTestEvent = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 1);
        testTaskList.setMap(1, 2);
        Task copyTestLesson = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 1);

        final int testEditNumber1 = copyTestDeadline.getEditNumber();
        final int testEditNumber2 = copyTestEvent.getEditNumber();
        final int testEditNumber3 = copyTestLesson.getEditNumber();

        testTaskHistory.pushOldCopy(copyTestDeadline);
        testTaskHistory.pushOldCopy(copyTestEvent);
        testTaskHistory.pushOldCopy(copyTestLesson);

        Task lastTask = testTaskHistory.getLastTask();

        Assertions.assertAll(
            () -> assertEquals(copyTestLesson, lastTask),
            () -> assertEquals(1, testEditNumber1),
            () -> assertEquals(2, testEditNumber2),
            () -> assertEquals(3, testEditNumber3)
        );

        resetTaskHistory();
    }

    @Test
    void getEditNumber_TaskHistoryWithDistinctDeletedItems_NegativeInts() {
        setUpTaskList();
        testTaskList.createTaskMap();

        testTaskList.setMap(1, 0);
        Task copyTestDeadline = testTaskHistory.getCurrCopyOfTaskToDelete(testTaskList, 1);
        testTaskList.setMap(1, 1);
        Task copyTestEvent = testTaskHistory.getCurrCopyOfTaskToDelete(testTaskList, 1);
        testTaskList.setMap(1, 2);
        Task copyTestLesson = testTaskHistory.getCurrCopyOfTaskToDelete(testTaskList, 1);

        final int testEditNumber1 = copyTestDeadline.getEditNumber();
        final int testEditNumber2 = copyTestEvent.getEditNumber();
        final int testEditNumber3 = copyTestLesson.getEditNumber();

        testTaskHistory.pushOldCopy(copyTestDeadline);
        testTaskHistory.pushOldCopy(copyTestEvent);
        testTaskHistory.pushOldCopy(copyTestLesson);

        Task lastTask = testTaskHistory.getLastTask();

        Assertions.assertAll(
            () -> assertEquals(copyTestLesson, lastTask),
            () -> assertEquals(-1, testEditNumber1),
            () -> assertEquals(-2, testEditNumber2),
            () -> assertEquals(-3, testEditNumber3)
        );

        resetTaskHistory();
    }

    @Test
    void getEditNumber_TaskHistoryWithNonDistinctEditedItems_sameEditNumber() {
        setUpTaskList();
        testTaskList.createTaskMap();

        testTaskList.setMap(1, 0);
        Task copyTestDeadline = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 1);
        testTaskList.setMap(1, 0);
        Task copyTestDeadline2 = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 1);

        int testEditNumber1 = copyTestDeadline.getEditNumber();
        int testEditNumber1again = copyTestDeadline2.getEditNumber();

        testTaskHistory.pushOldCopy(copyTestDeadline);
        testTaskHistory.pushOldCopy(copyTestDeadline2);

        Task lastTask = testTaskHistory.getLastTask();

        Assertions.assertAll(
            () -> assertEquals(copyTestDeadline2, lastTask),
            () -> assertEquals(1, testEditNumber1),
            () -> assertEquals(1, testEditNumber1again)
        );

        resetTaskHistory();
    }
}