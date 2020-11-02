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

    public static final String THICK_SEPARATOR = "===================================================================="
            + "===========================================";

    public static final String THIN_SEPARATOR  = "--------------------------------------------------------------------"
            + "-------------------------------------------";

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
            + "******************************************************************************************************\n"
            + "help --------------------------------------------------------------------- Displays available commands\n"
            + "addLesson /code MODULE_CODE /date DATE /time START /to END /repeats TIMES ----------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds a lesson\n"
            + "addEvent EVENT_NAME /date DATE /time START /to END /repeats TIMES ------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds an event\n"
            + "addDeadline DEADLINE_NAME /by DATETIME /repeats TIMES ----------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds a deadline\n"
            + "editLesson MODULE_CODE ------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits a lesson\n"
            + "editEvent EVENT_NAME --------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits an event\n"
            + "editDeadline DEADLINE_NAME ------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits a deadline\n"
            + "deleteTask /type TYPE /name NAME ------------------------------------------------------ Deletes a task\n"
            + "addNotes TITLE -------------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Adds a new note\n"
            + "showNotes TITLE --------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Shows selected note\n"
            + "archive ------------------------------------------------------------------ Archives all existing notes\n"
            + "editNotes TITLE ------------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Edits a selected note\n"
            + "deleteNotes TITLE --------------------------------------------------------- "
            + ANSI_CYAN + "[P]" + ANSI_RESET + "Deletes a selected note\n"
            + "undo TYPE ------------------------------------------------- Undoes the last edit/deleted task or notes\n"
            + "display [WEEK] ---------------------------------------------- Displays either weekly or daily schedule\n"
            + "freeTime --------------------------------------------- Tells you your longest block of free time today\n"
            + "sleepTime -------------------------------------------------- Tells you how much time you have to sleep\n"
            + "exit -------------------------------------------------------------------- Closes the LifEasier program\n"
            + "******************************************************************************************************\n"
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

    public void printThickSeparator() {
        System.out.println(THICK_SEPARATOR);
    }

    public void printThinSeparator() {
        System.out.println(THIN_SEPARATOR);
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
        printThickSeparator();
        printThickSeparator();
        printLogo();
        printThickSeparator();
        printThickSeparator();
    }

    public void showGreetingMessage() {
        System.out.println(colourTextCyan(MESSAGE_GREETING));
        System.out.println(colourTextGreen(MESSAGE_HELP_COMMAND));
        printThickSeparator();
        printBlankLine();

    }

    public void showAddConfirmationMessage(Task task) {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("Done! I've added"));
        System.out.println(colourTextGreen("\"" + task + "\" "));
        System.out.println(colourTextGreen("to your schedule!"));
        printThickSeparator();
        printBlankLine();
    }

    private void printLogo() {
        System.out.println(colourTextCyan(LOGO));
    }

    public void showHelpMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(HELP_MESSAGE);
        printThickSeparator();
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

    public void showSelectTaskToEditPrompt(String type) {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please select the " + type + " you want to edit:"));
    }

    public void showSelectParameterToEditPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please select the parameter you want to edit:"));
    }

    public void showSelectTaskToDeletePrompt(String type) {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please select the " + type + " you want to delete:"));
    }

    public void showInputFormatPrompt(String type) {
        printBlankLine();
        printThinSeparator();
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

    public void showInputPrompt(String type) {
        printBlankLine();
        printThinSeparator();
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
        printThickSeparator();
        System.out.println(colourTextGreen("Your edit has been saved."));
        printThickSeparator();
        printBlankLine();
    }

    public void showDeleteConfirmationMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("The task you selected has been deleted."));
        printThickSeparator();
        printBlankLine();
    }

    public void showInvalidInputMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed(ERROR_INVALID_INPUT));
        printThickSeparator();
        printBlankLine();
    }

    public void showGoodbyeMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextCyan(MESSAGE_GOODBYE));
        printThickSeparator();
        printBlankLine();
    }

    public void showNoteTitlePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please put in a title:"));
    }

    public void showNoteDescriptionPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Alright! Please fill in your notes:"));
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
        printThickSeparator();
        System.out.println(colourTextRed("You do not have any data available for archiving."));
        printThickSeparator();
        printBlankLine();
    }

    public void showFileArchiveError() {
        System.out.println(colourTextRed("There was an error archiving your data"));
        printThickSeparator();
        printBlankLine();
    }

    public void showArchiveStartMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("Starting archiving..."));
    }

    public void showArchiveEndMessage() {
        System.out.println(colourTextGreen("Archiving successful!"));
        printThickSeparator();
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

    public void showInvalidNumberError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("The number you inputted is invalid!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showNoTitleFoundError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("The title you inputted is not found..."));
        printThickSeparator();
        printBlankLine();
    }

    public void showNumberFormatError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("Oops! The input must be a number!"));
        printThickSeparator();
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

    public void showParseUnknownCommandError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("I'm sorry! I don't understand that command!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showParseIncorrectCommandFormatError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("I'm sorry! Please double check the input of your format!"));
        System.out.println(colourTextRed("Use the help command for formats of all the commands!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showParseIncorrectDateTimeError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("I'm sorry! Something's wrong with your date or time!"));
        System.out.println(colourTextRed("Please ensure your dates are in the format dd-mm-yy,"
                + "and your times are in the format hh:mm!"));
        System.out.println(colourTextRed("Also ensure your dates and times are valid numbers!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showEmptyDescriptionError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("Empty description! =O"));
        printThickSeparator();
        printBlankLine();
    }

    public void showNoMatchesError(String type) {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("Sorry! There is no " + type + " matching your query. Please "
                + "re-enter your command."));
        printThickSeparator();
        printBlankLine();
    }

    public void showNoteAddedMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("Ok! I've taken note of this note!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showMultipleMatchesFoundPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Multiple matches found! Please select the one you are looking "
                + "for:"));
    }

    public void showSelectWhichNoteToViewPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please select the notes you want to view:"));
    }

    public void showFreeTimeMessage(int startHour, int endHour, int duration) {
        printBlankLine();
        printThickSeparator();
        if (duration > 0) {
            System.out.println(colourTextGreen("You have " + duration + " hours of free time between "
                    + startHour + ":00 and " + endHour + ":00!"));
            System.out.println(colourTextGreen("You can try scheduling something in this time!"));

        } else {
            System.out.println(colourTextRed("Unfortunately you have no free time today!"));
            System.out.println(colourTextRed("You might want to relax a little!"));
        }
        printThickSeparator();
        printBlankLine();
    }

    public void showNothingScheduledTodayTomorrowMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("You have nothing on for today and tomorrow!"));
    }

    public void showAvailableSleepTimeMessage(int earliestSleepTime, int latestWakeTime) {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("You have nothing on from " + earliestSleepTime + ":00 today to "
                + latestWakeTime + ":00 tomorrow!"));
    }

    public void showSleepDurationMessage(int duration) {
        System.out.println(colourTextGreen("You can sleep for up to " + duration + " hours!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showExcessSleepDurationMessage() {
        System.out.println(colourTextGreen("You can sleep for the recommended 8 hours or longer!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showSelectWhichNoteToDeletePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please select the notes you want to delete:"));
    }

    public void showConfirmDeletePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Is this the note you want to delete? (Y/N)"));
    }

    public void showInvalidConfirmationPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Y for Yes and N for No:"));
    }

    public void showNoteNotDeletedMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("OK! Note not deleted!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showNoteDeletedMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("OK! Note deleted!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showEditWhichPartMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextCyan("Do you want to change the title or description? (Enter T/D)"));
    }

    public void showNoteNotEditedMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("OK! Note not edited!"));
        printThickSeparator();
        printBlankLine();
    }
    
    public void showInvalidTitleDescriptionConfirmationPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("T for title and D for Description:"));
    }

    public void showEditTitlePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the title you want to change to:"));
    }

    public void showEditDescriptionPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the description you want to change to:"));
    }

    public void showSelectWhichNoteToEditPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please select the notes you want to edit:"));
    }

    public void showEmptyNoteListMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("There's no Notes!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showAddModuleCodePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the module code:"));
    }

    public void showAddDatePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the date:"));
    }

    public void showAddStartTimePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the start time:"));
    }

    public void showAddEndTimePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the end time:"));
    }

    public void showAddDescriptionPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the description:"));
    }

    public void showAddDateTimePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the Date Time:"));
    }

    public void showAddRecurrencesPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Please input the number of times to repeat:"));
    }

    public void showMultipleNoteMatchesMessage(NoteList notes, String title) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                System.out.println(i + 1 + ". " + notes.get(i).getTitle() + "\n");
            }
        }
    }

    public void showAllNotesMessage(NoteList notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() + "\n");
        }
    }

    public void showEnterUndoTypePrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("To undo a change in tasks, please enter: ") + "task");
        System.out.println(colourTextCyan("To undo a change in notes, please enter: ") + "note");
    }

    public void showInvalidUndoTypeError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("Invalid undo type, please try again!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showInvalidUndoActionError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("Nothing to undo!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showUndoTaskEditMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("This task has been reverted back to its previous version!"));
    }

    public void showUndoTaskDeleteMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("This deleted task has been successfully restored!"));
    }

    public void showOldTaskMessage(TaskHistory taskHistory) {
        System.out.println(taskHistory.getLastTask().toString());
        printThickSeparator();
        printBlankLine();
    }

    public void showUndoNoteEditMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("This note has been reverted back to its previous version!"));
    }

    public void showUndoNoteDeleteMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("This deleted note has been successfully restored!"));
    }

    public void showOldNoteMessage(NoteHistory noteHistory) {
        System.out.println(noteHistory.getLastNote());
        printThickSeparator();
        printBlankLine();
    }

    public void showEmptyParamPrompt(String param) {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("We detected that you did not fill in the field of " + param
                + ". Please fill it in:"));
    }

    public void showIndeterminableRecurrenceError() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("There was an error reading the task recurrence"));
        printThickSeparator();
        printBlankLine();
    }

    public void showRecurrencesNumberFormatPrompt() {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("The input for /repeats is not a valid number! Please try again:"));
    }

    public void printMatchingTask(int index, String task) {
        System.out.println(index + ". " + task);
    }

    public void showIndexOutOfBoundsMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println("The number you have input is out of bounds!");
        printThickSeparator();
        printBlankLine();
    }

    public void showInvalidTimeLogicMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextRed("Start time must be before end time!"));
        printThickSeparator();
        printBlankLine();
    }

    public void showNothingScheduledTodayMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println("You have nothing on for today!");
        printThickSeparator();
        printBlankLine();
    }

    public void showMatchingTasksPrompt(String type) {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Here are all your matching " + type + "s:"));
    }

    public void showCurrentTitlePrompt(String currentTitle) {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Current Title: "));
        System.out.println(currentTitle);
    }

    public void showTitleChangedMessage(String title) {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("OK! Your title is now: \"" + title + "\""));
        printThickSeparator();
        printBlankLine();
    }

    public void showCurrentDescriptionPrompt(String currentDescription) {
        printBlankLine();
        printThinSeparator();
        System.out.println(colourTextCyan("Current Description: "));
        System.out.println(currentDescription);
    }

    public void showDescriptionChangedMessage(String description) {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("OK! Your description is now: \"" + description + "\""));
        printThickSeparator();
        printBlankLine();
    }

    public void showDayScheduleMessage() {
        printBlankLine();
        printThickSeparator();
        System.out.println("Here is your schedule for today:");
    }

    public void showNotesMessage(String note) {
        printBlankLine();
        printThickSeparator();
        System.out.println(colourTextGreen("Here is your note:"));
        System.out.println(note);
        printThickSeparator();
        printBlankLine();
    }

    public void showNotesPrompt(String note) {
        printBlankLine();
        printThinSeparator();
        System.out.println(note);
    }

}
