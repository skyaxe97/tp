package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.Event;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskDuplicateException;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.model.tasks.TaskPastException;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FreeTimeCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void isFreeTime_busyHour_false() {

        FreeTimeCommand command = new FreeTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some very long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 11, 10);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 12, 59);
        tasks.add(new Event(description, start, end));

        boolean isFreeTime = command.isFreeTime(11, tasks);

        assertFalse(isFreeTime);
    }

    @Test
    void isFreeTime_freeHour_true() {

        FreeTimeCommand command = new FreeTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();

        boolean isFreeTime = command.isFreeTime(11, tasks);

        assertTrue(isFreeTime);

    }

    @Test
    void getLongestFreeTime_busyDay_twoHoursFree() {

        FreeTimeCommand command = new FreeTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description1 = "Some very long activity";
        LocalDateTime start1 = LocalDateTime.of(2020, 10, 17, 11,0);
        LocalDateTime end1 = LocalDateTime.of(2020, 10, 17, 17, 0);
        tasks.add(new Event(description1, start1, end1));
        String description2 = "Another very long activity";
        LocalDateTime start2 = LocalDateTime.of(2020, 10, 17, 18, 0);
        LocalDateTime end2 = LocalDateTime.of(2020, 10, 17, 23, 0);
        tasks.add(new Event(description2, start2, end2));

        int[] longestFreeTime = command.getLongestFreeTime(tasks);

        assertEquals(4, (longestFreeTime[1] - longestFreeTime[0]));
    }

    @Test
    void executeFreeTimeCommand_freeDay_6HoursFree() throws TaskDuplicateException, TaskPastException {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        TaskList tasks = new TaskList();
        NoteHistory noteHistory = new NoteHistory();
        FileStorage storage = new FileStorage("saveFileTasks.txt",
                "saveFileNotes.txt", ui, notes, tasks, noteHistory);
        Parser parser = new Parser();
        TaskHistory taskHistory = new TaskHistory();

        FreeTimeCommand command = new FreeTimeCommand();

        LocalDate today = LocalDate.now();

        LocalDateTime start1 = today.atTime(10, 0);
        LocalDateTime end1 = today.atTime(12, 0);
        LocalDateTime start2 = today.atTime(18, 0);
        LocalDateTime end2 = today.atTime(23, 0);


        tasks.addEvent("first event", start1, end1, 0);
        tasks.addEvent("second event", start2, end2, 0);

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals((System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator()
                + ui.colourTextGreen("You have 6 hours of free time between 12:00 and 18:00!")
                + System.lineSeparator()
                + ui.colourTextGreen("You can try scheduling something in this time!") + System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator() + System.lineSeparator()), outContent.toString());

        restoreStreams();
    }

    @Test
    void executeFreeTimeCommand_busyDay_0HoursFree() throws TaskDuplicateException, TaskPastException {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        TaskList tasks = new TaskList();
        NoteHistory noteHistory = new NoteHistory();
        FileStorage storage = new FileStorage("saveFileTasks.txt",
                "saveFileNotes.txt", ui, notes, tasks, noteHistory);
        Parser parser = new Parser();
        TaskHistory taskHistory = new TaskHistory();

        FreeTimeCommand command = new FreeTimeCommand();

        LocalDate today = LocalDate.now();

        LocalDateTime start1 = today.atTime(6, 0);
        LocalDateTime end1 = today.atTime(13, 30);
        LocalDateTime start2 = today.atTime(14, 0);
        LocalDateTime end2 = today.atTime(23, 30);

        tasks.addEvent("first event", start1, end1, 0);
        tasks.addEvent("second event", start2, end2, 0);

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals((System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator()
                + ui.colourTextRed("Unfortunately you have no free time today!") + System.lineSeparator()
                + ui.colourTextRed("You might want to relax a little!") + System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator() + System.lineSeparator()), outContent.toString());

        restoreStreams();
    }


}
