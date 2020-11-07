package seedu.lifeasier.storage;

import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles file archiving in LifEasier.
 */
public class FileArchive {

    private static Logger logger = Logger.getLogger(NoteStorage.class.getName());
    private NoteList notes;
    private Ui ui;

    private static final String NOTE_SEPARATOR =
            "-------------------------------------------------------------------" + System.lineSeparator();

    public FileArchive(NoteList notes, Ui ui) {
        this.notes = notes;
        this.ui = ui;
    }

    /**
     * Creates new archive save file in archive directory and clears notes.
     *
     * @param archiveFilePath File path to the save directory.
     */
    public void handleDataArchiving(String archiveFilePath) {
        ArrayList<Note> notesList = notes.getNotes();
        Boolean isNotesEmpty = checkForEmptyNotes(notesList);

        logger.log(Level.INFO, "Check notes empty status: isNotesEmpty");
        if (isNotesEmpty) {
            ui.showNoDataToArchiveMessage();
            logger.log(Level.INFO, "Notes empty");
            return;
        }
        logger.log(Level.INFO, "Notes not empty");
        File archiveDirectory = new File(archiveFilePath);
        assert archiveDirectory.exists() : "Archive directory must exist";

        String archiveSaveFilePath = archiveFilePath + getCurrentDateTime() + "Archive.txt";
        try {
            File archiveSave = new File(archiveSaveFilePath);

            createArchiveSaveFile(archiveSave);
            writeDataToArchiveSaveFile(archiveSaveFilePath, notesList);
            clearNoteList(notesList);
        } catch (IOException e) {
            ui.showFileArchiveError();
        }
        ui.showArchiveEndMessage();
        ui.showArchiveEndInformationMessage();
        logger.log(Level.INFO, "Finish archiving process");
    }

    /**
     * Handles writing all data from current note list onto archive save file.
     *
     * @param archiveSaveFilePath File path of where to save archive data.
     * @param notesList ArrayList of notes to be archived.
     * @throws IOException When an error is encountered writing to the archive file.
     */
    protected void writeDataToArchiveSaveFile(String archiveSaveFilePath, ArrayList<Note> notesList)
            throws IOException {
        FileWriter fileWriter = new FileWriter(archiveSaveFilePath, true);

        assert notesList.size() > 0 : "NotesList cannot be empty when archiving";
        logger.log(Level.INFO, "Start archive file write");
        for (Note note : notesList) {
            String noteTitle = getNoteTitle(note);
            String noteBody = getNoteBody(note);
            fileWriter.write(NOTE_SEPARATOR);
            fileWriter.write(noteTitle);
            fileWriter.write(noteBody);
            fileWriter.write(NOTE_SEPARATOR);
            logger.log(Level.INFO, "Archive written for: " + noteTitle);
        }
        fileWriter.close();
        logger.log(Level.INFO, "End archive file write");
    }

    protected String getNoteBody(Note note) {
        return note.getDescription().trim() + System.lineSeparator();
    }

    protected String getNoteTitle(Note note) {
        return note.getTitle().trim() + System.lineSeparator();
    }

    /**
     * Creates a new archive save file if one does not exist yet.
     *
     * @param archiveSave Current file path of the archive save file.
     * @throws IOException When an error is encountered writing to the archive file.
     */
    protected void createArchiveSaveFile(File archiveSave) throws IOException {
        //Create new save if none exist
        if (!archiveSave.exists()) {
            logger.log(Level.INFO, "Creating new archive save");
            if (!archiveSave.createNewFile()) {
                logger.log(Level.WARNING, "Archive save file creation failed");
                throw new IOException();
            }
        }
        logger.log(Level.INFO, "Archive save created");
    }

    protected Boolean checkForEmptyNotes(ArrayList<Note> notesList) {
        return notesList.size() == 0;
    }

    /**
     * Gets the current date and time and formats it for return.
     *
     * @return Formatted date time string.
     */
    protected String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(FileCommand.DATE_TIME_FORMATTER);
        return formattedDateTime.replaceAll(" ", "T").replace(":", "");
    }

    /**
     * Removes all notes from the current noteList after archiving.
     *
     * @param notesList ArrayList where all notes are to be removed.
     */
    protected void clearNoteList(ArrayList<Note> notesList) {
        logger.log(Level.INFO, "Clearing notes");
        notesList.clear();
    }
}
