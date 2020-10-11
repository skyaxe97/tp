package seedu.lifeasier.storage;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileCommandTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private FileCommand fileCommand;

    public FileCommandTest() {
        this.fileCommand = new FileCommand();
    }

    @Test
    void convertToLocalDateTime_inputValidDateTime_correctOutput() {
        String formattedDateTime =
                fileCommand.convertToLocalDateTime("09-04-21T18:00").format(DATE_TIME_FORMATTER);
        assertEquals("09-04-21 18:00", formattedDateTime);
    }

    @Test
    void convertToLocalDateTIme_invalidDateTime_defaultDateTime() {
        String formattedDateTime =
                fileCommand.convertToLocalDateTime("09-23-21T18:00").format(DATE_TIME_FORMATTER);
        assertEquals("31-12-99 00:00", formattedDateTime);
    }

}