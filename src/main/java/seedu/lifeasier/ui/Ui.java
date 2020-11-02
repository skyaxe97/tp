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
    public static final String ERROR_INVALID_INPUT = "Oh no, I do not understand this command! Type 'help' for "
            + "a list of available commands";

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

    public void printBlankLine() {
        System.out.println();
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
        printSeparator();
        printBlankLine();

    }

    public void showAddConfirmationMessage(Task task) {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("Done! I've added \"" + task + "\" to your calendar"));
        printSeparator();
        printBlankLine();
    }

    private void printLogo() {
        System.out.println(colourTextCyan(LOGO));
    }

    public void showHelp() {
        printBlankLine();
        printSeparator();
        System.out.println(HELP_MESSAGE);
        printSeparator();
        printBlankLine();
    }

    /**
     * Returns read user command.
     */
    public String readCommand() {
        return conversation.nextLine();
    }

    public int readSingleIntInput() {
        return Integer.parseInt(readCommand());
    }

    public void showSelectTaskToEdit(String type) {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please select the " + type + " you want to edit:"));
    }

    public void showSelectParameterToEdit() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please select the parameter you want to edit:"));
    }

    public void showSelectTaskToDelete(String type) {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please select the " + type + " you want to delete:"));
    }

    public void showInputFormat(String type) {
        printBlankLine();
        printSeparator();
        switch (type) {

        case (PARAM_EVENT):
            System.out.println(colourTextCyan("Please input your new time in this format: "
                    + NEW_EVENT_TIME_INPUT_FORMAT + "\n"));
            break;

        case (PARAM_DEADLINE):
            System.out.println(colourTextCyan("Please input your new time in this format: "
                    + NEW_DEADLINE_TIME_INPUT_FORMAT + "\n"));
            break;

        case (PARAM_LESSON):
            System.out.println(colourTextCyan("Please input your new time in this format: "
                    + NEW_LESSON_TIME_INPUT_FORMAT + "\n"));
            break;

        default:
            break;
        }
    }

    public void showInputMessage(String type) {
        printBlankLine();
        printSeparator();
        switch (type) {

        case (PARAM_LESSON):
            System.out.println(colourTextCyan("Please input your new Module Code:"));
            break;

        case (PARAM_DEADLINE):
            System.out.println(colourTextCyan("Please input your new Deadline name:"));
            break;

        case (PARAM_EVENT):
            System.out.println(colourTextCyan("Please input your new Event name:"));
            break;

        default:
            break;
        }
    }

    public void showEditableParametersMessage(String type) {
        printBlankLine();
        printSeparator();
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
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("Your edit has been saved."));
        printSeparator();
        printBlankLine();
    }

    public void showDeleteConfirmationMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("The task you selected has been deleted."));
        printSeparator();
        printBlankLine();
    }

    public void showInvalidInputMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed(ERROR_INVALID_INPUT));
        printSeparator();
        printBlankLine();
    }

    public void showGoodbyeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan(MESSAGE_GOODBYE));
        printSeparator();
        printBlankLine();
    }

    public void showNoteTitleMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please put in a title:"));
    }

    public void showNoteDescriptionMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Alright! Please fill in your notes."));
    }

    public void showFileCreationError() {
        System.out.println(colourTextRed("Something went wrong... Save file creation failed..."));
    }

    public void showDataLoadingMessage() {
        System.out.println(colourTextGreen("Reading your save data. New saves will be created "
                + "if no saves are found."));
    }

    public void showNoDataToArchiveMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("You do not have any data available for archiving."));
        printSeparator();
        printBlankLine();
    }

    public void showFileArchiveError() {
        System.out.println(colourTextRed("There was an error archiving your data"));
        printSeparator();
        printBlankLine();
    }

    public void showArchiveStartMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("Starting archiving..."));
    }

    public void showArchiveEndMessage() {
        System.out.println(colourTextGreen("Archiving successful!"));
        printSeparator();
        printBlankLine();
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
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("The number you inputted is invalid!"));
        printSeparator();
        printBlankLine();
    }

    public void showNoTitleFoundMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("The title you inputted is not found..."));
        printSeparator();
        printBlankLine();
    }

    public void showNumberFormatMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("Oops! The input must be a number!"));
        printSeparator();
        printBlankLine();
    }

    public void showInvalidCastError() {
        System.out.println(colourTextRed("Something went wrong, mismatching task types..."));
    }

    public void showSaveDataMissingError() {
        System.out.println(colourTextRed("Encountered an error while reading from the save file "
                + "- Data missing/corrupted"));
    }

    public void showLocalDateTimeParseError() {
        System.out.println(colourTextRed("Encountered a problem reading the date and time of the task..."));
    }

    public void showUndeterminableTaskError() {
        System.out.println(colourTextRed("Something went wrong while determining the tasks..."));
    }

    public void showParseUnknownCommandMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("I'm sorry! I don't understand that command!"));
        printSeparator();
        printBlankLine();
    }

    public void showParseIncorrectCommandFormatMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("I'm sorry! Please double check the input of your format!"));
        System.out.println(colourTextGreen("Use the help command for formats of all the commands!"));
        printSeparator();
        printBlankLine();
    }

    public void showParseIncorrectDateTimeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("I'm sorry! Something's wrong with your date or time!"));
        System.out.println(colourTextRed("Please ensure your dates are in the format dd-mm-yy," +
                "and your times are in the format hh:mm!"));
        System.out.println(colourTextRed("Also ensure your dates and times are valid numbers!"));
        printSeparator();
        printBlankLine();
    }

        public void showEmptyDescriptionMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("Empty description! =O\n"));
        printSeparator();
        printBlankLine();
    }

    public void showNoMatchesMessage(String type) {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("Sorry! There is no " + type + " matching your query. Please "
                + "re-enter your command."));
        printSeparator();
        printBlankLine();
    }

    public void showNoteAddedMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("Ok! I've taken note of this note!"));
        printSeparator();
        printBlankLine();
    }

    public void showMultipleMatchesFoundMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Multiple matches found! Please select the one you are looking "
                + "for:\n"));
    }

    public void showSelectWhichNoteToViewMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please select the notes you want to view:\n"));
    }

    public void showFreeTimeMessage(int startHour, int endHour, int duration) {
        printBlankLine();
        printSeparator();
        if (duration > 0) {
            System.out.println(colourTextGreen("You have " + duration + " hours of free time between "
                    + startHour + ":00 and " + endHour + ":00!"));
            System.out.println(colourTextCyan("You can try scheduling something in this time!"));

        } else {
            System.out.println(colourTextRed("Unfortunately you have no free time today!"));
            System.out.println(colourTextGreen("You might want to relax a little!"));
        }
        printSeparator();
        printBlankLine();
    }

    public void showNothingScheduledMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("You have nothing on for today and tomorrow!"));
    }

    public void showAvailableSleepTimeMessage(int earliestSleepTime, int latestWakeTime) {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("You have nothing on from " + earliestSleepTime + ":00 today to "
                + latestWakeTime + ":00 tomorrow!"));
    }

    public void showSleepDurationMessage(int duration) {
        System.out.println(colourTextGreen("You can sleep for up to " + duration + " hours!"));
        printSeparator();
        printBlankLine();
    }

    public void showExcessSleepDurationMessage() {
        System.out.println(colourTextGreen("You can sleep for the recommended 8 hours or longer!"));
        printSeparator();
        printBlankLine();
    }

    public void showSelectWhichNoteToDeleteMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please select the notes you want to delete:\n"));
    }

    public void showConfirmDeleteMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Is this the note you want to delete? (Y/N)\n"));
    }

    public void showInvalidConfirmationMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Y for Yes and N for No\n"));
    }

    public void showNoteNotDeletedMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("OK! Note not deleted!"));
        printSeparator();
        printBlankLine();
    }

    public void showNoteDeletedMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("OK! Note deleted!"));
        printSeparator();
        printBlankLine();
    }

    public void showEditWhichPartMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Do you want to change the title or description? (T/D)\n"));
    }

    public void showNoteNotEditedMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("OK! Note not edited!"));
        printSeparator();
        printBlankLine();
    }
    
    public void showInvalidTitleDescriptionConfirmationMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("T for title and D for Description\n"));
    }

    public void showEditTitleMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the title you want to change to:\n"));
    }

    public void showEditDescriptionMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the description you want to change to:\n"));
    }

    public void showSelectWhichNoteToEditMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please select the notes you want to edit:\n"));
    }

    public void showEmptyNoteListMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("There's no Notes!"));
        printSeparator();
        printBlankLine();
    }

    public void showAddModuleCodeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the module code:\n"));
    }

    public void showAddDateMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the date:\n"));
    }

    public void showAddStartTimeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the start time:\n"));
    }

    public void showAddEndTimeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the end time:\n"));
    }

    public void showAddDescriptionMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the description:\n"));
    }

    public void showAddDateTimeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the Date Time:\n"));
    }

    public void showAddRecurrencesMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("Please input the number of times to repeat:\n"));
    }

    public void printMultipleNoteMatches(NoteList notes, String title) {
        printBlankLine();
        printSeparator();
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                System.out.println(i + 1 + ". " + notes.get(i).getTitle() + "\n");
            }
        }
        printSeparator();
        printBlankLine();
    }

    public void printAllNotes(NoteList notes) {
        printBlankLine();
        printSeparator();
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() + "\n");
        }
        printSeparator();
        printBlankLine();
    }

    public void showEnterUndoTypeMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextCyan("To undo a change in tasks, please enter:") + "task");
        System.out.println(colourTextCyan("To undo a change in notes, please enter:") + "note\n");
    }

    public void showInvalidUndoType() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("Invalid undo type, please try again!"));
        printSeparator();
        printBlankLine();
    }

    public void showInvalidUndoAction() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("Nothing to undo!"));
        printSeparator();
        printBlankLine();
    }

    public void showUndoTaskEditMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("This task has been reverted back to its previous version!"));
        printSeparator();
        printBlankLine();
    }

    public void showUndoTaskDeleteMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("This deleted task has been successfully restored!"));
        printSeparator();
        printBlankLine();
    }

    public void showOldTask(TaskHistory taskHistory) {
        printBlankLine();
        printSeparator();
        System.out.println(taskHistory.getLastTask().toString());
        printSeparator();
        printBlankLine();
    }

    public void showUndoNoteEditMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("This note has been reverted back to its previous version!"));
        printSeparator();
        printBlankLine();
    }

    public void showUndoNoteDeleteMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextGreen("This deleted note has been successfully restored!"));
        printSeparator();
        printBlankLine();
    }

    public void showOldNote(NoteHistory noteHistory) {
        printBlankLine();
        printSeparator();
        System.out.println(noteHistory.getLastNote());
        printSeparator();
        printBlankLine();
    }

    public void printEmptyParam(String param) {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("We detected that you did not fill in the field of " + param
                + ". Please fill it in:\n"));
    }

    public void showUndeterminableRecurrenceError() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("There was an error reading the task recurrence"));
        printSeparator();
        printBlankLine();
    }

    public void showRecurrencesNumberFormatError() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("The input for /repeats is not a valid number! Please try again:\n"));
    }

    public void printMatchingTask(int index, String task) {
        System.out.println(index + ". " + task);
    }

    public void showIndexOutOfBoundsMessage() {
        printBlankLine();
        printSeparator();
        System.out.println("The number you have input is out of bounds");
        printSeparator();
        printBlankLine();
    }

    public void showInvalidTimeLogicMessage() {
        printBlankLine();
        printSeparator();
        System.out.println(colourTextRed("Start time must be before end time"));
        printSeparator();
        printBlankLine();
    }
    
}
