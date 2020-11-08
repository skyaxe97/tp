package test.java.seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.commands.AddNotesCommand;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddNotesCommandTest {
    private Ui ui = new Ui();
    private NoteList notes = new NoteList();
    private TaskList tasks = new TaskList();
    private NoteHistory noteHistory = new NoteHistory();
    private FileStorage storage = new FileStorage("saveFileTasks.txt",
            "saveFileNotes.txt", ui, notes, tasks, noteHistory);
    private Parser parser = new Parser();
    private TaskHistory taskHistory = new TaskHistory();

    public static final String THICK_SEPARATOR = "===================================================================="
            + "===========================================";

    public static final String THIN_SEPARATOR  = "--------------------------------------------------------------------"
            + "-------------------------------------------";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    void testCheckForEmpty_notEmptyInput_returnInput() {
        setUpStreams();
        String input = "test test";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();
        AddNotesCommand command = new AddNotesCommand("cats");
        assertEquals(input, command.checkForEmpty(ui));
        restoreStreams();
    }

    @Test
    void testAddNotesCommand_validTitle_noteAdded() {
        setUpStreams();
        String input = "Test Description";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();
        AddNotesCommand command = new AddNotesCommand("Test Title");
        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        assertEquals(System.lineSeparator() + THIN_SEPARATOR
                + System.lineSeparator() + ui.colourTextCyan("Alright! Please fill in your notes:")
                + System.lineSeparator() + System.lineSeparator() + THICK_SEPARATOR
                + System.lineSeparator() + ui.colourTextGreen("Ok! I've taken note of this note!")
                + System.lineSeparator() + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(),
                outContent.toString());

        restoreStreams();
    }

    @Test
    void testAddNotesCommand_noTitle_noteAdded() {
        setUpStreams();
        String input = "Test Title\nTest Description";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();
        AddNotesCommand command = new AddNotesCommand("");
        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        assertEquals(System.lineSeparator() + THIN_SEPARATOR
                        + System.lineSeparator() + ui.colourTextCyan("Please put in a title:")
                        + System.lineSeparator() + System.lineSeparator() + THIN_SEPARATOR
                        + System.lineSeparator() + ui.colourTextCyan("Alright! Please fill in your notes:")
                        + System.lineSeparator() + System.lineSeparator() + THICK_SEPARATOR
                        + System.lineSeparator() + ui.colourTextGreen("Ok! I've taken note of this note!")
                        + System.lineSeparator() + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(),
                outContent.toString());

        restoreStreams();
    }
}