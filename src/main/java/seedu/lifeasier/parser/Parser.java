package seedu.lifeasier.parser;

import seedu.lifeasier.commands.AddDeadlineCommand;
import seedu.lifeasier.commands.AddEventCommand;
import seedu.lifeasier.commands.AddLessonCommand;
import seedu.lifeasier.commands.AddNotesCommand;
import seedu.lifeasier.commands.ArchiveCommand;
import seedu.lifeasier.commands.Command;
import seedu.lifeasier.commands.DeleteTaskCommand;
import seedu.lifeasier.commands.DisplayScheduleCommand;
import seedu.lifeasier.commands.EditDeadlineCommand;
import seedu.lifeasier.commands.EditEventCommand;
import seedu.lifeasier.commands.EditLessonCommand;
import seedu.lifeasier.commands.ExitCommand;
import seedu.lifeasier.commands.HelpCommand;
import seedu.lifeasier.commands.ShowNotesCommand;
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
    public static final String PARAM_EDIT_LESSON = "editLesson";
    public static final String PARAM_EDIT_DEADLINE = "editDeadline";
    public static final String PARAM_EDIT_EVENT = "editEvent";
    public static final String PARAM_DELETE_TASK = "deleteTask";

    public static final String PARAM_CODE = "/code";
    public static final String PARAM_DATE = "/date";
    public static final String PARAM_TIME = "/time";
    public static final String PARAM_TO = "/to";
    public static final String PARAM_BY = "/by";
    public static final String PARAM_TYPE = "/type";
    public static final String PARAM_NAME = "/name";

    public static final String PARAM_LESSON = "lesson";
    public static final String PARAM_EVENT = "event";
    public static final String PARAM_DEADLINE = "deadline";


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
    private Command parseAddLessonCommand(String input) {

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
    private Command parseAddDeadlineCommand(String input) {

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

    private Command parseEditLessonCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing editLesson command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_EDIT_LESSON) + PARAM_EDIT_LESSON.length();
        String code = input.substring(lastIndexOfAddNotesCommand).trim();

        return new EditLessonCommand(code);
    }

    private Command parseEditEventCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing editEvent command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_EDIT_EVENT) + PARAM_EDIT_EVENT.length();
        String eventName = input.substring(lastIndexOfAddNotesCommand).trim();

        return new EditEventCommand(eventName);
    }

    private Command parseEditDeadlineCommand(String input) {

        LOGGER.log(Level.INFO, "Parsing editDeadline command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_EDIT_DEADLINE) + PARAM_EDIT_DEADLINE.length();
        String deadlineName = input.substring(lastIndexOfAddNotesCommand).trim();

        return new EditDeadlineCommand(deadlineName);
    }

    private Command parseDeleteTaskCommand(String input) {
        String type = "";
        String name = "";
        try {
            LOGGER.log(Level.INFO, "Parsing deleteTask command...");
            int firstIndexOfTypeCommand = input.indexOf(PARAM_TYPE);
            int lastIndexOfTypeCommand = input.indexOf(PARAM_TYPE) + PARAM_TYPE.length();
            if (firstIndexOfTypeCommand == -1) {
                throw new ParserException();
            }

            int firstIndexOfNameCommand = input.indexOf(PARAM_NAME);
            if (firstIndexOfNameCommand == -1) {
                type = input.substring(lastIndexOfTypeCommand).trim();
                name = "";
            } else {
                int lastIndexOfNameCommand = input.indexOf(PARAM_NAME) + PARAM_NAME.length();
                type = input.substring(lastIndexOfTypeCommand, firstIndexOfNameCommand).trim();
                if (!type.equals(PARAM_DEADLINE) && !type.equals(PARAM_EVENT)
                        && !type.equals(PARAM_LESSON)) {
                    throw new ParserException();
                }
                name = input.substring(lastIndexOfNameCommand).trim();
            }
        } catch (ParserException e) {
            LOGGER.log(Level.SEVERE, "Invalid command...");
        }
        return new DeleteTaskCommand(type, name);
    }

    public LocalDateTime[] parseNewTimeInput(Ui ui, String input, int numOfTimeArgs) throws ParserException {

        LOGGER.log(Level.INFO, "Parsing newTimeInput from user...");
        LocalDateTime[] times = new LocalDateTime[2];
        try {
            switch (numOfTimeArgs) {

            case (1):
                int firstIndexOfByCommand = input.indexOf(PARAM_BY);
                int lastIndexOfByCommand = firstIndexOfByCommand + PARAM_BY.length();

                if (firstIndexOfByCommand == -1) {
                    throw new ParserException();
                }

                String byInput = input.substring(lastIndexOfByCommand).trim();
                LocalDateTime by = LocalDateTime.parse(byInput, DATE_TIME_FORMATTER);
                times[0] = by;
                return times;

            case (2):
                int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);
                int lastIndexOfDateCommand = firstIndexOfDateCommand + PARAM_DATE.length();
                int firstIndexOfTimeCommand = input.indexOf(PARAM_TIME);
                int lastIndexOfTimeCommand = firstIndexOfTimeCommand + PARAM_TIME.length();
                int firstIndexOfToCommand = input.indexOf(PARAM_TO);
                int lastIndexOfToCommand = firstIndexOfToCommand + PARAM_TO.length();

                if (firstIndexOfDateCommand == -1 || firstIndexOfTimeCommand == -1 || firstIndexOfToCommand == -1) {
                    throw new ParserException();
                }

                String date = input.substring(lastIndexOfDateCommand, firstIndexOfTimeCommand).trim();
                String startTime = input.substring(lastIndexOfTimeCommand, firstIndexOfToCommand).trim();
                String endTime =  input.substring(lastIndexOfToCommand).trim();
                LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
                LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);
                times[0] = start;
                times[1] = end;
                return times;

            default:
                throw new ParserException();
            }

        } catch (DateTimeParseException e) {
            ui.showLocalDateTimeParseError();
        }
        return times;
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

            case (PARAM_EDIT_LESSON):
                return parseEditLessonCommand(input);

            case (PARAM_EDIT_EVENT):
                return parseEditEventCommand(input);

            case (PARAM_EDIT_DEADLINE):
                return parseEditDeadlineCommand(input);

            case (PARAM_DELETE_TASK):
                return parseDeleteTaskCommand(input);

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
