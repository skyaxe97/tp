package seedu.lifeasier.parser;

import seedu.lifeasier.commands.*;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    private static Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public static final String PARAM_ADD_LESSON = "addLesson";
    public static final String PARAM_ADD_EVENT = "addEvent";
    public static final String PARAM_ADD_DEADLINE = "addDeadline";
    public static final String PARAM_ADD_NOTES = "addNotes";
    public static final String PARAM_SHOW_NOTES = "showNotes";
    public static final String PARAM_DISPLAY = "display";
    public static final String PARAM_HELP = "help";
    public static final String PARAM_EXIT = "exit";
    public static final String PARAM_ARCHIVE = "archive";

    public static final String PARAM_CODE = "/code";
    public static final String PARAM_DATE = "/date";
    public static final String PARAM_TIME = "/time";
    public static final String PARAM_TO = "/to";
    public static final String PARAM_BY = "/by";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");

    public Parser() {

    }

    /**
     * Returns the first word entered from the user's input, which represents the command type.
     *
     * @param input String containing the user's input.
     * @return The first word that the user input.
     */
    private String getCommandType(String input) {
        LOGGER.log(Level.INFO, "Getting command type...");
        int indexOfFirstSpace = input.indexOf(" ");
        if (indexOfFirstSpace > 0) {
            return input.substring(0, indexOfFirstSpace);
        } else {
            return input;
        }
    }

    /**
     * Parses the addLesson command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return AddLessonCommand with the parameters input by the user.
     */
    Command parseAddLessonCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing addLesson command...");

        int lastIndexOfCodeCommand = input.indexOf(PARAM_CODE) + PARAM_CODE.length();
        int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);
        int lastIndexOfDateCommand = firstIndexOfDateCommand + PARAM_DATE.length();
        int firstIndexOfTimeCommand = input.indexOf(PARAM_TIME);
        int lastIndexOfTimeCommand = firstIndexOfTimeCommand + PARAM_TIME.length();
        int firstIndexOfToCommand = input.indexOf(PARAM_TO);
        int lastIndexOfToCommand = firstIndexOfToCommand + PARAM_TO.length();

        String moduleCode = input.substring(lastIndexOfCodeCommand, firstIndexOfDateCommand).trim();
        String date = input.substring(lastIndexOfDateCommand, firstIndexOfTimeCommand).trim();
        String startTime = input.substring(lastIndexOfTimeCommand, firstIndexOfToCommand).trim();
        String endTime =  input.substring(lastIndexOfToCommand).trim();
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);

        return new AddLessonCommand(moduleCode, start, end);
    }

    /**
     * Parses the addEvent command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return AddEventCommand with the parameters input by the user.
     */
    private Command parseAddEventCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing addEvent command...");

        int lastIndexOfAddEventCommand = input.indexOf(PARAM_ADD_EVENT) + PARAM_ADD_EVENT.length();
        int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);
        int lastIndexOfDateCommand = firstIndexOfDateCommand + PARAM_DATE.length();
        int firstIndexOfTimeCommand = input.indexOf(PARAM_TIME);
        int lastIndexOfTimeCommand = firstIndexOfTimeCommand + PARAM_TIME.length();
        int firstIndexOfToCommand = input.indexOf(PARAM_TO);
        int lastIndexOfToCommand = firstIndexOfToCommand + PARAM_TO.length();

        String description = input.substring(lastIndexOfAddEventCommand, firstIndexOfDateCommand).trim();
        String date = input.substring(lastIndexOfDateCommand, firstIndexOfTimeCommand).trim();
        String startTime = input.substring(lastIndexOfTimeCommand, firstIndexOfToCommand).trim();
        String endTime =  input.substring(lastIndexOfToCommand).trim();
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);

        return new AddEventCommand(description, start, end);
    }

    /**
     * Parses the addDeadline command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return AddDeadlineCommand with the parameters input by the user.
     */
    Command parseAddDeadlineCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing addDeadline command...");

        int lastIndexOfAddDeadlineCommand = input.indexOf(PARAM_ADD_DEADLINE) + PARAM_ADD_DEADLINE.length();
        int firstIndexOfByCommand = input.indexOf(PARAM_BY);
        int lastIndexOfByCommand = firstIndexOfByCommand + PARAM_BY.length();

        String description = input.substring(lastIndexOfAddDeadlineCommand, firstIndexOfByCommand).trim();
        String byInput = input.substring(lastIndexOfByCommand).trim();
        LocalDateTime by = LocalDateTime.parse(byInput, DATE_TIME_FORMATTER);

        return new AddDeadlineCommand(description, by);
    }

    /**
     * Parses the addNotes command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return AddNotesCommand with the parameters input by the user.
     */
    private Command parseAddNotesCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing addNotes command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_ADD_NOTES) + PARAM_ADD_NOTES.length();
        String title = input.substring(lastIndexOfAddNotesCommand).trim();

        return new AddNotesCommand(title);
    }

    /**
     * Parses the showNotes command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return ShowNotesCommand with the parameters input by the user.
     */
    private Command parseShowNotesCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing showNotes command...");

        int lastIndexOfShowNotesCommand = input.indexOf(PARAM_SHOW_NOTES) + PARAM_SHOW_NOTES.length();
        String title = input.substring(lastIndexOfShowNotesCommand).trim();

        return new ShowNotesCommand(title);
    }

    /**
     * Parses the display command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return DisplayScheduleCommand with the parameters input by the user.
     */
    private Command parseDisplayScheduleCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing display command...");

        int lastIndexOfDisplayScheduleCommand = input.indexOf(PARAM_DISPLAY) + PARAM_DISPLAY.length();
        String toDisplay = input.substring(lastIndexOfDisplayScheduleCommand).trim();

        return new DisplayScheduleCommand(toDisplay);
    }

    /**
     * Parses the user's input into a Command object that can later be executed.
     *
     * @param input String containing the user's input.
     * @return Command that the user inputs.
     */
    public Command parseCommand(String input, Ui ui) throws ParserException {

        LOGGER.log(Level.INFO, "Parsing user input for command...");

        try {
            String commandType = getCommandType(input);

            switch (commandType) {

            case (PARAM_ADD_LESSON):
                return parseAddLessonCommand(input);

            case (PARAM_ADD_EVENT):
                return parseAddEventCommand(input);

            case (PARAM_ADD_DEADLINE):
                return parseAddDeadlineCommand(input);

            case (PARAM_ADD_NOTES):
                return parseAddNotesCommand(input);

            case (PARAM_SHOW_NOTES):
                return parseShowNotesCommand(input);

            case (PARAM_DISPLAY):
                return parseDisplayScheduleCommand(input);

            case (PARAM_HELP):
                return new HelpCommand();

            case (PARAM_ARCHIVE):
                return new ArchiveCommand();

            case (PARAM_EXIT):
                return new ExitCommand();

            default:
                throw new ParserException();
            }

        } catch (IndexOutOfBoundsException e) {
            ui.showParseIncorrectCommandFormatMessage();

        } catch (DateTimeParseException e) {
            ui.showParseIncorrectDateTimeMessage();
        }

        return new ExitCommand();

    }
}
