package seedu.lifeasier.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {
    TaskList taskList;

    @Test
    void getTaskCount_returnTaskCountAsInt() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", LocalDateTime.now(), LocalDateTime.now()));
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void getTask_outOfBoundIndex_throwsException() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", LocalDateTime.now(), LocalDateTime.now()));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(2));
    }

    @Test
    void getTask_invalidIndex_throwsException() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", LocalDateTime.now(), LocalDateTime.now()));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(-1));
    }

    @Test
    void updateTasks_outdatedRecurringLesson_movedOneWeek() {
        taskList = new TaskList();
        String moduleCode = "cg1111";

        LocalDateTime start = LocalDateTime.of(20, 10, 27, 12, 0);
        LocalDateTime end = LocalDateTime.of(20, 10, 27, 13, 0);
        int recurrences = 11;

        Lesson lesson = new Lesson(moduleCode, start, end, recurrences);

        taskList.addTask(lesson);

        taskList.updateTasks(LocalDate.of(20, 10, 29));

        LocalDate expected = LocalDate.of(20, 11, 3);

        assertTrue(((lesson.isHappeningOn(expected)) && (lesson.getRecurrences() == 10)));
    }

    @Test
    void updateTasks_outdatedTasks_deleted() {
        taskList = new TaskList();

        String moduleCode = "cg1111";
        LocalDateTime start1 = LocalDateTime.of(20, 10, 27, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(20, 10, 27, 13, 0);
        int recurrences = 0;

        String eventDescription = "my event";
        LocalDateTime start2 = LocalDateTime.of(20, 10, 28, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(20, 10, 28, 23, 0);

        String deadlineDescription = "my deadline";
        LocalDateTime by = LocalDateTime.of(20, 10, 28, 23, 59);

        taskList.addLesson(moduleCode, start1, end1, recurrences);
        taskList.addEvent(eventDescription, start2, end2, recurrences);
        taskList.addDeadline(deadlineDescription, by, recurrences);

        taskList.updateTasks(LocalDate.of(20, 10, 29));

        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void getTasksFromOneDay_sameDayTasks_returned() {
        taskList = new TaskList();

        String moduleCode = "cg1111";
        LocalDateTime start1 = LocalDateTime.of(20, 10, 27, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(20, 10, 27, 13, 0);
        int recurrences = 0;

        String eventDescription = "my event";
        LocalDateTime start2 = LocalDateTime.of(20, 10, 27, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(20, 10, 27, 23, 0);

        String deadlineDescription = "my deadline";
        LocalDateTime by = LocalDateTime.of(20, 10, 27, 23, 59);

        taskList.addLesson(moduleCode, start1, end1, recurrences);
        taskList.addEvent(eventDescription, start2, end2, recurrences);
        taskList.addDeadline(deadlineDescription, by, recurrences);

        ArrayList<Task> tasksFromOneDay = taskList.getTasksFromOneDay(LocalDate.of(20, 10, 27));

        assertEquals(3, tasksFromOneDay.size());
    }
}
