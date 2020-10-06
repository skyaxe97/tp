package seedu.lifeasier.storage;

import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The FileStorage class will handle actions related to the reading and writing of the LifEasier save file.
 */
public class FileStorage {

    private static final String DIRECTORY_PATH = "/LifEasierSaves/";

    private String filePathTasks;
    private String filePathNotes;
    private Ui ui;

    public FileStorage(String fileNameTasks, String fileNameNotes, Ui ui) {
        this.filePathTasks = DIRECTORY_PATH + fileNameTasks;
        this.filePathNotes = DIRECTORY_PATH + fileNameNotes;
        this.ui = ui;
    }

    /**
     * Reads and loads the stored information on the save file. If no save file exists, the method will create a new
     * save file and directory.
     */
    public void readTaskSaveFile() {
        File saveFileTasks = new File(filePathTasks);
        File saveFileNotes = new File(filePathNotes);

        if (!fileExists(saveFileTasks, saveFileNotes)) {
            createNewSaves(saveFileTasks, saveFileNotes);
        } else {
            System.out.println("Reading your save data...");
//            try {
//                Scanner fileScanner = new Scanner(saveFileTasks);
//                while (fileScanner.hasNext()) {
//                    System.out.println(fileScanner.nextLine());
//                }
//            } catch (IOException e) {
//                System.out.println("Oh no, we could not read the file!");
//            }
        }
    }

    /**
     * Writes information of TaskList onto the save file for storage whenever there is a change.
     *
     * @throws IOException When the file cannot be found or is corrupted.
     */
    public void writeToTaskSaveFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filePathTasks, true);
    }

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

}
