package test.java.seedu.lifeasier.notes;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.notes.Note;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Test
    void getTitle() {
        assertEquals("cat", new Note("cat", "i like cats").getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("i like cats", new Note("cat", "i like cats").getDescription());
    }

    @Test
    void testToString() {
        assertEquals("Title: cat\n\ni like cats\n", new Note("cat", "i like cats" ).toString());
    }
}