package seedu.lifeasier.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskListTest {
    TaskList taskList;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private static final LocalDateTime SAMPLE1 = LocalDateTime.parse("12-12-20 12:00", DATE_TIME_FORMATTER);
    private static final LocalDateTime SAMPLE2 = LocalDateTime.parse("12-12-20 12:00", DATE_TIME_FORMATTER);

    @Test
    void name() {
    }

    @Test
    void getTaskCount_returnTaskCountAsInt() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void getTask_outOfBoundIndex_throwsException() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(2));
    }

    @Test
    void getTask_invalidIndex_throwsException() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(-1));
    }

    @Test
    void getTaskList_returnsFullTaskList() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertNotNull(taskList.getTaskList());
    }


    @Test
    void addEvent() {
        taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void addLesson() {
        taskList = new TaskList();
        taskList.addLesson("Lesson", SAMPLE1, SAMPLE2);
        Task lesson = new Lesson("Lesson", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), lesson.toString());
    }

    @Test
    void addDeadline() {
        taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void editTaskDescription() {
    }

    @Test
    void editLessonTime() {
    }

    @Test
    void editEventTime() {
    }

    @Test
    void editDeadlineTime() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void printMatchingTasks() {
    }

    @Test
    void checkForIndexOutOfBounds() {
    }

    @Test
    void getTasksFromOneDay() {
    }

    @Test
    void sort() {
    }
}