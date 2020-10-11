package seedu.lifeasier.storage;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.IOException;

/**
 * The FileStorage class is the main entry point to actions related to the reading, writing and creation of the
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

        ui.showDataLoading();
        //Check if both saves present. If they are, proceed to read data, else determine which saves to create
        if (!checkIfBothFilesExists(saveFileTasks, saveFileNotes)) {
            createNewSaves(saveFileTasks, saveFileNotes);
        } else {
            noteStorage.readNotesSave(filePathNotes);
            taskStorage.readTasksSave(filePathTasks);
        }
    }

    /**
     * Checks if both save files exist.
     *
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     * @return True when both files exist.
     */
    private boolean checkIfBothFilesExists(File saveFileTasks, File saveFileNotes) {
        return saveFileTasks.exists() && saveFileNotes.exists();
    }

    private void createNewSaves(File saveFileTasks, File saveFileNotes) {
        File fileDirectory = new File(DIRECTORY_PATH);

        if (directoryExists(fileDirectory)) {
            //Directory exists, check individual save files and create the missing files.
            handleExistingSaveDirectory(saveFileTasks, saveFileNotes);
        } else {
            //Directory missing, create new directory
            handleMissingSaveDirectory(fileDirectory, saveFileTasks, saveFileNotes);
        }
    }

    private void handleExistingSaveDirectory(File saveFileTasks, File saveFileNotes) {
        checkForTaskSaveFile(saveFileTasks);
        checkForNotesSaveFile(saveFileNotes);
    }

    private void checkForNotesSaveFile(File saveFileNotes) {
        //Create new save file if task save file does not exist
        if (!saveFileNotes.exists()) {
            createNewSaveFile(saveFileNotes);
        }
    }

    private void checkForTaskSaveFile(File saveFileTasks) {
        //Create new notes save file if notes save file does not exist
        if (!saveFileTasks.exists()) {
            createNewSaveFile(saveFileTasks);
        }
    }

    private boolean directoryExists(File fileDirectory) {
        return fileDirectory.exists();
    }

    private void handleMissingSaveDirectory(File fileDirectory, File saveFileTasks, File saveFileNotes) {
        //Attempt creation of new save directory to hold save files
        if (createNewDirectory(fileDirectory)) {
            createNewSaveFile(saveFileTasks);
            createNewSaveFile(saveFileNotes);
        }
    }

    private boolean createNewDirectory(File fileDirectory) {
        return fileDirectory.mkdir();
    }

    private void createNewSaveFile(File saveFile) {
        try {
            saveFile.createNewFile();
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
