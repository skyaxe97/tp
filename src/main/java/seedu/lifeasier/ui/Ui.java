package seedu.lifeasier.ui;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.Task;

import java.util.Scanner;

/**
 * The Ui class deals with all interactions with the user.
 */
public class Ui {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31;1m";
    public static final String ANSI_GREEN = "\u001B[32;1m";
    public static final String ANSI_YELLOW = "\u001B[33;1m";
    public static final String ANSI_BLUE = "\u001B[34;1m";
    public static final String ANSI_PURPLE = "\u001B[35;1m";
    public static final String ANSI_CYAN = "\u001B[36;1m";

    public static final String SEPARATOR = "========================================================================="
            + "======================================";
    public static final String PARAM_LESSON = "lesson";
    public static final String PARAM_EVENT = "event";
    public static final String PARAM_DEADLINE = "deadline";
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
    public static final String ERROR_INVALID_INPUT = "Oh no, I do not understand this input!";

    //General UI messages
    public static final String MESSAGE_GOODBYE = "Goodbye, hope to see you again soon!";
    public static final String MESSAGE_GREETING = "Hello! What can I do for you today?";
    public static final String MESSAGE_HELP_COMMAND = " -Type 'help' for a list the list of available commands";

    //Help message
    public static final String HELP_MESSAGE = "These are the commands that are available:\n"
            + ANSI_PURPLE + "IMPORTANT INFORMATION:\n" + ANSI_RESET
            + " * Words in UPPER_CASE are the parameters to be supplied by the user\n"
            + " * Commands are CASE SENSITIVE\n"
            + " * Command parameters have to be input in the order as stated\n"
            + " * Items in square brackets are optional, e.g [DATE]\n"
            + " * Commands with the " + ANSI_CYAN + "[P]" + ANSI_RESET +  " can take partial commands\n"
            + "   e.g " + ANSI_YELLOW + "addLesson" + ANSI_RESET + " and " + ANSI_YELLOW
            + "addLesson /code CS2113 /to 18:00" + ANSI_RESET + " are valid commands\n"
            + " * All dates are in the " + ANSI_CYAN + "DD-MM-YY" + ANSI_RESET + " and times in the "
            + ANSI_CYAN + "HH:MM" + ANSI_RESET + " format\n"
            + "\n"
            + ANSI_CYAN + "COMMANDS\n" + ANSI_RESET
            + "*****************************************************************************************************\n"
            + "help -------------------------------------------------------------------- Displays available commands\n"
            + "addLesson /code MODULE_CODE /date DATE /time START /to END /repeats ---------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds a lesson\n"
            + "addEvent EVENT_NAME /date DATE /time START /to END /repeats ------------------------ "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds an event\n"
            + "addDeadline DEADLINE_NAME /by DATETIME /repeats----------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds a deadline\n"
            + "editLesson MODULE_CODE ------------------------------------------------------------ "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits a lesson\n"
            + "editEvent EVENT_NAME -------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits an event\n"
            + "editDeadline DEADLINE_NAME ------------------------------------------------------ "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits a deadline\n"
            + "deleteTask /type TYPE /name NAME ----------------------------------------------------- Deletes a task\n"
            + "addNotes TITLE ------------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds a new note\n"
            + "showNotes TITLE -------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Shows selected note\n"
            + "archive ----------------------------------------------------------------- Archives all existing notes\n"
            + "editNotes TITLE ------------------------------------------------------------ "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits a selected note\n"
            + "deleteNotes TITLE -------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Deletes a selected note\n"
            + "undo TYPE ------------------------------------------------ Undoes the last edit/deleted task or notes\n"
            + "display [WEEK] --------------------------------------------- Displays either weekly or daily schedule\n"
            + "freeTime -------------------------------------------- Tells you your longest block of free time today\n"
            + "sleepTime ------------------------------------------------- Tells you how much time you have to sleep\n"
            + "exit ------------------------------------------------------------------- Closes the LifEasier program\n"
            + "*****************************************************************************************************\n"
            + ANSI_GREEN + "For more detailed information, please visit the online user guide at:\n" + ANSI_RESET
            + "https://ay2021s1-cs2113t-w13-4.github.io/tp/UserGuide";

