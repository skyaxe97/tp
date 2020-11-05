package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.Deadline;
import seedu.lifeasier.model.tasks.Event;
import seedu.lifeasier.model.tasks.Lesson;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UndoTaskCommandTest {
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
    private final TaskHistory testTaskHistory = new TaskHistory();

    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");
    private final Deadline testDeadline = new Deadline("testDeadline", sampleTime1, 0);
    private final Event testEvent = new Event("testEvent", sampleTime1, sampleTime2, 1);
    private final Lesson testLesson = new Lesson("testLesson", sampleTime1, sampleTime2, 3);

    void setUpTaskHistory() {
        testTaskHistory.pushOldCopy(testDeadline);
        testTaskHistory.pushOldCopy(testEvent);
        testTaskHistory.pushOldCopy(testLesson);
    }

    void resetTaskHistory() {
        while (testTaskHistory.getChangeCount() > 0) {
            testTaskHistory.popLastTask();
        }
    }

    @Test
    void execute_emptyTaskHistory_invalidUndoAction() {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        TaskList tasks = new TaskList();
        NoteHistory noteHistory = new NoteHistory();
        FileStorage storage = new FileStorage("saveFileTasks.txt",
                "saveFileNotes.txt", ui, notes, tasks);
        Parser parser = new Parser();

        UndoTaskCommand command = new UndoTaskCommand();

        assertEquals(0, testTaskHistory.getChangeCount());

        command.execute(ui, notes, tasks, storage, parser, noteHistory, testTaskHistory);
        assertEquals(ui.colourTextGreen("Nothing to undo!") +
                System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator(),
                outContent.toString());

        restoreStreams();
    }

    @Test
    void executeAndGetLastTask_TaskHistoryWithThreeTasks_Event() {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        TaskList tasks = new TaskList();
        NoteHistory noteHistory = new NoteHistory();
        FileStorage storage = new FileStorage("saveFileTasks.txt",
                "saveFileNotes.txt", ui, notes, tasks);
        Parser parser = new Parser();

        UndoTaskCommand command = new UndoTaskCommand();

        assertEquals(3, testTaskHistory.getChangeCount());
        assertEquals(testLesson, testTaskHistory.getLastTask());

        command.execute(ui, notes, tasks, storage, parser, noteHistory, testTaskHistory);

        assertEquals(2, testTaskHistory.getChangeCount());
        assertEquals(testEvent, testTaskHistory.getLastTask());

        restoreStreams();
    }

}