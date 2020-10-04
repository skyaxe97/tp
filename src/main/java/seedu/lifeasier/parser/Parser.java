package seedu.lifeasier.parser;

import seedu.lifeasier.commands.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {

    public static final String PARAM_ADD_LESSON = "addLesson";
    public static final String PARAM_ADD_EVENT = "addEvent";
    public static final String PARAM_ADD_DEADLINE = "addDeadline";
    public static final String PARAM_ADD_NOTES = "addNotes";
    public static final String PARAM_SHOW_NOTES = "showNotes";
    public static final String PARAM_DISPLAY = "display";
    public static final String PARAM_HELP = "help";
    public static final String PARAM_EXIT = "exit";

    public static final String PARAM_CODE = "/code";
    public static final String PARAM_DATE = "/date";
    public static final String PARAM_TIME = "/time";
    public static final String PARAM_TO = "/to";
    public static final String PARAM_BY = "/by";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");

    private boolean isAddLessonCommand(String input) {
        return input.startsWith(PARAM_ADD_LESSON);
    }

    private boolean isAddEventCommand(String input) {
        return input.startsWith(PARAM_ADD_EVENT);
    }

    private boolean isAddDeadlineCommand(String input) {
        return input.startsWith(PARAM_ADD_DEADLINE);
    }

    private boolean isAddNotesCommand(String input) {
        return input.startsWith(PARAM_ADD_NOTES);
    }

    private boolean isShowNotesCommand(String input) {
        return input.startsWith(PARAM_SHOW_NOTES);
    }

    private boolean isDisplayScheduleCommand(String input) {
        return input.startsWith(PARAM_DISPLAY);
    }

    private boolean isHelpCommand(String input) {
        return input.equals(PARAM_HELP);
    }

    private boolean isExitCommand(String input) {
        return input.equals(PARAM_EXIT);
    }

    private Command parseAddLessonCommand(String input) {

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
        LocalDateTime start = LocalDateTime.parse(date + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + endTime, DATE_TIME_FORMATTER);

        return new AddLessonCommand(moduleCode, start, end);
    }

    private Command parseAddEventCommand(String input) {

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
        LocalDateTime start = LocalDateTime.parse(date + startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(date + endTime, DATE_TIME_FORMATTER);

        return new AddEventCommand(description, start, end);
    }

    private Command parseAddDeadlineCommand(String input) {

        int lastIndexOfAddDeadlineCommand = input.indexOf(PARAM_ADD_DEADLINE) + PARAM_ADD_DEADLINE.length();
        int firstIndexOfByCommand = input.indexOf(PARAM_BY);
        int lastIndexOfByCommand = firstIndexOfByCommand + PARAM_BY.length();

        String description = input.substring(lastIndexOfAddDeadlineCommand, firstIndexOfByCommand);
        String byInput = input.substring(lastIndexOfByCommand).trim();
        LocalDateTime by = LocalDateTime.parse(byInput, DATE_TIME_FORMATTER);

        return new AddDeadlineCommand(description, by);
    }

    private Command parseAddNotesCommand(String input) {

        int lastIndexOfAddNotesCommand = input.indexOf(PARAM_ADD_NOTES) + PARAM_ADD_NOTES.length();
        String title = input.substring(lastIndexOfAddNotesCommand).trim();

        return new AddNotesCommand(title);
    }

    private Command parseShowNotesCommand(String input) {
        int lastIndexOfShowNotesCommand = input.indexOf(PARAM_SHOW_NOTES) + PARAM_SHOW_NOTES.length();
        String title = input.substring(lastIndexOfShowNotesCommand).trim();

        return new ShowNotesCommand(title);
    }

    private Command parseDisplayScheduleCommand(String input) {
        int lastIndexOfDisplayScheduleCommand = input.indexOf(PARAM_DISPLAY) + PARAM_DISPLAY.length();
        String toDisplay = input.substring(lastIndexOfDisplayScheduleCommand).trim();

        return new DisplayScheduleCommand(toDisplay);
    }


    public Command parseCommand(String input) throws ParserException {

        if (isAddLessonCommand(input)) {
            return parseAddLessonCommand(input);

        } else if (isAddEventCommand(input)) {
            return parseAddEventCommand(input);

        } else if (isAddDeadlineCommand(input)) {
            return parseAddDeadlineCommand(input);

        } else if (isAddNotesCommand(input)) {
            return parseAddNotesCommand(input);

        } else if (isShowNotesCommand(input)) {
            return parseShowNotesCommand(input);

        } else if (isDisplayScheduleCommand(input)) {
            return parseDisplayScheduleCommand(input);

        } else if (isHelpCommand(input)) {
            return new HelpCommand();

        } else if (isExitCommand(input)) {
            return new ExitCommand();

        } else {
            throw new ParserException();
        }
    }
}
