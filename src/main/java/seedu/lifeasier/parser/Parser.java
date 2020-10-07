package seedu.lifeasier.parser;

import seedu.lifeasier.commands.AddDeadlineCommand;
import seedu.lifeasier.commands.AddEventCommand;
import seedu.lifeasier.commands.AddLessonCommand;
import seedu.lifeasier.commands.AddNotesCommand;
import seedu.lifeasier.commands.Command;
import seedu.lifeasier.commands.DisplayScheduleCommand;
import seedu.lifeasier.commands.ExitCommand;
import seedu.lifeasier.commands.HelpCommand;
import seedu.lifeasier.commands.ShowNotesCommand;

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

    private String getCommandType(String input) {
        int indexOfFirstSpace = input.indexOf(" ");
        return input.substring(0, indexOfFirstSpace);

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

        String commandType = getCommandType(input);

        switch (commandType) {

        case(PARAM_ADD_LESSON):
            return parseAddLessonCommand(input);

        case(PARAM_ADD_EVENT):
            return parseAddEventCommand(input);

        case(PARAM_ADD_DEADLINE):
            return parseAddDeadlineCommand(input);

        case(PARAM_ADD_NOTES):
            return parseAddNotesCommand(input);

        case(PARAM_SHOW_NOTES):
            return parseShowNotesCommand(input);

        case(PARAM_DISPLAY):
            return parseDisplayScheduleCommand(input);

        case(PARAM_HELP):
            return new HelpCommand();

        case(PARAM_EXIT):
            return new ExitCommand();

        default:
            throw new ParserException();
        }
    }
}
