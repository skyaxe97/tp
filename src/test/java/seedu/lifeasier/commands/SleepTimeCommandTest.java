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

class SleepTimeCommandTest {

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
    void isBusyTime_busyHour_true() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 10, 10);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 12, 59);
        tasks.add(new Event(description, start, end));

        boolean isBusyTime = command.isBusyTime(11, tasks);

        assertTrue(isBusyTime);
    }

    @Test
    void isBusyTime_freeHour_false() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 11, 10);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 12, 59);
        tasks.add(new Event(description, start, end));

        boolean isBusyTime = command.isBusyTime(22, tasks);

        assertFalse(isBusyTime);
    }

    @Test
    void getLatestBusyTime_busyAllDay_22() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some super long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 13, 0);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 23, 0);
        tasks.add(new Event(description, start, end));

        int latestBusyTime = command.getLatestBusyTime(tasks);

        assertEquals(22, latestBusyTime);
    }

    @Test
    void getLatestBusyTime_noTasks_minus1() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();

        int latestBusyTime = command.getLatestBusyTime(tasks);

        assertEquals(-1, latestBusyTime);
    }

    @Test
    void getEarliestBusyTime_notBusy_18() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some super long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 18, 0);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 20, 0);
        tasks.add(new Event(description, start, end));

        int earliestBusyTime = command.getEarliestBusyTime(tasks);

        assertEquals(18, earliestBusyTime);
    }

    @Test
    void getEarliestBusyTime_noTasks_25() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();

        int earliestBusyTime = command.getEarliestBusyTime(tasks);

        assertEquals(25, earliestBusyTime);
    }

    @Test
    void executeSleepTimeCommand_busyDay_6HoursSleep() throws TaskDuplicateException, TaskPastException {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        TaskList tasks = new TaskList();
        NoteHistory noteHistory = new NoteHistory();
        TaskHistory taskHistory = new TaskHistory();
        FileStorage storage = new FileStorage("saveFileTasks.txt",
                "saveFileNotes.txt", ui, notes, tasks, noteHistory);
        Parser parser = new Parser();

        SleepTimeCommand command = new SleepTimeCommand();

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        LocalDateTime start1 = today.atTime(10, 0);
        LocalDateTime end1 = today.atTime(23, 0);
        LocalDateTime start2 = tomorrow.atTime(6, 0);
        LocalDateTime end2 = tomorrow.atTime(12, 0);

        tasks.addEvent("first event", start1, end1, 0);
        tasks.addEvent("second event", start2, end2, 0);

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals(System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator()
                + (ui.colourTextGreen("You have nothing on from 23:00 today to 5:00 tomorrow!")
                + System.lineSeparator() + ui.colourTextGreen("You can sleep for up to 6 hours!")
                + System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator() + System.lineSeparator()),
                outContent.toString());

        restoreStreams();
    }

    @Test
    void executeSleepTimeCommand_freeDay_16HoursSleep() {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        TaskList tasks = new TaskList();
        NoteHistory noteHistory = new NoteHistory();
        TaskHistory taskHistory = new TaskHistory();
        FileStorage storage = new FileStorage("saveFileTasks.txt",
                "saveFileNotes.txt", ui, notes, tasks, noteHistory);
        Parser parser = new Parser();

        SleepTimeCommand command = new SleepTimeCommand();

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals(System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator()
                + (ui.colourTextGreen("You have nothing on for today and tomorrow!") + System.lineSeparator()
                +  ui.colourTextGreen("You can sleep for the recommended 8 hours or longer!")
                + System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator() + System.lineSeparator()),
                outContent.toString());

        restoreStreams();
    }

}
