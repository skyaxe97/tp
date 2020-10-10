package test.java.seedu.lifeasier.commands;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.notes.Note;

import static org.junit.jupiter.api.Assertions.*;

class ShowNotesCommandTest {
    private NoteList notes = new NoteList();

    @Test
    void execute() {
        notes.add(new Note("cat", "i like cats"));
        notes.add(new Note("dog", "i like dogs"));
        assertEquals(2, notes.size());
    }
}