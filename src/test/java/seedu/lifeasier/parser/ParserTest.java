package seedu.lifeasier.parser;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.commands.AddLessonCommand;
import seedu.lifeasier.commands.Command;
import seedu.lifeasier.commands.HelpCommand;
import seedu.lifeasier.ui.Ui;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    void parseCommand_inputHelp_HelpCommand() throws ParserException {
        Parser parser = new Parser();
        Ui ui = new Ui();
        Command command = parser.parseCommand("help", ui);
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    void parseCommand_inputAddLesson_AddLessonCommand() throws ParserException {
        Parser parser = new Parser();
        Ui ui = new Ui();
        Command command = parser.parseCommand("addLesson /code cg1111 /date 10-10-20 /time 10:00 /to 20:00", ui);
        assertTrue(command instanceof AddLessonCommand);
    }

    @Test
    void parseCommand_inputInvalidCommand_ParserException() {
        Parser parser = new Parser();
        Ui ui = new Ui();
        assertThrows(ParserException.class, () -> {
            parser.parseCommand("I want to add a lesson", ui);
        });
    }

    @Test
    void parseAddDeadlineCommand_inputInvalidDateTime_ParserException() {
        Parser parser = new Parser();
        assertThrows(DateTimeParseException.class, () -> {
            parser.parseAddDeadlineCommand("addDeadline do homework /by 10-30-40 24:67");
        });
    }


}