    //Input format messages
    public static final String NEW_DEADLINE_TIME_INPUT_FORMAT = "/by DATETIME";
    public static final String NEW_EVENT_TIME_INPUT_FORMAT = "/date DATE /time START /to END";
    public static final String NEW_LESSON_TIME_INPUT_FORMAT = "/date DATE /time START /to END";

    private Scanner conversation;

    public Ui() {
        this.conversation = new Scanner(System.in);
    }

    public void printSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Colours input string to a green colour.
     *
     * @param string String to be coloured.
     * @return Green coloured string.
     */
    public String colourTextGreen(String string) {
        return ANSI_GREEN + string + ANSI_RESET;
    }

    /**
     * Colours input string to a red colour.
     *
     * @param string String to be coloured.
     * @return Red coloured string.
     */
    public String colourTextRed(String string) {
        return ANSI_RED + string + ANSI_RESET;
    }

    /**
     * Colours input string to a cyan colour.
     *
     * @param string String to be coloured
     * @return Cyan coloured string.
     */
    public String colourTextCyan(String string) {
        return ANSI_CYAN + string + ANSI_RESET;
    }

    /**
     * Colours input string to a yellow colour.
     *
     * @param string String to be coloured
     * @return Yellow coloured string.
     */
    public String colourTextYellow(String string) {
        return ANSI_YELLOW + string + ANSI_RESET;
    }

    public void showLogo() {
        printSeparator();
        printSeparator();
        printLogo();
        printSeparator();
        printSeparator();
    }

    public void showGreetingMessage() {
        System.out.println(colourTextCyan(MESSAGE_GREETING));
        System.out.println(colourTextGreen(MESSAGE_HELP_COMMAND));
    }

    public void showAddConfirmationMessage(Task task) {
        System.out.println(colourTextGreen("Done! I've added \"" + task + "\" to your calendar"));
        printSeparator();
    }

    private void printLogo() {
        System.out.println(colourTextCyan(LOGO));
    }

    public void showHelp() {
        System.out.println(SEPARATOR);
        System.out.println(HELP_MESSAGE);
        System.out.println(SEPARATOR);
    }

    /**
     * Prompts continually for user command and checks if save delimiter has been added to prevent errors.
     *
     * @return User command without save delimiters.
     */
    public String readCommand() {
        boolean isCommandWithoutDelimiter = false;
        String userInput = "";
        do {
            userInput = conversation.nextLine();

            try {
                if (userInput.contains("=-=")) {
                    throw new SaveDelimiterException();
                }
                isCommandWithoutDelimiter = true;
            } catch (SaveDelimiterException e) {
                System.out.println(colourTextRed("Inputs cannot contain the character sequence: =-="));
                System.out.println(colourTextCyan("Please re-enter your input:"));
            }
        } while (!isCommandWithoutDelimiter);

        return userInput;
    }

    public int readSingleIntInput() {
        return Integer.parseInt(readCommand());
    }

    public void showSelectTaskToEdit(String type) {
        System.out.println(colourTextCyan("Please select the " + type + " you want to edit."));
    }

    public void showSelectParameterToEdit() {
        System.out.println(colourTextCyan("Please select the parameter you want to edit."));
    }

    public void showSelectTaskToDelete(String type) {
        System.out.println(colourTextCyan("Please select the " + type + " you want to delete."));
    }

    public void showInputFormat(String type) {
        switch (type) {

        case (PARAM_EVENT):
            System.out.println(colourTextCyan("Please input your new time in this format: "
                    + NEW_EVENT_TIME_INPUT_FORMAT));
            break;

        case (PARAM_DEADLINE):
            System.out.println(colourTextCyan("Please input your new time in this format: "
                    + NEW_DEADLINE_TIME_INPUT_FORMAT));
            break;

        case (PARAM_LESSON):
            System.out.println(colourTextCyan("Please input your new time in this format: "
                    + NEW_LESSON_TIME_INPUT_FORMAT));
            break;

        default:
            break;
        }
    }

