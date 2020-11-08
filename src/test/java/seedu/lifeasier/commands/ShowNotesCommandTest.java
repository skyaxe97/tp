package test.java.seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.commands.ShowNotesCommand;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShowNotesCommandTest {
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
    void testEmptyList_searchTitle_emptyNoteList() {
        setUpStreams();
        Ui ui = new Ui();
        ShowNotesCommand command = new ShowNotesCommand("cats");
        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals(System.lineSeparator()
                        + THICK_SEPARATOR + System.lineSeparator()
                        + ui.colourTextGreen("There's no Notes!") + System.lineSeparator()
                        + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(),
                        outContent.toString());
        restoreStreams();
    }

    @Test
    void testNoMatch_searchTitle_noMatchFound() {
        setUpStreams();
        Ui ui = new Ui();
        NoteList notes = new NoteList();
        Note note = new Note("cats", "i like cats");
        notes.add(note);
        new ShowNotesCommand("dogs").execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals(System.lineSeparator()
                + THICK_SEPARATOR + System.lineSeparator()
                + ui.colourTextRed("The title you inputted is not found...") + System.lineSeparator()
                + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(), outContent.toString());
        restoreStreams();
    }


    @Test
    void testOneMatch_searchTitle_noteWithMatchingTitle() {
        setUpStreams();
        Ui ui = new Ui();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("dogs", "dogs are awesome");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        new ShowNotesCommand("cats").execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals(System.lineSeparator()
                + THICK_SEPARATOR + System.lineSeparator()
                + ui.colourTextGreen("Here is your note:") + System.lineSeparator()
                + note + System.lineSeparator()
                + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(), outContent.toString());
        restoreStreams();
    }

    @Test
    void testMultipleMatch_searchTitle_allNotesWithMatchingTitle() {
        setUpStreams();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("catJAM", "catjamming");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        ShowNotesCommand command = new ShowNotesCommand("cat");
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        int i = 1;
        assertEquals(System.lineSeparator() + THIN_SEPARATOR + System.lineSeparator()
                + ui.colourTextCyan("Multiple matches found! Please select the one you are looking for:")
                + System.lineSeparator() + i++ + ". " + note.getTitle() + "\n"
                + System.lineSeparator() + i + ". " + note1.getTitle() + "\n"
                + System.lineSeparator()
                + System.lineSeparator() + THICK_SEPARATOR + System.lineSeparator()
                + ui.colourTextGreen("Here is your note:") + System.lineSeparator()
                + note + System.lineSeparator()
                + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(), outContent.toString());
        restoreStreams();
    }

    @Test
    void testNoTitleInput_emptyTitle_allNotes() {
        setUpStreams();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("catJAM", "catjamming");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        ShowNotesCommand command = new ShowNotesCommand("");
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        int i = 1;

        assertEquals(System.lineSeparator() + THIN_SEPARATOR + System.lineSeparator()
                + ui.colourTextCyan("Please select the notes you want to view:")
                + System.lineSeparator() + i++ + ". " + note.getTitle()
                + System.lineSeparator() + i + ". " + note1.getTitle()
                + System.lineSeparator() + System.lineSeparator() + THICK_SEPARATOR + System.lineSeparator()
                + ui.colourTextGreen("Here is your note:") + System.lineSeparator()
                + note + System.lineSeparator()
                + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(), outContent.toString());
        restoreStreams();
    }
}