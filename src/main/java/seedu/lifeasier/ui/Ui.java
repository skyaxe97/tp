package seedu.lifeasier.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * The Ui class deals with all interactions with the user.
 */
public class Ui {

    public static final String SEPARATOR = "=========================================================================";
    public static final String LOGO = "\n"
            + "  _      _  __ ______          _\n"
            + " | |    (_)/ _|  ____|        (_)\n"
            + " | |     _| |_| |__   __ _ ___ _  ___ _ __\n"
            + " | |    | |  _|  __| / _` / __| |/ _ \\ '__|\n"
            + " | |____| | | | |___| (_| \\__ \\ |  __/ |\n"
            + " |______|_|_| |______\\__,_|___/_|\\___|_|\n"
            + "\n";

    //Error messages
    public static final String ERROR_INVALID_COMMAND = "Oh no, I do not understand this command! Type 'help' for "
            + "a list of available commands";

    //General UI messages
    public static final String MESSAGE_GOODBYE = "Goodbye, hope to see you again soon!";
    public static final String MESSAGE_GREETING = "Hello [NAME], what can I do for you today?";
    public static final String MESSAGE_HELP_COMMAND = " -Type 'help' for a list the list of available commands";

    //Help message
    public static final String HELP_MESSAGE = "These are the commands that are available:\n"
            + "Notes about the command format:\n"
            + " * Words in UPPER_CASE are the parameters to be supplied by the user\n"
            + " * Items in square brackets are optional, e.g [DATE]\n"
            + "\n"
            + "COMMANDS\n"
            + "*************************************************************************\n"
            + "help ---------------------------------------- Displays available commands\n"
            + "addLesson /code MODULE_CODE /date DATE /time START /to END -- Adds lesson\n"
            + "addEvent EVENT_NAME /date DATE /time START /to END -------- Adds an event\n"
            + "addDeadline DEADLINE_NAME /by DATETIME ------------------ Adds a deadline\n"
            + "addNotes TITLE ------------------------------------------ Adds a new note\n"
            + "showNotes TITLE ------------------------------------- Shows selected note\n"
            + "display WEEK/DAY --------------- Displays either weekly or daily schedule\n"
            + "exit --------------------------------------- Closes the LifEasier program\n"
            + "*************************************************************************\n"
            + "For more detailed information, please visit the online user guide at:\n";

    private Scanner conversation;

    public Ui() {
        this.conversation = new Scanner(System.in);
    }

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

    public void showHelp() {
        System.out.println(SEPARATOR);
        System.out.println(HELP_MESSAGE);
        System.out.println(SEPARATOR);
    }

    /**
     * Returns read user command.
     */
    public String readCommand() {
        return conversation.nextLine();
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

    public void showDirectoryDetected() {
        System.out.println("Save directory detected!");
    }

    public DayOfWeek getCurrDayOfWeek(int dayCount) {
        LocalDateTime currDay = LocalDateTime.now().plus(dayCount, ChronoUnit.DAYS);
        return currDay.getDayOfWeek();
    }

    public void showNoDirectoryDetected() {
        System.out.println("No save directory detected, creating new save directory...");
    }

    public void showNewDirectoryCreated() {
        System.out.println("New save directory named 'LifEasierSaves' created!");
    }

    public void showFileCreationError() {
        System.out.println("Something went wrong... Save file creation failed...");
    }

    public void showParseUnknownCommandMessage() {
        System.out.println("I'm sorry! I don't understand that command!");
        printSeparator();
    }

    public void showParseIncorrectCommandFormatMessage() {
        System.out.println("I'm sorry! Please double check the input of your format!");
        System.out.println("Use the help command for formats of all the commands!");
        printSeparator();
    }

    public void showParseIncorrectDateTimeMessage() {
        System.out.println("I'm sorry! Please ensure your dates are in the format dd-mm-yy,");
        System.out.println("and your times are in the format hh:mm!");
        printSeparator();
    }

}
