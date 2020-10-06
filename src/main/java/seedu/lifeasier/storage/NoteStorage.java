package seedu.lifeasier.storage;

import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The NoteStorage class handles the reading and writing of the save file for notes.
 */
public class NoteStorage {

    private static final String SAVE_DELIMITER = "=-=";

    private NoteList notes;
    private String filePathNotes;

    public NoteStorage(NoteList notes, String filePathNotes) {
        this.notes = notes;
        this.filePathNotes = filePathNotes;
    }

    /**
     * Reads and loads all saved note information.
     */
    protected void readNotesSave(String filePathNotes) {
        try {
            File saveFile = new File(filePathNotes);

            Scanner fileScanner = new Scanner(saveFile);
            createNoteList(fileScanner);

            System.out.println("Notes save file successfully loaded!");

        } catch (IOException e) {
            System.out.println("Something went wrong, unable to read from notes save file...");
        }

    }

    private void createNoteList(Scanner fileScanner) {
        while (fileScanner.hasNext()) {
            String noteInformation = fileScanner.nextLine();

            String[] noteComponents = noteInformation.split(SAVE_DELIMITER);
            String noteTitle = noteComponents[0];
            String noteDescription = noteComponents[1];

            notes.add(new Note(noteTitle, noteDescription));
        }
    }

    /**
     * Writes information from the notes onto the notes save file for storage when there is a change to the notes.
     *
     * @throws IOException When the file cannot be found or is corrupted.
     */
    public void writeToNoteSaveFile() {
        try {
            FileWriter fileWriter = new FileWriter(filePathNotes, true);
            clearSaveFile(filePathNotes);
            ArrayList<Note> noteList = notes.getNotes();
            //Append note information into save file for notes
            for (Note note : noteList) {
                String noteToSave = generateNoteSave(note);
                fileWriter.write(noteToSave);
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while saving your notes...");
        }
    }

    private String generateNoteSave(Note note) {
        return note.getTitle() + SAVE_DELIMITER + note.getDescription() + System.lineSeparator();
    }

    /**
     * Clears all data from the specified save file.
     *
     * @param filePath File path to which file to clear information.
     */
    private void clearSaveFile(String filePath) {
        try {
            FileWriter fileClear = new FileWriter(filePath);
            fileClear.write("");
            fileClear.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while clearing the file...");
        }
    }
}
