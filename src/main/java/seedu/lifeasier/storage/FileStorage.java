package seedu.lifeasier.storage;

import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.IOException;

/**
 * The FileStorage class will handle actions related to the reading and writing of the LifEasier save file.
 */
public class FileStorage {

    private static final String DIRECTORY_PATH = "/Saves/";

    private String filePath;
    private Ui ui;

    public FileStorage(String fileName, Ui ui) {
        this.filePath = DIRECTORY_PATH + fileName;
        this.ui = ui;
    }

    /**
     * Reads and loads the stored information on the save file. If no save file exists, the method will create a new
     * save file and directory.
     */
    public void readSaveFile() {
        if (!fileExists()) {
            createNewSave();
        } else {
            System.out.println("READING FROM FILE");
        }
    }

    private boolean fileExists() {
        File fileLocation = new File(filePath);

        return fileLocation.exists();
    }

    private boolean directoryExists(File fileDirectory) {
        return fileDirectory.exists();
    }

    /**
     * Creates save files depending on if directory is already existent.
     */
    private void createNewSave() {
        File fileDirectory = new File(DIRECTORY_PATH);
        File saveFile = new File(filePath);

        if (directoryExists(fileDirectory)) {
            ui.showDirectoryDetected();
            createSaveFile(saveFile);
        } else {
            handleMissingSaveDirectory(fileDirectory, saveFile);
        }
    }

    /**
     * Handles when no save directory is detected and creates a new directory.
     *
     * @param fileDirectory File object which holds the directory path to be created.
     */
    private void handleMissingSaveDirectory(File fileDirectory, File saveFile) {
        ui.showNoDirectoryDetected();
        //Attempt creation of new save directory to hold save files
        if (createNewDirectory(fileDirectory)) {
            ui.showNewDirectoryCreated();
            createSaveFile(saveFile);
        } else {
            ui.showNewDirectoryFailCreated();
        }
    }

    /**
     * Creates a new save file at the specified path.
     *
     * @param saveFile File object which holds the path at which the save file is to be created.
     */
    private void createSaveFile(File saveFile) {
        ui.showCreatingNewSaveFile();

        try {
            if (saveFile.createNewFile()) {
                ui.showFileCreationSuccess();
            }
        } catch (IOException e) {
            ui.showFileCreationError();
        }
    }

    private boolean createNewDirectory(File fileDirectory) {
        return fileDirectory.mkdir();
    }

}
