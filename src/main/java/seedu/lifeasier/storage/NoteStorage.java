package seedu.lifeasier.storage;

import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.ui.Ui;

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
    private FileCommand fileCommand;
    private Ui ui;

    public NoteStorage() {
    }

    public NoteStorage(NoteList notes, String filePathNotes) {
        this.notes = notes;
        this.filePathNotes = filePathNotes;
        this.fileCommand = new FileCommand();
        this.ui = new Ui();
    }

    /**
     * Reads and loads all saved note information.
     *
     * @param filePathNotes File object containing the file path of the notes save file.
     */
    protected void readNotesSave(String filePathNotes) {
        try {
            File saveFile = new File(filePathNotes);

            Scanner fileScanner = new Scanner(saveFile);
            createNoteList(fileScanner);

        } catch (IOException e) {
            ui.showFileReadError();
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
            fileCommand.clearSaveFile(filePathNotes);
            ArrayList<Note> noteList = notes.getNotes();
            //Append note information into save file for notes
            for (Note note : noteList) {
                String noteToSave = convertNoteToString(note);
                fileWriter.write(noteToSave);
            }
            fileWriter.close();
        } catch (IOException e) {
            ui.showFileWriteError();
        }
    }

    protected String convertNoteToString(Note note) {
        return note.getTitle().trim() + SAVE_DELIMITER + note.getDescription().trim() + System.lineSeparator();
    }

}
