package seedu.lifeasier.storage;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The FileStorage class is the main entry point to actions related to the reading, writing and creation of the
 * LifEasier save files.
 */
public class FileStorage {

    private static final String DIRECTORY_PATH = "LifEasierSaves/";
    private static final String ARCHIVE_PATH = DIRECTORY_PATH + "Archives/";
    private static Logger logger = Logger.getLogger(FileStorage.class.getName());

    private String filePathTasks;
    private String filePathNotes;
    private Ui ui;
    private NoteStorage noteStorage;
    private TaskStorage taskStorage;
    private FileArchive fileArchive;
    private FileCommand fileCommand;
    private NoteHistory noteHistory;
    private NoteList notes;

    public FileStorage(String fileNameTasks, String fileNameNotes, Ui ui, NoteList notes, TaskList tasks,
            NoteHistory noteHistory) {
        this.filePathTasks = DIRECTORY_PATH + fileNameTasks;
        this.filePathNotes = DIRECTORY_PATH + fileNameNotes;
        this.ui = ui;
        this.notes = notes;
        this.noteStorage = new NoteStorage(notes, filePathNotes);
        this.taskStorage = new TaskStorage(tasks, filePathTasks);
        this.fileArchive = new FileArchive(notes, ui);
        this.fileCommand = new FileCommand();
        this.noteHistory = noteHistory;
    }

    /**
     * Acts as main entry point into data archiving for LifEasier.
     */
    public void archiveData() {
        File archiveDirectory = new File(ARCHIVE_PATH);

        ui.showArchiveStartMessage();
        logger.log(Level.INFO, "Start archiving process");
        //Create archive directory if non existent
        if (!directoryExists(archiveDirectory)) {
            createNewDirectory(archiveDirectory);
        }

        fileArchive.handleDataArchiving(ARCHIVE_PATH);
        //Clear notes save file
        fileCommand.clearSaveFile(filePathNotes);
        noteHistory.clearNoteHistory();
    }

    /**
     * Reads and loads the stored information on the save file. If save files are missing, the method will create a new
     * save file and directory.
     */
    public void readSaveFiles() {
        File saveFileTasks = new File(filePathTasks);
        File saveFileNotes = new File(filePathNotes);
        logger.log(Level.INFO, "Start processing read saves");

        ui.showDataLoadingMessage();
        //Check if both saves present. If they are, proceed to read data, else determine which saves to create
        if (!checkIfBothFilesExists(saveFileTasks, saveFileNotes)) {
            logger.log(Level.INFO, "Save files missing, create save files");
            createNewSaves(saveFileTasks, saveFileNotes);
        } else {
            assert checkIfBothFilesExists(saveFileTasks, saveFileNotes) : "Both saves are supposed to exist";

            logger.log(Level.INFO, "Save files found, read save files");
            noteStorage.readNotesSave();
            taskStorage.readTasksSave();
        }
        
        saveNotes();
        saveTasks();

        logger.log(Level.INFO, "Startup file read end");
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

    /**
     * Handles the creation of new save directories and save files.
     *
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     */
    private void createNewSaves(File saveFileTasks, File saveFileNotes) {
        File fileDirectory = new File(DIRECTORY_PATH);

        if (directoryExists(fileDirectory)) {
            //Directory exists, check individual save files and create the missing files.
            logger.log(Level.INFO, "Save directory found");
            handleExistingSaveDirectory(saveFileTasks, saveFileNotes);
        } else {
            assert !directoryExists(fileDirectory) : "Directory should not exist";

            //Directory missing, create new directory
            logger.log(Level.INFO, "Save directory missing, proceed to create");
            handleMissingSaveDirectory(fileDirectory, saveFileTasks, saveFileNotes);
        }
    }

    /**
     * Determines which save files exist and which do not when the save directory exists.
     *
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     */
    private void handleExistingSaveDirectory(File saveFileTasks, File saveFileNotes) {
        checkForTasksSaveFile(saveFileTasks);
        checkForNotesSaveFile(saveFileNotes);
    }

    /**
     * Checks if the notes save file exists and creates the save file if it is missing.
     *
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     */
    private void checkForNotesSaveFile(File saveFileNotes) {
        logger.log(Level.INFO, "Checking for notes save file");
        //Create new save file if task save file does not exist
        if (!saveFileNotes.exists()) {
            createNewSaveFile(saveFileNotes);
            return;
        }
        //Read note save file
        noteStorage.readNotesSave();
    }

    /**
     * Checks if the tasks save file exists and creates the save file if it is missing.
     *
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     */
    private void checkForTasksSaveFile(File saveFileTasks) {
        logger.log(Level.INFO, "Checking for task save file");
        //Create new notes save file if notes save file does not exist
        if (!saveFileTasks.exists()) {
            createNewSaveFile(saveFileTasks);
            return;
        }
        //Read task save file
        taskStorage.readTasksSave();
    }

    private boolean directoryExists(File fileDirectory) {
        return fileDirectory.exists();
    }

    /**
     * Creates the missing save directory and the save files for use.
     *
     * @param fileDirectory File object with the save directory path.
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     */
    private void handleMissingSaveDirectory(File fileDirectory, File saveFileTasks, File saveFileNotes) {
        //Attempt creation of new save directory to hold save files
        logger.log(Level.INFO, "Creating save directory");

        try {
            if (createNewDirectory(fileDirectory)) {
                createNewSaveFile(saveFileTasks);
                createNewSaveFile(saveFileNotes);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            ui.showDirectoryCreationFailedError();
            logger.log(Level.WARNING, "Save directory creation failed");
        }
    }

    private boolean createNewDirectory(File fileDirectory) {
        return fileDirectory.mkdir();
    }

    /**
     * Creates a save file depending on the file path passed.
     *
     * @param saveFile File object containing the file path of the save file that is to be created.
     */
    private void createNewSaveFile(File saveFile) {
        logger.log(Level.INFO, "Creating save file");
        try {
            saveFile.createNewFile();
            logger.log(Level.INFO, "Save file created");
        } catch (IOException e) {
            ui.showFileCreationError();
            logger.log(Level.WARNING, "Save file creation failed");
        }
    }

    /**
     * Saves all notes in the current task list.
     */
    public void saveNotes() {
        noteStorage.writeToNoteSaveFile();
    }

    /**
     * Saves all tasks in the current task list.
     */
    public void saveTasks() {
        taskStorage.writeToTaskSaveFile();
    }

}
