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
    private static final String TASK_FILE_TYPE = "taskSave";
    private static final String NOTE_FILE_TYPE = "noteSave";

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

        //Check if both saves present. If they are, proceed to read data, else determine which saves to create
        if (!checkIfBothFilesExists(saveFileTasks, saveFileNotes)) {
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
        ui.showDirectoryDetected();
        checkForTaskSaveFile(saveFileTasks);
        checkForNotesSaveFile(saveFileNotes);
    }

    private void checkForNotesSaveFile(File saveFileNotes) {
        //Create new save file if task save file does not exist
        if (!saveFileNotes.exists()) {
            createNewSaveFile(saveFileNotes, NOTE_FILE_TYPE);
        }
    }

    private void checkForTaskSaveFile(File saveFileTasks) {
        //Create new notes save file if notes save file does not exist
        if (!saveFileTasks.exists()) {
            createNewSaveFile(saveFileTasks, TASK_FILE_TYPE);
        }
    }

    private boolean directoryExists(File fileDirectory) {
        return fileDirectory.exists();
    }

    private void handleMissingSaveDirectory(File fileDirectory, File saveFileTasks, File saveFileNotes) {
        ui.showNoDirectoryDetected();
        //Attempt creation of new save directory to hold save files
        if (createNewDirectory(fileDirectory)) {
            ui.showNewDirectoryCreated();
            createNewSaveFile(saveFileTasks, TASK_FILE_TYPE);
            createNewSaveFile(saveFileNotes, NOTE_FILE_TYPE);
        } else {
            ui.showNewDirectoryFailCreated();
        }
    }

    private boolean createNewDirectory(File fileDirectory) {
        return fileDirectory.mkdir();
    }

    private void createNewSaveFile(File saveFile, String saveFileType) {
        switch (saveFileType) {
        case "taskSave":
            ui.showCreatingNewSaveFile();
            break;
        case "noteSave":
            ui.showCreatingNewNotesSaveFile();
            break;
        default:
            System.out.println("There was an error determining the save file type missing... Continue to create file");
            break;
        }

        try {
            if (saveFile.createNewFile()) {
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