    public void showInputMessage(String type) {
        switch (type) {

        case (PARAM_LESSON):
            System.out.println(colourTextCyan("Please input your new Module Code"));
            break;

        case (PARAM_DEADLINE):
            System.out.println(colourTextCyan("Please input your new Deadline name"));
            break;

        case (PARAM_EVENT):
            System.out.println(colourTextCyan("Please input your new Event name"));
            break;

        default:
            break;
        }
    }

    public void showEditableParametersMessage(String type) {
        switch (type) {

        case (PARAM_LESSON):
            System.out.println("1. Module Code\n2. Time");
            break;

        case (PARAM_DEADLINE):
            System.out.println("1. Deadline Name\n2. Time");
            break;

        case (PARAM_EVENT):
            System.out.println("1. Event Name\n2. Time");
            break;

        default:
            break;
        }
    }

    public void showEditConfirmationMessage() {
        System.out.println(colourTextGreen("Your edit has been saved."));
    }

    public void showDeleteConfirmationMessage() {
        System.out.println(colourTextGreen("The task you selected has been deleted."));
    }

    public void showInvalidCommandError() {
        System.out.println(colourTextRed(ERROR_INVALID_COMMAND));
    }

    public void showInvalidInputMessage() {
        System.out.println(colourTextRed(ERROR_INVALID_INPUT));
    }

    public void showGoodbyeMessage() {
        System.out.println(colourTextCyan(MESSAGE_GOODBYE));
    }

    public void showNoteTitleMessage() {
        System.out.println(colourTextCyan("Please put in a title:\n"));
    }

    public void showNoteDescriptionMessage() {
        System.out.println(colourTextCyan("Alright! Please fill in your notes.\n"));
    }

    public void showFileCreationError() {
        System.out.println(colourTextRed("Something went wrong... Save file creation failed..."));
    }

    public void showDataLoadingMessage() {
        System.out.println(colourTextGreen("Reading your save data. New saves will be created "
                + "if no saves are found."));
    }

    public void showNoDataToArchiveMessage() {
        System.out.println(colourTextRed("You do not have any data available for archiving."));
        printSeparator();
    }

    public void showFileArchiveError() {
        System.out.println(colourTextRed("There was an error archiving your data"));
    }

    public void showArchiveStartMessage() {
        printSeparator();
        System.out.println(colourTextGreen("Starting archiving..."));
    }

    public void showArchiveEndMessage() {
        System.out.println(colourTextGreen("Archiving successful!"));
    }

    public void showFileReadError() {
        System.out.println(colourTextRed("Something went wrong, unable to read from save file..."));
    }

    public void showFileWriteError() {
        System.out.println(colourTextRed("Something went wrong while saving your data..."));
    }

    public void showDirectoryCreationFailedError() {
        System.out.println(colourTextRed("Directory creation failed..."));
    }

    public void showInvalidNumberMessage() {
        System.out.println(colourTextRed("The number you inputted is invalid!"));
        printSeparator();
    }

    public void showNoTitleFoundMessage() {
        System.out.println(colourTextRed("The title you inputted is not found..."));
        printSeparator();
    }

    public void showNumberFormatMessage() {
        System.out.println(colourTextRed("\nOpps! The input must be a number!"));
        printSeparator();
    }

    public void showInvalidCastError() {
        System.out.println(colourTextRed("Something went wrong, mismatching task types"));
    }

    public void showSaveDataMissingError() {
        System.out.println(colourTextRed("Encountered an error while reading from the save file "
                + "- Data missing/corrupted"));
    }

    public void showUndeterminableTaskError() {
        System.out.println(colourTextRed("Something went wrong while determining the tasks"));
    }

    public void showParseUnknownCommandMessage() {
        System.out.println(colourTextRed("I'm sorry! I don't understand that command!"));
        printSeparator();
    }

    public void showParseIncorrectCommandFormatMessage() {
        System.out.println(colourTextRed("I'm sorry! Please double check the input of your format!"));
        System.out.println(colourTextGreen("Use the help command for formats of all the commands!"));
        printSeparator();
    }

    public void showParseIncorrectDateTimeMessage() {
        System.out.println(colourTextRed("I'm sorry! Please ensure your dates are in the format dd-mm-yy,"));
        System.out.println(colourTextRed("and your times are in the format hh:mm!"));
        printSeparator();
    }

