package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.Deadline;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskDuplicateException;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.model.tasks.TaskPastException;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditDeadlineCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;

    public static final String TEST_FILEPATH = "testSave.txt";

    private final LocalDateTime sampleTime1 = LocalDateTime.parse("2020-11-11T11:11");
    private final LocalDateTime sampleTime2 = LocalDateTime.parse("2020-12-12T12:12");

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
    void execute_validDeadlineAndEditingName_deadlineNameEdited() throws TaskPastException, TaskDuplicateException {
        setUpStreams("1\n" + "1\n" + "newDeadline");

        Ui ui = new Ui();
        NoteList notes = new NoteList();
        NoteHistory noteHistory = new NoteHistory();
        TaskList testTaskList = new TaskList();
        TaskHistory testTaskHistory = new TaskHistory();
        FileStorage storage = new FileStorage(TEST_FILEPATH, TEST_FILEPATH, ui, notes, testTaskList, noteHistory);
        Parser parser = new Parser();

        EditDeadlineCommand command = new EditDeadlineCommand("");
        testTaskList.addDeadline("oldDeadline", sampleTime1, 1);
        Task newDeadline = new Deadline("newDeadline", sampleTime1, 1);

        command.execute(ui, notes, testTaskList, storage, parser, noteHistory, testTaskHistory);
        assertEquals(newDeadline.toString(), testTaskList.getTask(0).toString());

        restoreStreams();
    }

    @Test
    void execute_validDeadlineAndEditingTime_deadlineNameEdited() throws TaskPastException, TaskDuplicateException {
        setUpStreams("1\n" + "2\n" + "/by 12-12-20 12:12");

        Ui ui = new Ui();
        NoteList notes = new NoteList();
        NoteHistory noteHistory = new NoteHistory();
        TaskList testTaskList = new TaskList();
        TaskHistory testTaskHistory = new TaskHistory();
        FileStorage storage = new FileStorage(TEST_FILEPATH, TEST_FILEPATH, ui, notes, testTaskList, noteHistory);
        Parser parser = new Parser();

        EditDeadlineCommand command = new EditDeadlineCommand("");
        testTaskList.addDeadline("testDeadline", sampleTime1, 1);
        Task newDeadline = new Deadline("testDeadline", sampleTime2, 1);

        command.execute(ui, notes, testTaskList, storage, parser, noteHistory, testTaskHistory);
        assertEquals(newDeadline.toString(), testTaskList.getTask(0).toString());

        restoreStreams();
    }

    @Test
    void execute_taskNotFound_noAction() throws TaskPastException, TaskDuplicateException {
        setUpStreams("");

        Ui ui = new Ui();
        NoteList notes = new NoteList();
        NoteHistory noteHistory = new NoteHistory();
        TaskList testTaskList = new TaskList();
        TaskHistory testTaskHistory = new TaskHistory();
        FileStorage storage = new FileStorage(TEST_FILEPATH, TEST_FILEPATH, ui, notes, testTaskList, noteHistory);
        Parser parser = new Parser();

        EditDeadlineCommand command = new EditDeadlineCommand("garbage");
        testTaskList.addDeadline("oldDeadline", sampleTime1, 1);

        command.execute(ui, notes, testTaskList, storage, parser, noteHistory, testTaskHistory);
        assertEquals(System.lineSeparator()
                + Ui.THIN_SEPARATOR + System.lineSeparator()
                + ui.colourTextCyan("Here are all your matching deadlines:") + System.lineSeparator()
                + System.lineSeparator()
                + Ui.THICK_SEPARATOR + System.lineSeparator()
                + ui.colourTextRed("Sorry! There is no deadline matching your query. Please "
                + "re-enter your command.") + System.lineSeparator()
                + Ui.THICK_SEPARATOR + System.lineSeparator()
                + System.lineSeparator(),
            outContent.toString());

        restoreStreams();
    }
}