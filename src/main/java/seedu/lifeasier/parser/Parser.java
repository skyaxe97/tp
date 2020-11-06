package seedu.lifeasier.parser;

import seedu.lifeasier.commands.AddDeadlineCommand;
import seedu.lifeasier.commands.AddEventCommand;
import seedu.lifeasier.commands.AddLessonCommand;
import seedu.lifeasier.commands.AddNotesCommand;
import seedu.lifeasier.commands.ArchiveCommand;
import seedu.lifeasier.commands.Command;
import seedu.lifeasier.commands.DeleteNotesCommand;
import seedu.lifeasier.commands.DeleteTaskCommand;
import seedu.lifeasier.commands.DisplayScheduleCommand;
import seedu.lifeasier.commands.EditDeadlineCommand;
import seedu.lifeasier.commands.EditEventCommand;
import seedu.lifeasier.commands.EditLessonCommand;
import seedu.lifeasier.commands.EditNotesCommand;
import seedu.lifeasier.commands.ExitCommand;
import seedu.lifeasier.commands.FreeTimeCommand;
import seedu.lifeasier.commands.HelpCommand;
import seedu.lifeasier.commands.InvalidCommand;
import seedu.lifeasier.commands.ShowNotesCommand;
import seedu.lifeasier.commands.SleepTimeCommand;
import seedu.lifeasier.commands.UndoNoteCommand;
import seedu.lifeasier.commands.UndoTaskCommand;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    private static Logger logger = Logger.getLogger(Parser.class.getName());

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
    public static final String PARAM_FREE_TIME = "freeTime";
    public static final String PARAM_SLEEP_TIME = "sleepTime";
    public static final String PARAM_ARCHIVE = "archive";
    public static final String PARAM_EDIT_LESSON = "editLesson";
    public static final String PARAM_EDIT_DEADLINE = "editDeadline";
    public static final String PARAM_EDIT_EVENT = "editEvent";
    public static final String PARAM_DELETE_TASK = "deleteTask";
    public static final String PARAM_UNDO = "undo";

    public static final String PARAM_CODE = "/code";
    public static final String PARAM_DATE = "/date";
    public static final String PARAM_TIME = "/time";
    public static final String PARAM_TO = "/to";
    public static final String PARAM_BY = "/by";
    public static final String PARAM_TYPE = "/type";
    public static final String PARAM_NAME = "/name";
    public static final String PARAM_REPEATS = "/repeats";

    public static final String PARAM_LESSON = "lesson";
    public static final String PARAM_EVENT = "event";
    public static final String PARAM_DEADLINE = "deadline";

    public static final int INDEX_START = 0;
    public static final int INDEX_END = 1;
    public static final int MAXIMUM_CODE_LENGTH = 8;
    public static final int MINIMUM_CODE_LENGTH = 6;
    public static final int MAXIMUM_SUFFIX_CHARACTER = 1;

    private boolean isParametersEmpty = true;
    private boolean isModuleCodeEmpty = true;
    private boolean isDescriptionEmpty = true;
    private boolean isDateEmpty = true;
    private boolean isStartTimeEmpty = true;
    private boolean isEndTimeEmpty = true;
    private boolean isDateTimeEmpty = true;
    private boolean isRecurrencesEmpty = true;

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
        logger.log(Level.INFO, "Getting command type...");
        int indexOfFirstSpace = input.indexOf(" ");
        if (indexOfFirstSpace > 0) {
            return input.substring(0, indexOfFirstSpace);
        } else {
            return input;
        }
    }

    /**
     * Parses the addLesson command that the user inputs.
     * Also prompts user for missing parameters if there are any.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return AddLessonCommand with the parameters input by the user.
     */
    Command parseAddLessonCommand(Ui ui, String input) {

        logger.log(Level.INFO, "Parsing addLesson command...");
        logger.log(Level.INFO, "Start check for missing parameters.");

        while (isParametersEmpty) {
            MissingParam param = checkMissingLessonParameters(input);

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
                input = addEndTimeParam(ui, input + " ");
                isEndTimeEmpty = false;
                break;
            case RECURRENCES:
                input = addRecurrencesParam(ui, input);
                isRecurrencesEmpty = false;
                break;
            default:
                isParametersEmpty = false;
                isModuleCodeEmpty = true;
                isDateEmpty = true;
                isStartTimeEmpty = true;
                isEndTimeEmpty = true;
                break;
            }

        }
        logger.log(Level.INFO, "End check for missing parameters");

        int lastIndexOfCodeCommand = input.indexOf(PARAM_CODE) + PARAM_CODE.length();
        int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);
        int lastIndexOfDateCommand = firstIndexOfDateCommand + PARAM_DATE.length();
        int firstIndexOfTimeCommand = input.indexOf(PARAM_TIME);
        int lastIndexOfTimeCommand = firstIndexOfTimeCommand + PARAM_TIME.length();
        int firstIndexOfToCommand = input.indexOf(PARAM_TO);
        int lastIndexOfToCommand = firstIndexOfToCommand + PARAM_TO.length();
        int firstIndexOfRepeatsCommand = input.indexOf(PARAM_REPEATS);
        int lastIndexOfRepeatsCommand = firstIndexOfRepeatsCommand + PARAM_REPEATS.length();

        String tempModuleCode = input.substring(lastIndexOfCodeCommand, firstIndexOfDateCommand).trim();
        String tempDate = input.substring(lastIndexOfDateCommand, firstIndexOfTimeCommand).trim();
        String tempStartTime = input.substring(lastIndexOfTimeCommand, firstIndexOfToCommand).trim();
        String tempEndTime =  input.substring(lastIndexOfToCommand, firstIndexOfRepeatsCommand).trim();
        String tempRecurrencesString =  input.substring(lastIndexOfRepeatsCommand).trim();

        String moduleCode = fillIfEmptyParam(ui, tempModuleCode, "/code");
        String date = fillIfEmptyParam(ui, tempDate, "/date");
        String startTime = fillIfEmptyParam(ui, tempStartTime, "/time");
        String endTime =  checkForMidnightEndTime(fillIfEmptyParam(ui, tempEndTime, "/to"));
        String recurrencesString = fillIfEmptyParam(ui, tempRecurrencesString, "/repeats");
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);
        if (start.compareTo(end) > 0) {
            ui.showInvalidTimeLogicMessage();
            return new InvalidCommand();
        }
        int recurrences = checkIfValidNumber(ui, recurrencesString);

        resetBoolean();
        return new AddLessonCommand(moduleCode, start, end, recurrences);
    }

    /**
     * Parses the addEvent command that the user inputs.
     * Also prompts user for missing parameters if there are any.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return AddEventCommand with the parameters input by the user.
     */
    private Command parseAddEventCommand(Ui ui, String input) {

        logger.log(Level.INFO, "Parsing addEvent command...");
        logger.log(Level.INFO, "Start check for missing parameters.");

        while (isParametersEmpty) {
            MissingParam param = checkMissingEventParameters(input);
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
                input = addEndTimeParam(ui, input + " ");
                isEndTimeEmpty = false;
                break;
            case RECURRENCES:
                input = addRecurrencesParam(ui, input);
                isRecurrencesEmpty = false;
                break;
            default:
                isParametersEmpty = false;
                isDescriptionEmpty = true;
                isDateEmpty = true;
                isStartTimeEmpty = true;
                isEndTimeEmpty = true;
                break;
            }

        }
        logger.log(Level.INFO, "End check for missing parameters.");

        int lastIndexOfAddEventCommand = input.indexOf(PARAM_ADD_EVENT) + PARAM_ADD_EVENT.length();
        int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);
        int lastIndexOfDateCommand = firstIndexOfDateCommand + PARAM_DATE.length();
        int firstIndexOfTimeCommand = input.indexOf(PARAM_TIME);
        int lastIndexOfTimeCommand = firstIndexOfTimeCommand + PARAM_TIME.length();
        int firstIndexOfToCommand = input.indexOf(PARAM_TO);
        int lastIndexOfToCommand = firstIndexOfToCommand + PARAM_TO.length();
        int firstIndexOfRepeatsCommand = input.indexOf(PARAM_REPEATS);
        int lastIndexOfRepeatsCommand = firstIndexOfRepeatsCommand + PARAM_REPEATS.length();

        String tempDate = input.substring(lastIndexOfDateCommand, firstIndexOfTimeCommand).trim();
        String tempStartTime = input.substring(lastIndexOfTimeCommand, firstIndexOfToCommand).trim();
        String tempEndTime = input.substring(lastIndexOfToCommand, firstIndexOfRepeatsCommand).trim();
        String tempRecurrencesString = input.substring(lastIndexOfRepeatsCommand).trim();

        String description = input.substring(lastIndexOfAddEventCommand, firstIndexOfDateCommand).trim();
        String date = fillIfEmptyParam(ui, tempDate, "/date");
        String startTime = fillIfEmptyParam(ui, tempStartTime, "/time");
        String endTime =  checkForMidnightEndTime(fillIfEmptyParam(ui, tempEndTime, "/to"));
        String recurrencesString =  fillIfEmptyParam(ui, tempRecurrencesString, "/repeats");
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);

        if (start.compareTo(end) > 0) {
            ui.showInvalidTimeLogicMessage();
            return new InvalidCommand();
        }
        int recurrences = checkIfValidNumber(ui, recurrencesString);

        resetBoolean();
        return new AddEventCommand(description, start, end, recurrences);
    }

    /**
     * Parses the addDeadline command that the user inputs.
     * Also prompts user for missing parameters if there are any.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return AddDeadlineCommand with the parameters input by the user.
     */
    Command parseAddDeadlineCommand(Ui ui, String input) {

        logger.log(Level.INFO, "Parsing addDeadline command...");
        logger.log(Level.INFO, "Start check for missing parameters.");

        while (isParametersEmpty) {
            MissingParam param = checkMissingDeadlineParameters(input);

            switch (param) {
            case DESCRIPTION:   // description is missing
                input = addDeadlineDescriptionParam(ui, input);
                isDescriptionEmpty = false;
                break;
            case END_TIME:
                input = addByDateTime(ui, input + " ");
                isEndTimeEmpty = false;
                break;
            case RECURRENCES:
                input = addRecurrencesParam(ui, input);
                isRecurrencesEmpty = false;
                break;
            default:
                isParametersEmpty = false;
                isDescriptionEmpty = true;
                isEndTimeEmpty = true;
                break;
            }

        }
        logger.log(Level.INFO, "End check for missing parameters.");

        int lastIndexOfAddDeadlineCommand = input.indexOf(PARAM_ADD_DEADLINE) + PARAM_ADD_DEADLINE.length();
        int firstIndexOfByCommand = input.indexOf(PARAM_BY);
        int lastIndexOfByCommand = firstIndexOfByCommand + PARAM_BY.length();
        int firstIndexOfRepeatsCommand = input.indexOf(PARAM_REPEATS);
        int lastIndexOfRepeatsCommand = firstIndexOfRepeatsCommand + PARAM_REPEATS.length();

        String tempByInput = input.substring(lastIndexOfByCommand, firstIndexOfRepeatsCommand).trim();
        String tempRecurencesString = input.substring(lastIndexOfRepeatsCommand).trim();

        String description = input.substring(lastIndexOfAddDeadlineCommand, firstIndexOfByCommand).trim();
        String byInput = fillIfEmptyParam(ui, tempByInput, "/by");
        LocalDateTime by = LocalDateTime.parse(byInput, DATE_TIME_FORMATTER);
        String recurrencesString = fillIfEmptyParam(ui, tempRecurencesString, "/repeats");
        int recurrences = checkIfValidNumber(ui, recurrencesString);

        resetBoolean();
        return new AddDeadlineCommand(description, by, recurrences);
    }

    /**
     * Parses the addNotes command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return AddNotesCommand with the parameters input by the user.
     */
    private Command parseAddNotesCommand(String input) {

        logger.log(Level.INFO, "Parsing addNotes command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_ADD_NOTES) + PARAM_ADD_NOTES.length();
        String title = input.substring(lastIndexOfAddNotesCommand).trim();

        return new AddNotesCommand(title);
    }

    private Command parseEditLessonCommand(String input) {

        logger.log(Level.INFO, "Parsing editLesson command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_EDIT_LESSON) + PARAM_EDIT_LESSON.length();
        String code = input.substring(lastIndexOfAddNotesCommand).trim();

        return new EditLessonCommand(code);
    }

    private Command parseEditEventCommand(String input) {

        logger.log(Level.INFO, "Parsing editEvent command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_EDIT_EVENT) + PARAM_EDIT_EVENT.length();
        String eventName = input.substring(lastIndexOfAddNotesCommand).trim();

        return new EditEventCommand(eventName);
    }

    private Command parseEditDeadlineCommand(String input) {

        logger.log(Level.INFO, "Parsing editDeadline command...");

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_EDIT_DEADLINE) + PARAM_EDIT_DEADLINE.length();
        String deadlineName = input.substring(lastIndexOfAddNotesCommand).trim();

        return new EditDeadlineCommand(deadlineName);
    }

    private Command parseDeleteTaskCommand(String input) {
        String type = "";
        String name = "";
        try {
            logger.log(Level.INFO, "Parsing deleteTask command...");
            int firstIndexOfTypeCommand = input.indexOf(PARAM_TYPE);
            int lastIndexOfTypeCommand = input.indexOf(PARAM_TYPE) + PARAM_TYPE.length();
            if (firstIndexOfTypeCommand == -1) {
                logger.log(Level.SEVERE, "deleteTask command missing TYPE keyword");
                throw new ParserException();
            }

            int firstIndexOfNameCommand = input.indexOf(PARAM_NAME);
            if (firstIndexOfNameCommand == -1) {
                type = input.substring(lastIndexOfTypeCommand).trim();
                name = "";
            } else {
                int lastIndexOfNameCommand = input.indexOf(PARAM_NAME) + PARAM_NAME.length();
                type = input.substring(lastIndexOfTypeCommand, firstIndexOfNameCommand).trim();
                checkValidType(type);
                name = input.substring(lastIndexOfNameCommand).trim();
            }
        } catch (ParserException e) {
            logger.log(Level.SEVERE, "Invalid command...");
        }
        return new DeleteTaskCommand(type, name);
    }

    private void checkValidType(String type) throws ParserException {
        if (!type.equals(PARAM_DEADLINE) && !type.equals(PARAM_EVENT)
                && !type.equals(PARAM_LESSON)) {
            throw new ParserException();
        }
    }

    public LocalDateTime[] parseNewTimeInput(Ui ui, String input, int numOfTimeArgs) throws ParserException {

        logger.log(Level.INFO, "Parsing newTimeInput from user...");
        LocalDateTime[] times = new LocalDateTime[2];
        try {
            switch (numOfTimeArgs) {

            case (1):
                int firstIndexOfByCommand = input.indexOf(PARAM_BY);
                int lastIndexOfByCommand = firstIndexOfByCommand + PARAM_BY.length();

                if (firstIndexOfByCommand == -1) {
                    logger.log(Level.SEVERE, "Input missing BY keyword");
                    throw new ParserException();
                }

                String byInput = input.substring(lastIndexOfByCommand).trim();
                LocalDateTime by = LocalDateTime.parse(byInput, DATE_TIME_FORMATTER);
                times[INDEX_START] = by;
                return times;

            case (2):
                int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);
                int lastIndexOfDateCommand = firstIndexOfDateCommand + PARAM_DATE.length();
                int firstIndexOfTimeCommand = input.indexOf(PARAM_TIME);
                int lastIndexOfTimeCommand = firstIndexOfTimeCommand + PARAM_TIME.length();
                int firstIndexOfToCommand = input.indexOf(PARAM_TO);
                int lastIndexOfToCommand = firstIndexOfToCommand + PARAM_TO.length();

                checkValidTimeKeywords(firstIndexOfDateCommand, firstIndexOfTimeCommand, firstIndexOfToCommand);

                String date = input.substring(lastIndexOfDateCommand, firstIndexOfTimeCommand).trim();
                String startTime = input.substring(lastIndexOfTimeCommand, firstIndexOfToCommand).trim();
                String endTime =  checkForMidnightEndTime(input.substring(lastIndexOfToCommand).trim());
                LocalDateTime start = LocalDateTime.parse(date + " " + startTime, DATE_TIME_FORMATTER);
                LocalDateTime end = LocalDateTime.parse(date + " " + endTime, DATE_TIME_FORMATTER);
                times[INDEX_START] = start;
                times[INDEX_END] = end;
                return times;

            default:
                break;
            }

        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Time input is not in the correct format");
            ui.showLocalDateTimeParseError();
        }
        return times;
    }

    private void checkValidTimeKeywords(int firstIndexOfDateCommand, int firstIndexOfTimeCommand,
                                        int firstIndexOfToCommand) throws ParserException {
        if (firstIndexOfDateCommand == -1 || firstIndexOfTimeCommand == -1 || firstIndexOfToCommand == -1) {
            throw new ParserException();
        }
    }

    /**
     * Parses the showNotes command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return ShowNotesCommand with the parameters input by the user.
     */
    private Command parseShowNotesCommand(String input) {

        logger.log(Level.INFO, "Parsing showNotes command...");

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
        logger.log(Level.INFO, "Parsing deleteNotes command...");

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
        logger.log(Level.INFO, "Parsing editNotes command...");

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

        logger.log(Level.INFO, "Parsing display command...");

        int lastIndexOfDisplayScheduleCommand = input.indexOf(PARAM_DISPLAY) + PARAM_DISPLAY.length();
        String toDisplay = input.substring(lastIndexOfDisplayScheduleCommand).trim();

        return new DisplayScheduleCommand(toDisplay);
    }

    /**
     * Parses the undo command that the user inputs.
     *
     * @param input String containing the user's input.
     * @return Either UndoNoteCommand or UndoTaskCommand depending on the parsed parameter.
     */
    private Command parseUndoCommand(String input) throws ParserException {
        Ui ui = new Ui();
        logger.log(Level.INFO, "Parsing undo command...");

        String undoType;

        try {
            String[] splitInput = input.split(" ");
            undoType = splitInput[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Missing parameter, proceed to prompt");
            undoType = handleMissingUndoParam(ui);
        }

        switch (undoType) {
        case "task":
            return new UndoTaskCommand();
        case "note":
            return new UndoNoteCommand();
        default:
            logger.log(Level.SEVERE, "Error determining undo command type");
            throw new ParserException();
        }
    }

    private String handleMissingUndoParam(Ui ui) {
        boolean isValidField = false;
        String userInput = "";

        while (!isValidField) {
            ui.showEnterUndoTypeMessage();

            userInput = ui.readCommand();

            if (userInput.equals("task") || userInput.equals("note")) {
                isValidField = true;
            } else {
                ui.showInvalidUndoType();
            }
        }
        return userInput;
    }

    /**
     * Parses the user T/D inputs.
     *
     * @param input String containing the user's input.
     * @param ui Input and output interaction with the user.
     * @return A "T" or "D" string.
     */
    public String parseUserInputTOrD(String input, Ui ui) {
        logger.log(Level.INFO, "Start check for T/D input");
        while (!input.trim().equals("T") && !input.trim().equals("D")) {
            ui.showInvalidTitleDescriptionConfirmationMessage();
            input = ui.readCommand();
        }
        logger.log(Level.INFO, "End check for T/D input");
        return input;
    }

    /**
     * Checks for an empty string and prompts the user if empty.
     *
     * @param ui Input and output interaction with the user.
     * @param string The string to be checked.
     * @return A non-empty string.
     */
    public String checkIfEmpty(Ui ui, String string) {
        logger.log(Level.INFO, "Start check for empty string");
        while (string.trim().length() == 0) {     // empty string
            ui.showEmptyDescriptionMessage();
            string = ui.readCommand();
        }
        logger.log(Level.INFO, "End check for empty string");
        return string;

    }

    /**
     * Checks the condition of whether the input is empty.
     *
     * @param input The string to be checked.
     * @param index1 The starting index.
     * @param index2 The ending index.
     * @param factor Status of the factor.
     * @return A non-empty string.
     */
    private boolean isMissingDescription(String input, int index1, int index2, boolean factor) {
        return input.substring(index1, index2).trim().length() == 0 && factor;
    }

    /**
     * Reset all boolean variables to true.
     */
    private void resetBoolean() {
        isParametersEmpty = true;
        isModuleCodeEmpty = true;
        isDescriptionEmpty = true;
        isDateEmpty = true;
        isStartTimeEmpty = true;
        isEndTimeEmpty = true;
        isDateTimeEmpty = true;
        isRecurrencesEmpty = true;
    }

    /**
     * Checks the addLesson input for missing parameters.
     *
     * @param input String containing user's input.
     * @return An enumeration of the missing parameter.
     */
    private MissingParam checkMissingLessonParameters(String input) {
        if (!input.contains(PARAM_REPEATS) && isRecurrencesEmpty) {
            return MissingParam.RECURRENCES;
        } else if (!input.contains(PARAM_TO) && isEndTimeEmpty) {
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
    private MissingParam checkMissingEventParameters(String input) {
        int lastIndexOfAddEventCommand = input.indexOf(PARAM_ADD_EVENT) + PARAM_ADD_EVENT.length();
        int firstIndexOfDateCommand = input.indexOf(PARAM_DATE);

        if (!input.contains(PARAM_REPEATS) && isRecurrencesEmpty) {
            return MissingParam.RECURRENCES;
        } else if (!input.contains(PARAM_TO) && isEndTimeEmpty) {
            return MissingParam.END_TIME;
        } else if (!input.contains(PARAM_TIME) && isStartTimeEmpty) {
            return MissingParam.START_TIME;
        } else if (!input.contains(PARAM_DATE) && isDateEmpty) {
            return MissingParam.DATE;
        } else if (isMissingDescription(input, lastIndexOfAddEventCommand, firstIndexOfDateCommand,
                isDescriptionEmpty)) {
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
    private MissingParam checkMissingDeadlineParameters(String input) {
        int lastIndexOfAddDeadlineCommand = input.indexOf(PARAM_ADD_DEADLINE) + PARAM_ADD_DEADLINE.length();
        int firstIndexOfByCommand = input.indexOf(PARAM_BY);

        if (!input.contains(PARAM_REPEATS) && isRecurrencesEmpty) {
            return MissingParam.RECURRENCES;
        } else if (!input.contains(PARAM_BY) && isDateTimeEmpty) {
            return MissingParam.END_TIME;
        } else if (isMissingDescription(input, lastIndexOfAddDeadlineCommand, firstIndexOfByCommand,
                isDescriptionEmpty)) {
            return MissingParam.DESCRIPTION;
        } else {
            return MissingParam.COMPLETED;
        }
    }
    
    /**
     * Checks if the end time for Lessons and Events inputted by user is 24:00
     * and adjust it to become 23:59.
     *
     * @param endTimeString String containing the end time from user's input.
     * @return A string with the corrected end time.
     */
    private String checkForMidnightEndTime(String endTimeString) {
        if (endTimeString.equals("24:00")) {
            return "23:59";
        }
        return endTimeString;
    }

    /**
     * Adds the event description to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return A string with description added.
     */
    private String addEventDescriptionParam(Ui ui, String input) {
        logger.log(Level.INFO, "Start of adding Event description to string.");
        ui.showAddDescriptionMessage();
        String description = checkIfEmpty(ui, ui.readCommand());
        String[] temp = input.split("/date");
        input = temp[0] + description + " /date" + temp[1];
        logger.log(Level.INFO, "End of adding Event description to string.");
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
        logger.log(Level.INFO, "Start of adding Deadline description to string.");
        ui.showAddDescriptionMessage();
        String description = checkIfEmpty(ui, ui.readCommand());
        String[] temp = input.split("/by");
        input = temp[0] + description + " /by" + temp[1];
        logger.log(Level.INFO, "End of adding Deadline description to string.");
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
        logger.log(Level.INFO, "Start of adding Module Code to string.");
        ui.showAddModuleCodeMessage();
        String moduleCode = checkIfEmpty(ui, ui.readCommand());
        if (!checkIfValidModuleCode(moduleCode)) {
            moduleCode = getValidModuleCode(ui);
        }
        String[] temp = input.split("/date");
        input = temp[0] + "/code" + moduleCode + " /date" + temp[1];
        logger.log(Level.INFO, "End of adding Module Code to string.");
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
        logger.log(Level.INFO, "Start of adding Date to string.");
        ui.showAddDateMessage();
        String date = checkIfEmpty(ui, ui.readCommand());
        String[] temp1 = input.split("/time");
        input = temp1[0] + "/date " + date + " /time" + temp1[1];
        logger.log(Level.INFO, "End of adding Date to string.");

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
        logger.log(Level.INFO, "Start of adding Start Time to string.");
        ui.showAddStartTimeMessage();
        String startTime = checkIfEmpty(ui, ui.readCommand());
        String[] temp2 = input.split("/to");
        input = temp2[0] + "/time " + startTime + " /to" + temp2[1];
        logger.log(Level.INFO, "End of adding Start Time to string.");

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
        logger.log(Level.INFO, "Start of adding End Time to string.");
        ui.showAddEndTimeMessage();
        String endTime = checkIfEmpty(ui, ui.readCommand());
        String[] temp3 = input.split(PARAM_REPEATS);
        input = temp3[0] + " /to " + endTime + "/repeats" + temp3[1];
        logger.log(Level.INFO, "End of adding End Time to string.");

        return input;
    }

    /**
     * Adds the recurrences to the string.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return String with the recurrences added.
     */
    private String addRecurrencesParam(Ui ui, String input) {
        logger.log(Level.INFO, "Start of adding recurrences to string.)");
        ui.showAddRecurrencesMessage();
        String recurrences = checkIfEmpty(ui, ui.readCommand());
        input = input + " /repeats " + recurrences;
        logger.log(Level.INFO, "End of adding recurrences to string.)");

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
        logger.log(Level.INFO, "Start of adding By Time to string.");
        ui.showAddDateTimeMessage();
        String byDateTime = checkIfEmpty(ui, ui.readCommand());
        String[] temp4 = input.split(PARAM_REPEATS);
        input = temp4[0] + " /by " + byDateTime + " /repeats" + temp4[1];
        logger.log(Level.INFO, "End of adding By Time to string.");

        return input;
    }

    private String fillIfEmptyParam(Ui ui, String input, String param) {
        if (input.length() == 0) {
            ui.printEmptyParam(param);
            input = checkIfEmpty(ui, ui.readCommand());
        }
        //Check for valid module code
        if (param.equals(PARAM_CODE)) {
            if (!checkIfValidModuleCode(input)) {
                input = getValidModuleCode(ui);
            }
        }
        return input;
    }

    public int checkIfValidNumber(Ui ui, String input) {
        while (!isNumeric(input) || Integer.parseInt(input) < 0) {
            ui.showRecurrencesNumberFormatError();
            input = ui.readCommand();
        }
        return Integer.parseInt(input);
    }

    private boolean isNumeric(String input) {
        try {
            int number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the input module code is in a valid format.
     *
     * @param moduleCode The module code to be checked.
     * @return true if the module code is determined to be valid.
     */
    public boolean checkIfValidModuleCode(String moduleCode) {
        logger.log(Level.INFO, "Starting module code verification for: " + moduleCode);
        moduleCode = moduleCode.trim();
        char[] moduleCodeElements = moduleCode.toCharArray();

        //Check if input for module code is within valid length of 8
        if (moduleCodeElements.length > MAXIMUM_CODE_LENGTH || moduleCodeElements.length < MINIMUM_CODE_LENGTH) {
            logger.log(Level.INFO, "Invalid module code length");
            return false;
        }

        int consecutiveLetterCount = getConsecutiveLetterCount(moduleCodeElements);
        if (consecutiveLetterCount < 2 || consecutiveLetterCount > 3) {
            logger.log(Level.INFO, "Invalid prefix length");
            return false;
        }

        int consecutiveNumberCount = getConsecutiveNumberCount(moduleCodeElements, consecutiveLetterCount);
        if (consecutiveNumberCount != 4) {
            logger.log(Level.INFO, "Invalid numeric code length");
            return false;
        }

        //Checking for existence of postfix
        int totalCharacterCount = consecutiveLetterCount + consecutiveNumberCount;
        boolean isFinalLength = totalCharacterCount == moduleCodeElements.length;
        if (isFinalLength) {
            logger.log(Level.INFO, "Check done - Valid module code");
            return true;
        }

        //Possible postfix, check for valid postfix
        int numberOfPostfixCharacters = moduleCodeElements.length - totalCharacterCount;
        if (numberOfPostfixCharacters > MAXIMUM_SUFFIX_CHARACTER) {
            logger.log(Level.INFO, "Invalid postfix length");
            return false;
        }

        logger.log(Level.INFO, "Checking for module postfix letter");
        int lastIndex = moduleCodeElements.length - 1;
        if (Character.isLetter(moduleCodeElements[lastIndex])) {
            logger.log(Level.INFO, "Check done - Valid module code");
            return true;
        } else {
            logger.log(Level.INFO, "Invalid module code postfix");
            return false;
        }
    }

    /**
     * Counts the number of consecutive numbers for the module code.
     *
     * @param moduleCodeElements Array of characters from module code.
     * @param letterCount Starting index from the number of prefix characters.
     * @return The number of consecutive numbers.
     */
    protected int getConsecutiveNumberCount(char[] moduleCodeElements, int letterCount) {
        assert letterCount != 0 : "LetterCount should not be 0 at this state";
        logger.log(Level.INFO, "Start numeric code count");
        int numberCount = 0;
        for (int i = letterCount; i < moduleCodeElements.length; i++) {
            if (Character.isDigit(moduleCodeElements[i])) {
                numberCount++;
            } else {
                break;
            }
        }
        return numberCount;
    }

    /**
     * Counts the number of consecutive letters for the module code prefix within the first 4 elements of array.
     *
     * @param moduleCodeElements Array of characters from module code.
     * @return The number of consecutive letters.
     */
    protected int getConsecutiveLetterCount(char[] moduleCodeElements) {
        logger.log(Level.INFO, "Start prefix letter count");
        int letterCount = 0;
        for (int i = 0; i < 4; i++) {
            if (Character.isLetter(moduleCodeElements[i])) {
                letterCount++;
            } else {
                break;
            }
        }
        return letterCount;
    }

    /**
     * Repeatedly prompts for valid module code until a valid one is input.
     *
     * @return Valid module code.
     */
    public String getValidModuleCode(Ui ui) {
        String moduleCode = "";
        boolean isModuleCodeValid = false;

        while (!isModuleCodeValid) {
            ui.showInvalidModuleCodePrompt();
            moduleCode = ui.readCommand();
            isModuleCodeValid = checkIfValidModuleCode(moduleCode);
        }
        return moduleCode;
    }

    /**
     * Parses the user's input into a Command object that can later be executed.
     *
     * @param ui Input and output interaction with the user.
     * @param input String containing the user's input.
     * @return Command that the user inputs.
     */
    public Command parseCommand(String input, Ui ui) throws ParserException {

        logger.log(Level.INFO, "Parsing user input for command...");
        input = input.trim();

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

            case (PARAM_FREE_TIME):
                return new FreeTimeCommand();

            case (PARAM_SLEEP_TIME):
                return new SleepTimeCommand();

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

            case (PARAM_UNDO):
                return parseUndoCommand(input);

            default:
                logger.log(Level.SEVERE, "User input command is invalid");
                throw new ParserException();
            }

        } catch (IndexOutOfBoundsException e) {
            resetBoolean();
            logger.log(Level.SEVERE, "User input command is invalid");
            ui.showParseIncorrectCommandFormatMessage();

        } catch (DateTimeParseException e) {
            resetBoolean();
            logger.log(Level.SEVERE, "Time input is invalid");
            ui.showParseIncorrectDateTimeMessage();
        } catch (NumberFormatException e) {
            resetBoolean();
            logger.log(Level.SEVERE, "/repeats input is an invalid number");
            ui.showNumberFormatMessage();
        }

        return new InvalidCommand();

    }

}
