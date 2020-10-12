package seedu.lifeasier.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}