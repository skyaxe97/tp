package test.java.seedu.lifeasier.model.notes;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteList;

import static org.junit.jupiter.api.Assertions.*;

class NoteListTest {
    NoteList noteList;

    @Test
    void testAdd() {
        noteList = new NoteList();
        Note note = new Note("cats","i love cats");
        noteList.add(note);
        assertEquals("Title: cats\n\ni love cats\n", noteList.get(0).toString());
    }

    @Test
    void testGet_correctIndex_correctNotes() {
        noteList = new NoteList();
        Note note = new Note("cats","i love cats");
        Note note1 = new Note("dogs", "dogs too!");
        noteList.add(note);
        noteList.add(note1);
        assertEquals(noteList.get(0).toString(), note.toString());
    }

    @Test
    void testGet_incorrectIndex_exceptionThrown() {
        noteList = new NoteList();
        Note note = new Note("cats","i love cats");
        noteList.add(note);
        assertThrows(IndexOutOfBoundsException.class, () -> noteList.get(1));
    }

    @Test
    void testSize() {
        noteList = new NoteList();
        Note note = new Note("cats","i love cats");
        noteList.add(note);
        assertEquals(1, noteList.size());
    }

    @Test
    void remove() {
        noteList = new NoteList();
        Note note = new Note("cats","i love cats");
        noteList.add(note);
        noteList.remove(0);
        assertEquals(0, noteList.size());
    }
}