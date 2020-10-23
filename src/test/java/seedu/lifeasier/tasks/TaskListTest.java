package seedu.lifeasier.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    void updateTasks_outdatedLesson_movedOneWeek() {
        taskList = new TaskList();
        String moduleCode = "cg1111";

        LocalDateTime start = LocalDateTime.of(20, 10, 22, 12, 00);
        LocalDateTime end = LocalDateTime.of(20, 10, 22, 13, 00);
        int recurrences = 11;

        Lesson lesson = new Lesson(moduleCode, start, end, recurrences);

        taskList.addTask(lesson);

        taskList.updateTasks(LocalDate.of(20, 10, 23));

        LocalDate expected = LocalDate.of(20, 10, 29);

        assertTrue(((lesson.isHappeningOn(expected)) && (lesson.getRecurrences() == 10)));

    }
}