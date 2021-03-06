package test.java.seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.commands.EditNotesCommand;
import seedu.lifeasier.model.notes.Note;
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

class EditNotesCommandTest {
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
    void testEmptyList_searchTitle_emptyNoteList() {
        setUpStreams();
        Ui ui = new Ui();
        EditNotesCommand command = new EditNotesCommand("cats");
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
        new EditNotesCommand("dogs").execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);

        assertEquals(System.lineSeparator()
                    + THICK_SEPARATOR + System.lineSeparator()
                    + ui.colourTextRed("The title you inputted is not found...") + System.lineSeparator()
                    + THICK_SEPARATOR + System.lineSeparator() + System.lineSeparator(), outContent.toString());
        restoreStreams();
    }


    @Test
    void testOneMatch_searchTitle_noteEditTitle() {
        setUpStreams();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("dogs", "dogs are awesome");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        String input = "T\ncats goes meow";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();
        new EditNotesCommand("cats").execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        Note noteToCompare = new Note("cats goes meow", "i like cats");

        assertEquals(noteToCompare.toString(), notes.get(0).toString());

        restoreStreams();
    }

    @Test
    void testOneMatch_searchTitle_noteEditDescription() {
        setUpStreams();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("dogs", "dogs are awesome");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        String input = "D\ncats go meow";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();
        new EditNotesCommand("cats").execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        Note noteToCompare = new Note("cats", "cats go meow");

        assertEquals(noteToCompare.toString(), notes.get(0).toString());

        restoreStreams();
    }

    @Test
    void testMultipleMatch_searchTitle_noteEditTitle() {
        setUpStreams();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("catJAM", "catjamming");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        EditNotesCommand command = new EditNotesCommand("cat");
        String input = "1\nT\ncats go meow";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();
        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        Note noteToCompare = new Note("cats go meow", "i like cats");

        assertEquals(noteToCompare.toString(), notes.get(0).toString());

        restoreStreams();
    }

    @Test
    void testNoTitleInput_emptyTitle_noteEditTitle() {
        setUpStreams();
        Note note = new Note("cats", "i like cats");
        Note note1 = new Note("catJAM", "catjamming");
        NoteList notes = new NoteList();
        notes.add(note);
        notes.add(note1);
        EditNotesCommand command = new EditNotesCommand("");
        String input = "1\nT\ncats go meow";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Ui ui = new Ui();

        command.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
        Note noteToCompare = new Note("cats go meow", "i like cats");

        assertEquals(noteToCompare.toString(), notes.get(0).toString());
        restoreStreams();
    }

}