package seedu.lifeasier.storage;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteStorageTest {

    private static final String EXPECTED_STRING_OUTPUT_NOTES =
            "Cats are awesome!=-=Cats are so cute, they are the best :D" + System.lineSeparator();
    private static final String EXPECTED_STRING_NOTES_READ =
            "Cats are awesome!=-=Cats are so cute, they are the best :D";
    private static final String TEST_NOTE_PATH = "testNotes.txt";

    private NoteStorage noteStorage;
    private NoteList notes;

    public NoteStorageTest() {
        this.notes = new NoteList();
        this.noteStorage = new NoteStorage(notes, TEST_NOTE_PATH);
    }

    @Test
    void convertNoteToString_newNote_correctStringOutput() {
        Note note = new Note("Cats are awesome!  ", "  Cats are so cute, they are the best :D  ");
        String output = noteStorage.convertNoteToString(note);
        assertEquals(EXPECTED_STRING_OUTPUT_NOTES, output);
    }

    @Test
    void writeToNoteSaveFile_newNote_writtenToFile() {
        Note note = new Note("Cats are awesome!  ", "  Cats are so cute, they are the best :D  ");
        notes.add(note);
        noteStorage.writeToNoteSaveFile();

        File testFile = new File(TEST_NOTE_PATH);
        String saveFileContents = null;
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(testFile);
        } catch (FileNotFoundException e) {
            System.out.println("Testing error - File not found");
        }

        assert fileReader != null;

        if (fileReader.hasNext()) {
            saveFileContents = fileReader.nextLine();
        }

        testFile.deleteOnExit();
        assertEquals(EXPECTED_STRING_NOTES_READ, saveFileContents);
    }

}