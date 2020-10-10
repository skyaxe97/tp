package seedu.lifeasier.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * The FileCommand class will house similar commands used by both NoteStorage and TaskStorage classes.
 */
public class FileCommand {

    public static final String BLANK_STRING = "";
    public static final String DEFAULT_DATETIME = "9999-12-30T00:00:00";

    /**
     * Clears all data from the specified save file.
     *
     * @param filePath File path to which file to clear information.
     */
    protected void clearSaveFile(String filePath) {
        try {
            FileWriter fileClear = new FileWriter(filePath);
            fileClear.write(BLANK_STRING);
            fileClear.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while clearing the file...");
        }
    }

    /**
     * Converts the saved raw string text in the save file into LocalDateTime objects.
     *
     * @param dateTimeInformation String which is to be parsed into LocalDateTime object.
     * @return Input string parsed into LocalDateTime object.
     */
    public LocalDateTime convertToLocalDateTime(String dateTimeInformation) {
        LocalDateTime taskDateTime;
        try {
            taskDateTime = LocalDateTime.parse(dateTimeInformation);

        } catch (DateTimeParseException e) {
            System.out.println("Encountered a problem reading the date and time of the task...");
            //Set as default time
            taskDateTime = LocalDateTime.parse(DEFAULT_DATETIME);
        }
        return taskDateTime;
    }

    /**
     * Converts a string to boolean form.
     *
     * @param string String to be parsed into a boolean.
     * @return Boolean true if string is "true", and false for all other cases.
     */
    public Boolean convertToBoolean(String string) {
        return Boolean.parseBoolean(string);
    }
}
