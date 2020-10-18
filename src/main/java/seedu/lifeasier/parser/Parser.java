package seedu.lifeasier.parser;

import seedu.lifeasier.commands.AddDeadlineCommand;
import seedu.lifeasier.commands.AddEventCommand;
import seedu.lifeasier.commands.AddLessonCommand;
import seedu.lifeasier.commands.AddNotesCommand;
import seedu.lifeasier.commands.ArchiveCommand;
import seedu.lifeasier.commands.Command;
import seedu.lifeasier.commands.DisplayScheduleCommand;
import seedu.lifeasier.commands.ExitCommand;
import seedu.lifeasier.commands.HelpCommand;
import seedu.lifeasier.commands.ShowNotesCommand;
import seedu.lifeasier.commands.DeleteNotesCommand;
import seedu.lifeasier.commands.EditNotesCommand;
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
    public static final String PARAM_DELETE_NOTES = "deleteNotes";
    public static final String PARAM_EDIT_NOTES = "editNotes";
    public static final String PARAM_DISPLAY = "display";
    public static final String PARAM_HELP = "help";
    public static final String PARAM_EXIT = "exit";
    public static final String PARAM_ARCHIVE = "archive";

    public static final String PARAM_CODE = "/code";
    public static final String PARAM_DATE = "/date";
    public static final String PARAM_TIME = "/time";
    public static final String PARAM_TO = "/to";
    public static final String PARAM_BY = "/by";

    private boolean isParametersEmpty = true;
    private boolean isModuleCodeEmpty = true;
    private boolean isDescriptionEmpty = true;
    private boolean isDateEmpty = true;
    private boolean isStartTimeEmpty = true;
    private boolean isEndTimeEmpty = true;
    private boolean isDateTimeEmpty = true;

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
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return AddLessonCommand with the parameters input by the user.
     */
    Command parseAddLessonCommand(Ui ui, String input) {

        LOGGER.log(Level.INFO, "Parsing addLesson command...");

        while (isParametersEmpty) {
            MissingParam param = checkLessonParameters(input);

            switch (param) {
            case MODULE_CODE:   // module code is missing
                input = addModuleCodeParam(ui, input);
                isModuleCodeEmpty = false;
                break;
            case DATE:
                input = addDateParam(ui, input);
                isDateEmpty = false;
                break;
            case START_TIME:
                input = addStartTimeParam(ui, input);
                isStartTimeEmpty = false;
                break;
            case END_TIME:
                input = addEndTimeParam(ui, input);
                isEndTimeEmpty = false;
                break;
            default:
                isParametersEmpty = false;
                break;
            }

        }

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

        System.out.println(moduleCode + " " + date + " " + startTime + " " + endTime);
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);

        resetBoolean();
        return new AddLessonCommand(moduleCode, start, end);
    }

    /**
     * Parses the addEvent command that the user inputs.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return AddEventCommand with the parameters input by the user.
     */
    private Command parseAddEventCommand(Ui ui, String input) {

        LOGGER.log(Level.INFO, "Parsing addEvent command...");

        while (isParametersEmpty) {
            MissingParam param = checkEventParameters(input);
            switch (param) {
            case DESCRIPTION:   // description is missing
                input = addEventDescriptionParam(ui, input);
                isDescriptionEmpty = false;
                break;
            case DATE:
                input = addDateParam(ui, input);
                isDateEmpty = false;
                break;
            case START_TIME:
                input = addStartTimeParam(ui, input);
                isStartTimeEmpty = false;
                break;
            case END_TIME:
                input = addEndTimeParam(ui, input);
                isEndTimeEmpty = false;
                break;
            default:
                isParametersEmpty = false;
                break;
            }

        }

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

        resetBoolean();
        return new AddEventCommand(description, start, end);
    }

    /**
     * Parses the addDeadline command that the user inputs.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return AddDeadlineCommand with the parameters input by the user.
     */
    Command parseAddDeadlineCommand(Ui ui, String input) {

        LOGGER.log(Level.INFO, "Parsing addDeadline command...");

        while (isParametersEmpty) {
            MissingParam param = checkDeadlineParameters(input);

            switch (param) {
            case DESCRIPTION:   // description is missing
                input = addDeadlineDescriptionParam(ui, input);
                isDescriptionEmpty = false;
                break;
            case END_TIME:
                input = addByDateTime(ui, input);
                isEndTimeEmpty = false;
                break;
            default:
                isParametersEmpty = false;
                break;
            }

        }

        int lastIndexOfAddDeadlineCommand = input.indexOf(PARAM_ADD_DEADLINE) + PARAM_ADD_DEADLINE.length();
        int firstIndexOfByCommand = input.indexOf(PARAM_BY);
        int lastIndexOfByCommand = firstIndexOfByCommand + PARAM_BY.length();

        String description = input.substring(lastIndexOfAddDeadlineCommand, firstIndexOfByCommand).trim();
        String byInput = input.substring(lastIndexOfByCommand).trim();
        LocalDateTime by = LocalDateTime.parse(byInput, DATE_TIME_FORMATTER);

        resetBoolean();
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
     * Parses the deleteNotes command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return ShowNotesCommand with the parameters input by the user.
     */
    private Command parseDeleteNotesCommand(String input) {
        LOGGER.log(Level.INFO, "Parsing deleteNotes command...");

        int lastIndexOfDeleteNotesCommand = input.indexOf(PARAM_DELETE_NOTES) + PARAM_DELETE_NOTES.length();
        String title = input.substring(lastIndexOfDeleteNotesCommand).trim();

        return new DeleteNotesCommand(title);
    }

    /**
     * Parses the editNotes command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return ShowNotesCommand with the parameters input by the user.
     */
    private Command parseEditNotesCommand(String input) {
        LOGGER.log(Level.INFO, "Parsing editNotes command...");

        int lastIndexOfEditNotesCommand = input.indexOf(PARAM_EDIT_NOTES) + PARAM_EDIT_NOTES.length();
        String title = input.substring(lastIndexOfEditNotesCommand).trim();

        return new EditNotesCommand(title);
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
     * Parses the user Y/N inputs.
     *
     * @param input String containing the user's input.
     * @param ui Input and output interaction with the user.
     * @return A "Y" or "N" string.
     */
    public String parseUserInputYesOrNo(String input, Ui ui) {
        LOGGER.log(Level.INFO, "Start check for Y/N input");
        while (!input.trim().equals("Y") && !input.trim().equals("N")) {
            ui.showInvalidConfirmationMessage();
            input = ui.readCommand();
        }
        LOGGER.log(Level.INFO, "End check for Y/N input");

        return input;
    }

    /**
     * Parses the user T/D inputs.
     *
     * @param input String containing the user's input.
     * @param ui Input and output interaction with the user.
     * @return A "T" or "D" string.
     */
    public String parseUserInputTOrD(String input, Ui ui) {
        while (!input.trim().equals("T") && !input.trim().equals("D")) {
            ui.showInvalidTitleDescriptionConfirmationMessage();
            input = ui.readCommand();
        }
        return input;
    }

    /**
     * Checks for an empty string.
     *
     * @param ui Input and output interaction with the user.
     * @param string The string to be checked.
     * @return A non-empty string.
     */
    private String checkIfEmpty(Ui ui, String string) {
        while (string.trim().length() == 0) {     // empty string
            ui.showEmptyDescriptionMessage();
            string = ui.readCommand();
        }
        return string;
    }

    /**
     * Reset all boolean variables to true.
     *
     */
    private void resetBoolean() {
        isParametersEmpty = true;
        isModuleCodeEmpty = true;
        isDescriptionEmpty = true;
        isDateEmpty = true;
        isStartTimeEmpty = true;
        isEndTimeEmpty = true;
        isDateTimeEmpty = true;
    }

    /**
     * Checks the addLesson input for missing parameters.
     *
     * @param input String containing user's input.
     * @return An enumeration of the missing parameter.
     */
    private MissingParam checkLessonParameters(String input) {
        if (!input.contains(PARAM_TO) && isEndTimeEmpty) {
            return MissingParam.END_TIME;
        } else if (!input.contains(PARAM_TIME) && isStartTimeEmpty) {
            return MissingParam.START_TIME;
        } else if (!input.contains(PARAM_DATE) && isDateEmpty) {
            return MissingParam.DATE;
        } else if (!input.contains(PARAM_CODE) && isModuleCodeEmpty) {
            return MissingParam.MODULE_CODE;
        } else {
            return MissingParam.COMPLETED;
        }
    }

    /**
     * Checks the addEvent input for missing parameters.
     *
     * @param input String containing the user's input.
     * @return An enumeration of the missing parameter.
     */
    private MissingParam checkEventParameters(String input) {
        int lastIndexOfAddEventCommand = input.indexOf(PARAM_ADD_EVENT) + PARAM_ADD_EVENT.length();
        int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);

        if (!input.contains(PARAM_TO) && isEndTimeEmpty) {
            return MissingParam.END_TIME;
        } else if (!input.contains(PARAM_TIME) && isStartTimeEmpty) {
            return MissingParam.START_TIME;
        } else if (!input.contains(PARAM_DATE) && isDateEmpty) {
            return MissingParam.DATE;
        } else if (input.substring(lastIndexOfAddEventCommand, firstIndexOfDateCommand).trim().length() == 0
                && isDescriptionEmpty) {
            return MissingParam.DESCRIPTION;
        } else {
            return MissingParam.COMPLETED;
        }
    }

    /**
     * Checks the addDeadline input for missing parameters.
     *
     * @param input String containing the user's input.
     * @return An enumeration of the missing parameter.
     */
    private MissingParam checkDeadlineParameters(String input) {
        int lastIndexOfAddDeadlineCommand = input.indexOf(PARAM_ADD_DEADLINE) + PARAM_ADD_DEADLINE.length();
        int firstIndexOfByCommand = input.indexOf(PARAM_BY);

        if (!input.contains(PARAM_BY) && isDateTimeEmpty) {
            return MissingParam.END_TIME;
        } else if (input.substring(lastIndexOfAddDeadlineCommand, firstIndexOfByCommand).trim().length() == 0
                && isDescriptionEmpty) {
            return MissingParam.DESCRIPTION;
        } else {
            return MissingParam.COMPLETED;
        }
    }

    /**
     * Adds the event description to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with description added.
     */
    private String addEventDescriptionParam(Ui ui, String input) {
        ui.showAddDescriptionMessage();
        String description = checkIfEmpty(ui, ui.readCommand());
        String[] temp = input.split("/date");
        input = temp[0] + description + " /date" + temp[1];

        return input;
    }

    /**
     * Adds the deadline description to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with description added.
     */
    private String addDeadlineDescriptionParam(Ui ui, String input) {
        ui.showAddDescriptionMessage();
        String description = checkIfEmpty(ui, ui.readCommand());
        String[] temp = input.split("/by");
        input = temp[0] + description + " /by" + temp[1];

        return input;
    }

    /**
     * Adds the module code to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with module code added.
     */
    private String addModuleCodeParam(Ui ui, String input) {
        ui.showAddModuleCodeMessage();
        String moduleCode = checkIfEmpty(ui, ui.readCommand());
        String[] temp = input.split("/date");
        input = temp[0] + "/code" + moduleCode + " /date" + temp[1];

        return input;
    }


    /**
     * Adds the date to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with date added.
     */
    private String addDateParam(Ui ui, String input) {
        ui.showAddDateMessage();
        String date = checkIfEmpty(ui, ui.readCommand());
        String[] temp1 = input.split("/time");
        input = temp1[0] + "/date " + date + " /time" + temp1[1];

        return input;
    }


    /**
     * Adds the start time to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with start time added.
     */
    private String addStartTimeParam(Ui ui, String input) {
        ui.showAddStartTimeMessage();
        String startTime = checkIfEmpty(ui, ui.readCommand());
        String[] temp2 = input.split("/to");
        input = temp2[0] + "/time " + startTime + " /to" + temp2[1];

        return input;
    }

    /**
     * Adds the end time to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with end time added.
     */
    private String addEndTimeParam(Ui ui, String input) {
        ui.showAddEndTimeMessage();
        String endTime = checkIfEmpty(ui, ui.readCommand());
        input = input + " /to " + endTime;

        return input;
    }

    /**
     * Adds the date and time to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with date and time added.
     */
    private String addByDateTime(Ui ui, String input) {
        ui.showAddDateTimeMessage();
        String byDateTime = checkIfEmpty(ui, ui.readCommand());
        input = input + " /by" + byDateTime;

        return input;
    }

    /**
     * Parses the user's input into a Command object that can later be executed.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return Command that the user inputs.
     */
    public Command parseCommand(String input, Ui ui) throws ParserException {

        LOGGER.log(Level.INFO, "Parsing user input for command...");

        try {
            String commandType = getCommandType(input);

            switch (commandType) {

            case (PARAM_ADD_LESSON):
                return parseAddLessonCommand(ui, input);

            case (PARAM_ADD_EVENT):
                return parseAddEventCommand(ui, input);

            case (PARAM_ADD_DEADLINE):
                return parseAddDeadlineCommand(ui, input);

            case (PARAM_ADD_NOTES):
                return parseAddNotesCommand(input);

            case (PARAM_SHOW_NOTES):
                return parseShowNotesCommand(input);

            case (PARAM_DELETE_NOTES):
                return parseDeleteNotesCommand(input);

            case(PARAM_EDIT_NOTES):
                return parseEditNotesCommand(input);

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
