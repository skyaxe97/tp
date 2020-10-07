package seedu.lifeasier.storage;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The FileCommand class will house similar commands used by both NoteStorage and TaskStorage classes.
 */
public class FileCommand {
    /**
     * Clears all data from the specified save file.
     *
     * @param filePath File path to which file to clear information.
     */
    protected void clearSaveFile(String filePath) {
        try {
            FileWriter fileClear = new FileWriter(filePath);
            fileClear.write("");
            fileClear.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while clearing the file...");
        }
    }
}
