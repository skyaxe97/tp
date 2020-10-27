package seedu.lifeasier.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileArchiveTest {

    public static final String TEST_FILEPATH = "testSave.txt";
    private static final String NOTE_SEPARATOR = "-------------------------------------------------------------------";

    private FileArchive fileArchive;
    private NoteList notes;
    private FileCommand fileCommand;

    public FileArchiveTest() {
        this.notes = new NoteList();
        this.fileArchive = new FileArchive(notes, new Ui());
        this.fileCommand = new FileCommand();
    }

    @Test
    void writeDataToArchiveSaveFile_testData_writeSuccess() {
        Note testNote = new Note("Cats are cute!", "I Love Cats");
        notes.getNotes().clear();
        notes.add(testNote);

        fileCommand.clearSaveFile(TEST_FILEPATH);
        try {
            fileArchive.writeDataToArchiveSaveFile(TEST_FILEPATH, notes.getNotes());
            File testFile = new File(TEST_FILEPATH);
            Scanner fileReader = new Scanner(testFile);
            ArrayList<String> fileContents = new ArrayList<>();

            while (fileReader.hasNext()) {
                fileContents.add(fileReader.nextLine());
            }

            Assertions.assertAll(
                () -> assertEquals(NOTE_SEPARATOR, fileContents.get(0)),
                () -> assertEquals("Cats are cute!", fileContents.get(1)),
                () -> assertEquals("I Love Cats", fileContents.get(2)),
                () -> assertEquals(NOTE_SEPARATOR, fileContents.get(3))
            );

        } catch (IOException e) {
            System.out.println("Testing error - There was an error writing to the file");
        }
    }


    @Test
    void getNoteBody_inputWithWhiteSpace_correctReturn() {
        Note testNote = new Note("Test 1     ", " Test Body  ");
        assertEquals("Test Body" + System.lineSeparator(), fileArchive.getNoteBody(testNote));
    }

    @Test
    void getNoteTitle_inputWithWhiteSpace_correctReturn() {
        Note testNote = new Note("Test 1     ", " Test Body  ");
        assertEquals("Test 1" + System.lineSeparator(), fileArchive.getNoteTitle(testNote));
    }

    @Test
    void checkForEmptyNotes_noExistingNotes_true() {
        Note testNote = new Note("Cats are cute!", "I Love Cats");
        notes.add(testNote);
        notes.getNotes().clear();

        ArrayList<Note> noteList = notes.getNotes();
        assertTrue(fileArchive.checkForEmptyNotes(noteList));
    }

    @Test
    void checkForEmptyNotes_existingNotes_false() {
        Note testNote = new Note("Cats are cute!", "I Love Cats");
        notes.getNotes().clear();
        notes.add(testNote);

        ArrayList<Note> noteList = notes.getNotes();
        assertFalse(fileArchive.checkForEmptyNotes(noteList));
    }

    @Test
    void clearNoteList_existingNotes_notesCleared() {
        notes.getNotes().clear();
        Note testNote = new Note("Cats are cute!", "I Love Cats");
        notes.add(testNote);

        fileArchive.clearNoteList(notes.getNotes());
        assertEquals(0, notes.getNotes().size());
    }

}