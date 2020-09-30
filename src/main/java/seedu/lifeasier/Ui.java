package seedu.lifeasier;

import java.util.Scanner;

/**
 * The Ui class deals with all interactions with the user
 */
public class Ui {

    public static final String SEPARATOR = "=========================================================================";
    public static final String LOGO = "\n"
            + "  _      _  __ ______          _           \n"
            + " | |    (_)/ _|  ____|        (_)          \n"
            + " | |     _| |_| |__   __ _ ___ _  ___ _ __ \n"
            + " | |    | |  _|  __| / _` / __| |/ _ \\ '__|\n"
            + " | |____| | | | |___| (_| \\__ \\ |  __/ |   \n"
            + " |______|_|_| |______\\__,_|___/_|\\___|_|   \n"
            + "\n";

    //Error messages
    public static final String ERROR_INVALID_COMMAND = "Oh no, I do not understand this command! Type 'help' for " +
            "a list of available commands";

    //General UI messages
    public static final String MESSAGE_GOODBYE = "Goodbye, hope to see you again soon!";
    public static final String MESSAGE_GREETING = "Hello [NAME], what can I do for you today?";
    public static final String MESSAGE_HELP_COMMAND = " -Type 'help' for a list the list of available commands";

    public void printSeparator() {
        System.out.println(SEPARATOR);
    }

    public void showWelcomeMessage() {
        printSeparator();
        printSeparator();
        printLogo();
        printSeparator();
        printSeparator();
        showGreetingMessage();
    }

    private void showGreetingMessage() {
        System.out.println(MESSAGE_GREETING);
        System.out.println(MESSAGE_HELP_COMMAND);
    }

    private void printLogo() {
        System.out.println(LOGO);
    }

    /**
     * Returns read user command
     */
    public String readCommand() {
        Scanner fullCommand = new Scanner(System.in);

        return fullCommand.nextLine();
    }

    public void showInvalidCommandError() {
        System.out.println(ERROR_INVALID_COMMAND);
    }

    public void showGoodbyeMessage() {
        System.out.println(MESSAGE_GOODBYE);
    }

    public void showNoteTitleMessage() {
        System.out.println("Please put in a title:\n");
    }

    public void showNoteDescriptionMessage() {
        System.out.println("Alright! Please fill in your notes.\n");
    }
}
