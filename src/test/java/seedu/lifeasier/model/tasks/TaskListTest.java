package seedu.lifeasier.model.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private static final LocalDateTime SAMPLE1 = LocalDateTime.parse("12-12-20 12:00", DATE_TIME_FORMATTER);
    private static final LocalDateTime SAMPLE2 = LocalDateTime.parse("12-12-20 13:00", DATE_TIME_FORMATTER);
    private static final LocalDateTime SAMPLE3 = LocalDateTime.parse("12-12-20 14:00", DATE_TIME_FORMATTER);

    public void setUpStreams(String input) {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    void getTaskCount_returnTaskCountAsInt() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void getTask_outOfBoundIndex_throwsException() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(2));
    }

    @Test
    void getTask_invalidIndex_throwsException() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(-1));
    }

    //@@author daniellimzj

    @Test
    void updateTasks_outdatedRecurringLesson_movedOneWeek() {
        TaskList taskList = new TaskList();
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
        TaskList taskList = new TaskList();

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
        TaskList taskList = new TaskList();

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

    //@@author

    @Test
    void getTaskList_returnsFullTaskList() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Event("EXAMPLE", SAMPLE1, SAMPLE2));
        assertNotNull(taskList.getTaskList());
    }


    @Test
    void addEvent() throws TaskDuplicateException, TaskPastException {
        TaskList taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void addLesson() throws TaskDuplicateException, TaskPastException {
        TaskList taskList = new TaskList();
        taskList.addLesson("Lesson", SAMPLE1, SAMPLE2, 0);
        Task lesson = new Lesson("Lesson", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), lesson.toString());
    }

    @Test
    void addDeadline() throws TaskDuplicateException, TaskPastException {
        TaskList taskList = new TaskList();
        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        Task event = new Event("Event", SAMPLE1, SAMPLE2);
        assertEquals(taskList.getTask(0).toString(), event.toString());
    }

    @Test
    void editTaskDescription_validInput_taskDescriptionEdited() throws TaskPastException, TaskDuplicateException,
        TaskNotFoundException {
        TaskList taskList = new TaskList();

        String newDescription = "newDescription";
        setUpStreams(newDescription);
        Ui ui = new Ui();

        taskList.addEvent("Event", SAMPLE1, SAMPLE2, 0);
        taskList.printMatchingTasks("event", "", ui);
        taskList.editTaskDescription(1, ui);
        Task newEvent = new Event("newDescription", SAMPLE1, SAMPLE2);
        assertEquals(newEvent.toString(), taskList.getTask(0).toString());

        restoreStreams();
    }

    @Test
    void editTaskDescription_emptyInput_promptForAnotherInput() throws TaskNotFoundException {

        String newDescription = "\nnewDescription";
        setUpStreams(newDescription);
        Ui ui = new Ui();

        TaskList taskList = new TaskList();
        Task originalEvent = new Event("Event", SAMPLE1, SAMPLE2, 0);
        taskList.addTask(originalEvent);
        taskList.printMatchingTasks("event", "", ui);
        taskList.editTaskDescription(1, ui);
        Task changedEvent = new Event("newDescription", SAMPLE1, SAMPLE2, 0);
        assertEquals(taskList.getTask(0).toString(), changedEvent.toString());

        restoreStreams();
    }

    @Test
    void editModuleCode_validInput_lessonModuleCodeEdited() throws TaskPastException, TaskDuplicateException,
        TaskNotFoundException {
        TaskList taskList = new TaskList();

        String newDescription = "CS2040C";
        setUpStreams(newDescription);
        Ui ui = new Ui();

        taskList.addLesson("CS1010", SAMPLE1, SAMPLE2, 0);
        taskList.printMatchingTasks("lesson", "", ui);
        taskList.editModuleCode(1, ui, newDescription);
        Task newLesson = new Lesson("CS2040C", SAMPLE1, SAMPLE2);
        assertEquals(newLesson.toString(), taskList.getTask(0).toString());

        restoreStreams();
    }

    @Test
    void editLessonTime_validTimeInput_lessonTimeEdited() throws TaskNotFoundException, TaskPastException,
        TaskDuplicateException {


        LocalDateTime[] newTimes = new LocalDateTime[2];
        newTimes[0] = SAMPLE1;
        newTimes[1] = SAMPLE2;

        setUpStreams("");
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.addLesson("CS1231", SAMPLE2, SAMPLE3, 0);
        taskList.printMatchingTasks("lesson", "", ui);
        taskList.editLessonTime(1, ui, newTimes);

        Assertions.assertAll(
            () -> assertEquals(taskList.getTask(0).getStart(), SAMPLE1),
            () -> assertEquals(taskList.getTask(0).getEnd(), SAMPLE2)
        );

        restoreStreams();
    }


    @Test
    void editEventTime_validTimeInput_eventTimeEdited() throws TaskNotFoundException, TaskPastException,
        TaskDuplicateException {

        LocalDateTime[] newTimes = new LocalDateTime[2];
        newTimes[0] = SAMPLE1;
        newTimes[1] = SAMPLE2;

        setUpStreams("");
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.addEvent("Concert", SAMPLE2, SAMPLE3, 0);
        taskList.printMatchingTasks("event", "", ui);
        taskList.editEventTime(1, ui, newTimes);

        Assertions.assertAll(
            () -> assertEquals(taskList.getTask(0).getStart(), SAMPLE1),
            () -> assertEquals(taskList.getTask(0).getEnd(), SAMPLE2)
        );

        restoreStreams();
    }

    @Test
    void editDeadlineTime_validTimeInput_deadlineTimeEdited() throws TaskNotFoundException, TaskPastException,
        TaskDuplicateException {
        TaskList taskList = new TaskList();

        LocalDateTime[] newTimes = new LocalDateTime[2];
        newTimes[0] = SAMPLE1;

        setUpStreams("");
        Ui ui = new Ui();
        taskList.addDeadline("Homework", SAMPLE2, 0);
        taskList.printMatchingTasks("deadline", "", ui);
        taskList.editDeadlineTime(1, ui, newTimes);

        assertEquals(taskList.getTask(0).getStart(), SAMPLE1);

        restoreStreams();
    }

    @Test
    void deleteTask_validIndex_taskDeleted() throws TaskPastException, TaskDuplicateException,
        TaskNotFoundException {
        TaskList taskList = new TaskList();

        setUpStreams("");
        Ui ui = new Ui();

        taskList.addDeadline("Homework", SAMPLE2, 0);
        taskList.printMatchingTasks("deadline", "", ui);
        taskList.deleteTask(1, ui);

        assert (taskList.getTaskList().isEmpty());

        restoreStreams();
    }

    @Test
    void checkForIndexOutOfBounds() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.checkForIndexOutOfBounds(-1));
    }
}