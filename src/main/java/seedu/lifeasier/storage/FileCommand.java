package seedu.lifeasier.storage;

import seedu.lifeasier.ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The FileCommand class will house similar commands used by both NoteStorage and TaskStorage classes.
 */
public class FileCommand {

    public static final String BLANK_STRING = "";
    public static final String DEFAULT_DATETIME = "31-12-99 00:00";
    public static final String TIME_DELIMITER = "T";
    public static final String WHITE_SPACE = " ";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private static Logger logger = Logger.getLogger(FileCommand.class.getName());

    private Ui ui;

    public FileCommand() {
        this.ui = new Ui();
    }

    /**
     * Clears all data from the specified save file.
     *
     * @param filePath File path to which file to clear information.
     */
    protected void clearSaveFile(String filePath) {
        logger.log(Level.INFO, "Clearing save file");

        try {
            FileWriter fileClear = new FileWriter(filePath);
            fileClear.write(BLANK_STRING);
            fileClear.close();
        } catch (IOException e) {
            ui.showFileWriteError();
            logger.log(Level.SEVERE, "Error encountered clearing save file");
        }

        logger.log(Level.INFO, "Save file cleared");
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
            dateTimeInformation = dateTimeInformation.replace(TIME_DELIMITER, WHITE_SPACE);
            taskDateTime = LocalDateTime.parse(dateTimeInformation, DATE_TIME_FORMATTER);

        } catch (DateTimeParseException e) {
            ui.showLocalDateTimeParseError();
            logger.log(Level.WARNING, "Error encountered parsing LocalDateTime from save file");
            //Set as default time
            taskDateTime = LocalDateTime.parse(DEFAULT_DATETIME, DATE_TIME_FORMATTER);
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
