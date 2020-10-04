package seedu.lifeasier;

import seedu.lifeasier.commands.Command;

public class Parser {

    public static Command parseCommand(String fullCommand) {
        return new Command(fullCommand);
    }
}
