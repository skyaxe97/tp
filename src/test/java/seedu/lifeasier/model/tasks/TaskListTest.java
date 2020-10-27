package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void getTaskList_returnsFullTaskList() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertNotNull(taskList.getTaskList());
    }


    @Test
    void addEvent() {
        taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void addLesson() {
        taskList = new TaskList();
        taskList.addLesson("Lesson", SAMPLE1, SAMPLE2, 0);
        Task lesson = new Lesson("Lesson", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), lesson.toString());
    }

    @Test
    void addDeadline() {
        taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
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
