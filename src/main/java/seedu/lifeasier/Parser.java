package seedu.lifeasier;

public class Parser {

    public static Command parseCommand(String fullCommand) {
        return new Command(fullCommand);
    }
}
