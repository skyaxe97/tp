package seedu.lifeasier.storage;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.IOException;

/**
 * The FileStorage class will handle actions related to the reading, writing and creation of the
 * LifEasier save files.
 */
public class FileStorage {

    private static final String DIRECTORY_PATH = "/LifEasierSaves/";

    private String filePathTasks;
    private String filePathNotes;
    private Ui ui;
    private NoteStorage noteStorage;
    private TaskStorage taskStorage;

    public FileStorage(String fileNameTasks, String fileNameNotes, Ui ui, NoteList notes, TaskList tasks) {
        this.filePathTasks = DIRECTORY_PATH + fileNameTasks;
        this.filePathNotes = DIRECTORY_PATH + fileNameNotes;
        this.ui = ui;
        this.noteStorage = new NoteStorage(notes, filePathNotes);
        this.taskStorage = new TaskStorage(tasks, filePathTasks);
    }

    /**
     * Reads and loads the stored information on the save file. If save files are missing, the method will create a new
     * save file and directory.
     */
    public void readSaveFiles() {
        File saveFileTasks = new File(filePathTasks);
        File saveFileNotes = new File(filePathNotes);

        if (!fileExists(saveFileTasks, saveFileNotes)) {
            createNewSaves(saveFileTasks, saveFileNotes);
        } else {
            System.out.println("Reading your save data...");
            noteStorage.readNotesSave(filePathNotes);
            taskStorage.readTasksSave(filePathTasks);
            System.out.println("All save data successfully read!");
        }
    }

    /**
     * Checks if both save files exist.
     *
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     * @return True when both files exist.
     */
    private boolean fileExists(File saveFileTasks, File saveFileNotes) {
        return saveFileTasks.exists() && saveFileNotes.exists();
    }

    /**
     * Determines if save directory and files are existent, before creating new save files and directories.
     */
    private void createNewSaves(File saveFileTasks, File saveFileNotes) {
        File fileDirectory = new File(DIRECTORY_PATH);

        //If directory exists, create missing save files
        if (directoryExists(fileDirectory)) {
            ui.showDirectoryDetected();

            if (!saveFileTasks.exists()) {
                createTaskSaveFile(saveFileTasks);
            }

            if (!saveFileNotes.exists()) {
                createNoteSaveFile(saveFileNotes);
            }

        } else {
            handleMissingSaveDirectory(fileDirectory, saveFileTasks, saveFileNotes);
        }
    }

    private boolean directoryExists(File fileDirectory) {
        return fileDirectory.exists();
    }

    /**
     * Handles when no save directory is detected and creates a new directory.
     *
     * @param fileDirectory File object which holds the directory path to be created.
     */
    private void handleMissingSaveDirectory(File fileDirectory, File saveFileTasks, File saveFileNotes) {
        ui.showNoDirectoryDetected();
        //Attempt creation of new save directory to hold save files
        if (createNewDirectory(fileDirectory)) {
            ui.showNewDirectoryCreated();
            createTaskSaveFile(saveFileTasks);
            createNoteSaveFile(saveFileNotes);
        } else {
            ui.showNewDirectoryFailCreated();
        }
    }

    private boolean createNewDirectory(File fileDirectory) {
        return fileDirectory.mkdir();
    }

    /**
     * Creates a new save file for storing tasks at the specified path.
     *
     * @param saveFileTasks File object which holds the path at which the save file is to be created.
     */
    private void createTaskSaveFile(File saveFileTasks) {
        ui.showCreatingNewSaveFile();

        try {
            if (saveFileTasks.createNewFile()) {
                ui.showFileCreationSuccess();
            }
        } catch (IOException e) {
            ui.showFileCreationError();
        }
    }

    /**
     * Creates a new save file for storing notes at the specified path.
     *
     * @param saveFileNotes File object which holds the path at which the save file is to be created.
     */
    private void createNoteSaveFile(File saveFileNotes) {
        ui.showCreatingNewNotesSaveFile();

        try {
            if (saveFileNotes.createNewFile()) {
                ui.showFileCreationSuccess();
            }
        } catch (IOException e) {
            ui.showFileCreationError();
        }
    }

    public void saveNote() {
        noteStorage.writeToNoteSaveFile();
    }

    public void saveTasks() {
        taskStorage.writeToTaskSaveFile();
    }

}