    public void showEmptyDescriptionMessage() {
        System.out.println(colourTextRed("Empty description! =O\n"));
    }

    public void showNoMatchesMessage(String type) {
        System.out.println(colourTextRed("Sorry! There is no " + type + " matching your query. Please "
                + "re-enter your command."));
    }

    public void showNoteAddedMessage() {
        System.out.println(colourTextGreen("Ok! I've taken note of this note!"));
    }

    public void showMultipleMatchesFoundMessage() {
        System.out.println(colourTextCyan("Multiple matches found! Please select the one you are looking "
                + "for:\n"));
    }

    public void showSelectWhichNoteToViewMessage() {
        System.out.println(colourTextCyan("Please select the notes you want to view:\n"));
    }

    public void showFreeTimeMessage(int startHour, int endHour, int duration) {

        if (duration > 0) {
            System.out.println(colourTextGreen("You have " + duration + " hours of free time between "
                    + startHour + ":00 and " + endHour + ":00!"));
            System.out.println(colourTextCyan("You can try scheduling something in this time!"));

        } else {
            System.out.println(colourTextRed("Unfortunately you have no free time today!"));
            System.out.println(colourTextGreen("You might want to relax a little!"));
        }

        printSeparator();
    }

    public void showNothingScheduledMessage() {
        System.out.println(colourTextGreen("You have nothing on for today and tomorrow!"));
    }

    public void showAvailableSleepTimeMessage(int earliestSleepTime, int latestWakeTime) {
        System.out.println(colourTextGreen("You have nothing on from " + earliestSleepTime + ":00 today to "
                + latestWakeTime + ":00 tomorrow!"));
    }

    public void showSleepDurationMessage(int duration) {
        System.out.println(colourTextGreen("You can sleep for up to " + duration + " hours!"));
    }

    public void showExcessSleepDurationMessage() {
        System.out.println(colourTextGreen("You can sleep for the recommended 8 hours or longer!"));
    }

    public void showSelectWhichNoteToDeleteMessage() {
        System.out.println(colourTextCyan("Please select the notes you want to delete:\n"));
    }

    public void showConfirmDeleteMessage() {
        System.out.println(colourTextCyan("Is this the note you want to delete? (Y/N)\n"));
    }

    public void showInvalidConfirmationMessage() {
        System.out.println(colourTextCyan("Y for Yes and N for No\n"));
    }

    public void showNoteNotDeletedMessage() {
        System.out.println(colourTextGreen("OK! Note not deleted!"));
    }

    public void showNoteDeletedMessage() {
        System.out.println(colourTextGreen("OK! Note deleted!"));
    }

    public void showEditWhichPartMessage() {
        System.out.println(colourTextCyan("Do you want to change the title or description? (T/D)\n"));
    }

    public void showNoteNotEditedMessage() {
        System.out.println(colourTextGreen("OK! Note not edited!"));
        printSeparator();
    }
    
    public void showInvalidTitleDescriptionConfirmationMessage() {
        System.out.println(colourTextRed("T for title and D for Description\n"));
    }

    public void showEditTitleMessage() {
        System.out.println(colourTextCyan("Please input the title you want to change to:\n"));
    }

    public void showEditDescriptionMessage() {
        System.out.println(colourTextCyan("\nPlease input the description you want to change to:\n"));
    }

    public void showSelectWhichNoteToEditMessage() {
        System.out.println(colourTextCyan("Please select the notes you want to edit:\n"));
    }

    public void showEmptyNoteListMessage() {
        System.out.println(colourTextGreen("There's no Notes!"));
        printSeparator();
    }

    public void showAddModuleCodeMessage() {
        System.out.println(colourTextCyan("Please input the module code:"));
    }

    public void showAddDateMessage() {
        System.out.println(colourTextCyan("Please input the date:"));
    }

    public void showAddStartTimeMessage() {
        System.out.println(colourTextCyan("Please input the start time:"));
    }

    public void showAddEndTimeMessage() {
        System.out.println(colourTextCyan("Please input the end time:"));
    }

    public void showAddDescriptionMessage() {
        System.out.println(colourTextCyan("Please input the description:"));
    }

