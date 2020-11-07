package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.ui.Ui;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {
    TaskList taskList;
    Ui ui;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private static final LocalDateTime SAMPLE1 = LocalDateTime.parse("12-12-20 12:00", DATE_TIME_FORMATTER);
    private static final LocalDateTime SAMPLE2 = LocalDateTime.parse("12-12-20 13:00", DATE_TIME_FORMATTER);

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

        LocalDateTime start = LocalDateTime.of(20, 11, 27, 12, 0);
        LocalDateTime end = LocalDateTime.of(20, 11, 27, 13, 0);
        int recurrences = 11;

        Lesson lesson = new Lesson(moduleCode, start, end, recurrences);

        taskList.addTask(lesson);

        taskList.updateTasks(LocalDate.of(20, 11, 29));

        LocalDate expected = LocalDate.of(20, 12, 4);

        assertTrue(((lesson.isHappeningOn(expected)) && (lesson.getRecurrences() == 10)));
    }

    @Test
    void updateTasks_outdatedTasks_deleted() {
        taskList = new TaskList();

        String moduleCode = "cg1111";
        LocalDateTime start1 = LocalDateTime.of(21, 11, 27, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(21, 11, 27, 13, 0);
        int recurrences = 0;

        String eventDescription = "my event";
        LocalDateTime start2 = LocalDateTime.of(21, 11, 28, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(21, 11, 28, 23, 0);

        String deadlineDescription = "my deadline";
        LocalDateTime by = LocalDateTime.of(21, 11, 28, 23, 59);

        Lesson lesson = new Lesson(moduleCode, start1, end1, recurrences);
        Event event = new Event(moduleCode, start2, end2, recurrences);
        Deadline deadline = new Deadline(moduleCode, by, recurrences);

        taskList.addTask(lesson);
        taskList.addTask(event);
        taskList.addTask(deadline);

        taskList.updateTasks(LocalDate.of(21, 11, 29));

        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void getTasksFromOneDay_sameDayTasks_returned() {
        taskList = new TaskList();

        String moduleCode = "cg1111";
        LocalDateTime start1 = LocalDateTime.of(20, 11, 27, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(20, 11, 27, 13, 0);
        int recurrences = 0;

        String eventDescription = "my event";
        LocalDateTime start2 = LocalDateTime.of(20, 11, 27, 22, 0);
        LocalDateTime end2 = LocalDateTime.of(20, 11, 27, 23, 0);

        String deadlineDescription = "my deadline";
        LocalDateTime by = LocalDateTime.of(20, 11, 27, 23, 59);

        Lesson lesson = new Lesson(moduleCode, start1, end1, recurrences);
        Event event = new Event(moduleCode, start2, end2, recurrences);
        Deadline deadline = new Deadline(moduleCode, by, recurrences);

        taskList.addTask(lesson);
        taskList.addTask(event);
        taskList.addTask(deadline);

        ArrayList<Task> tasksFromOneDay = taskList.getTasksFromOneDay(LocalDate.of(20, 11, 27));

        assertEquals(3, tasksFromOneDay.size());
    }

    @Test
    void getTaskList_returnsFullTaskList() {
        taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertNotNull(taskList.getTaskList());
    }


    @Test
    void addEvent() throws TaskDuplicateException, TaskPastException {
        taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void addLesson() throws TaskDuplicateException, TaskPastException {
        taskList = new TaskList();
        taskList.addLesson("Lesson", SAMPLE1, SAMPLE2, 0);
        Task lesson = new Lesson("Lesson", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), lesson.toString());
    }

    @Test
    void addDeadline() throws TaskDuplicateException, TaskPastException {
        taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void editTaskDescription() throws TaskPastException, TaskDuplicateException {
        taskList = new TaskList();
        ui = new Ui();
        InputStream sysInBackup = System.in;
        InputStream in = new ByteArrayInputStream("newDescription".getBytes());
        System.setIn(in);
        HashMap<Integer,Integer> taskMap = new HashMap<>();

        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        taskMap.put(1,0);
        taskList.editTaskDescription(1, ui);
        Task newEvent = new Event("newDescription", SAMPLE1, SAMPLE2);
        assertEquals(newEvent.toString(), taskList.getTask(0).toString());
        System.setIn(sysInBackup);
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
