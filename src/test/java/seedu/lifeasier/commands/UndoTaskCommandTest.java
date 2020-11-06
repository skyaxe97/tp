package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.Deadline;
import seedu.lifeasier.model.tasks.Event;
import seedu.lifeasier.model.tasks.Lesson;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileCommand;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UndoTaskCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final TaskList testTaskList = new TaskList();
    private final TaskHistory testTaskHistory = new TaskHistory();
    private final FileCommand fileCommand = new FileCommand();

    public static final String TEST_FILEPATH = "testSave.txt";

    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");
    private final Deadline testDeadlineToEdit = new Deadline("testDeadline", sampleTime1, 0);
    private final Event testEventToDelete = new Event("testEvent", sampleTime1, sampleTime2, 1);
    private final Lesson testLessonToEdit = new Lesson("testLesson", sampleTime1, sampleTime2, 3);

    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    void setUpTaskList() {
        testTaskList.addTask(testDeadlineToEdit);
        testTaskList.addTask(testEventToDelete);
        testTaskList.addTask(testLessonToEdit);
    }

    void resetTaskList() {
        testTaskList.getTaskList().clear();
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
        NoteHistory noteHistory = new NoteHistory();
        FileStorage storage = new FileStorage(TEST_FILEPATH,
                TEST_FILEPATH, ui, notes, testTaskList);
        Parser parser = new Parser();

        UndoTaskCommand command = new UndoTaskCommand();

        assertEquals(0, testTaskHistory.getChangeCount());

        command.execute(ui, notes, testTaskList, storage, parser, noteHistory, testTaskHistory);
        assertEquals(ui.colourTextGreen("Nothing to undo!")
                + System.lineSeparator()
                + "==================================================================================================="
                + "============" + System.lineSeparator(),
                outContent.toString());

        restoreStreams();
    }

    @Test
    void executeAndGetLastTask_TaskHistoryWithThreeTasks_LastTaskIsEvent() {
        File testSave = new File(TEST_FILEPATH);
        try {
            if (!testSave.exists()) {
                if (testSave.createNewFile()) {
                    System.out.println("New save created for testing");
                }
            }
            fileCommand.clearSaveFile(TEST_FILEPATH);

            setUpStreams();
            setUpTaskList();

            Task copyTestDeadline = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 0);
            Task copyTestEvent = testTaskHistory.getCurrCopyOfTaskToDelete(testTaskList, 1);
            Task copyTestLesson = testTaskHistory.getCurrCopyOfTaskToEdit(testTaskList, 2);

            testTaskHistory.pushOldCopy(copyTestDeadline);
            testTaskHistory.pushOldCopy(copyTestEvent);
            testTaskHistory.pushOldCopy(copyTestLesson);

            Ui ui = new Ui();
            NoteList notes = new NoteList();
            NoteHistory noteHistory = new NoteHistory();
            FileStorage storage = new FileStorage(TEST_FILEPATH,
                    TEST_FILEPATH, ui, notes, testTaskList);
            Parser parser = new Parser();

            UndoTaskCommand command = new UndoTaskCommand();

            assertEquals(3, testTaskHistory.getChangeCount());
            assertEquals(copyTestLesson, testTaskHistory.getLastTask());

            command.execute(ui, notes, testTaskList, storage, parser, noteHistory, testTaskHistory);
            assertEquals(ui.colourTextGreen("This task has been reverted back to its previous version!")
                            + System.lineSeparator() + testLessonToEdit.toString()
                            + System.lineSeparator()
                            + "========================================================================================"
                            + "=======================" + System.lineSeparator(),
                    outContent.toString());

            assertEquals(2, testTaskHistory.getChangeCount());
            assertEquals(copyTestEvent, testTaskHistory.getLastTask());

            resetTaskHistory();
            resetTaskList();
            restoreStreams();
        } catch (IOException e) {
            System.out.println("Testing error - Unable to read/write to file");
        }
    }

}