    public void showAddDateTimeMessage() {
        System.out.println(colourTextCyan("Please input the Date Time:"));
    }

    public void showAddRecurrencesMessage() {
        System.out.println(colourTextCyan("Please input the number of times to repeat:"));
    }

    public void printMultipleNoteMatches(NoteList notes, String title) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                System.out.println(i + 1 + ". " + notes.get(i).getTitle() + "\n");
            }
        }
    }

    public void printAllNotes(NoteList notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() + "\n");
        }
    }

    public void showEnterUndoTypeMessage() {
        printSeparator();
        System.out.println(colourTextCyan("To undo a change in tasks, please enter:") + "task");
        System.out.println(colourTextCyan("To undo a change in notes, please enter:") + "note");
    }

    public void showInvalidUndoType() {
        System.out.println(colourTextRed("Invalid undo type, please try again!"));
    }

    public void showInvalidUndoAction() {
        System.out.println(colourTextGreen("Nothing to undo!"));
    }

    public void showUndoTaskEditMessage() {
        System.out.println(colourTextGreen("This task has been reverted back to its previous version!"));
    }

    public void showUndoTaskDeleteMessage() {
        System.out.println(colourTextGreen("This deleted task has been successfully restored!"));
    }

    public void showOldTask(TaskHistory taskHistory) {
        System.out.println(taskHistory.getLastTask().toString());
    }

    public void showUndoNoteEditMessage() {
        System.out.println(colourTextGreen("This note has been reverted back to its previous version!"));
    }

    public void showUndoNoteDeleteMessage() {
        System.out.println(colourTextGreen("This deleted note has been successfully restored!"));
    }

    public void showOldNote(NoteHistory noteHistory) {
        System.out.println(noteHistory.getLastNote());
    }

    public void printEmptyParam(String param) {
        System.out.println(colourTextRed("We detected that you did not fill in the field of " + param
                + ". Please fill it in:"));
    }

    public void showUndeterminableRecurrenceError() {
        System.out.println(colourTextRed("There was an error reading the task recurrence"));
    }

    public void showRecurrencesNumberFormatError() {
        System.out.println(colourTextRed("The input for /repeats is not a valid number! Please try again:"));
    }

    public void printMatchingTask(int index, String task) {
        System.out.println(index + ". " + task);
    }

    public void showIndexOutOfBoundsMessage() {
        System.out.println("The number you have inputted is out of bounds");
    }

    public void showInvalidTimeLogicMessage() {
        System.out.println(colourTextRed("Start time must be before end time"));
        printSeparator();
    }

    public void showInvalidRecurrencesError() {
        System.out.println(colourTextRed("Recurrences must be a positive integer!"));
        printSeparator();
    }

    public void showArchiveEndInformationMessage() {
        System.out.println(colourTextYellow("Note: All current notes have been archived and will no longer be "
                + "read by the program"));
        printSeparator();
    }

    public void showReadErrorHandlerMessage() {
        System.out.println(colourTextRed("LifEasier will continue to run, but the current data will not be "
                + "read. The rest of your data will continue to be loaded"));
        System.out.println(colourTextRed("This is done to protect your stored data\n"));
        System.out.println(colourTextRed("If you have made changes directly to the save file, you are "
                + "recommended to undo those changes"));
        System.out.println("---------------------------------------------------------------------------------------");
    }

    public void showSaveDelimiterError() {
        System.out.println(colourTextRed("LifEasier has detected either missing or additional save delimiters"
                + " in the save file"));
    }

    public void showLocalDateTimeParseError() {
        System.out.println(colourTextRed("Encountered a problem reading the date and time of the task\n"));
        System.out.println(colourTextCyan("The task will continue to be added with a default date to "
                + "preserve your data"));
        System.out.println(colourTextCyan("You can edit the date and times directly from the save file"));
        System.out.println("---------------------------------------------------------------------------------------");
    }

    public void showInvalidModuleCodeMessage() {
        System.out.println(colourTextRed("The entered module code has an invalid format"));
        System.out.println(colourTextGreen("Examples of valid formats: CS1010 / CS2113T / GER1000 / CSS1000X"));
    }
}
