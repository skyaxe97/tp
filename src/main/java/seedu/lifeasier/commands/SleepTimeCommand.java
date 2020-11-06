package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.Deadline;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SleepTimeCommand extends Command {

    private static Logger logger = Logger.getLogger(SleepTimeCommand.class.getName());

    public static final int HOUR_EARLIEST = 0;
    public static final int HOUR_LATEST = 24;
    public static final int RECOMMENDED_SLEEP_DURATION = 9;


    /**
     * Executes the sleep time command and displays to the user how long they can sleep.
     *
     * @param ui Ui object to display messages to the user.
     * @param notes NoteList containing user's notes.
     * @param tasks TaskList containing user's tasks.
     * @param storage Storage object to save tasks and notes to memory.
     * @param parser Parser object to parse user's inputs.
     * @param noteHistory NoteHistory object to store history of edited and deleted notes.
     * @param taskHistory TaskHistory object to store history of edited and deleted tasks.
     */
    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {

        logger.log(Level.INFO, "Getting tasks from today...");
        ArrayList<Task> tasksFromToday = tasks.getTasksFromOneDay(LocalDate.now());

        logger.log(Level.INFO, "Getting tasks from tomorrow...");
        ArrayList<Task> tasksFromTomorrow = tasks.getTasksFromOneDay(LocalDate.now().plusDays(1));

        logger.log(Level.INFO, "Getting earliest sleep time...");
        int earliestSleepTime = getLatestBusyTime(tasksFromToday) + 1;

        logger.log(Level.INFO, "Getting latest wake time...");
        int latestWakeTime = getEarliestBusyTime(tasksFromTomorrow) - 1;

        int duration = (HOUR_LATEST - earliestSleepTime) + latestWakeTime;

        logger.log(Level.INFO, "Showing sleep time message...");

        if (earliestSleepTime == HOUR_EARLIEST && latestWakeTime == HOUR_LATEST) {
            ui.showNothingScheduledMessage("today and tomorrow");
        } else {
            ui.showAvailableSleepTimeMessage(earliestSleepTime, latestWakeTime);
        }

        if (duration < RECOMMENDED_SLEEP_DURATION) {
            ui.showSleepDurationMessage(duration);
        } else {
            ui.showExcessSleepDurationMessage();
        }
    }

    /**
     * Returns true if there is something scheduled for a particular hour slot in a day.
     *
     * @param hour The time to be checked.
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return True if there is something scheduled.
     */
    boolean isBusyTime(int hour, ArrayList<Task> tasks) {

        for (Task task : tasks) {
            if (!(task instanceof Deadline) && task.isWithinTimeSlot(hour)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the latest hour slot in which there is something scheduled in a day.
     *
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return Integer representing the latest hour slot in which there is something scheduled for the day.
     */
    int getLatestBusyTime(ArrayList<Task> tasks) {

        assert (0 < HOUR_LATEST && HOUR_LATEST < 25) : "The latest hour checked must be between 0 and 24";

        for (int hour = HOUR_LATEST - 1; hour > 0; hour--) {
            if (isBusyTime(hour, tasks)) {
                return hour;
            }
        }
        return -1;
    }

    /**
     * Returns the earliest hour slot in which there is something scheduled in a day.
     *
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return Integer representing the earliest hour slot in which there is something scheduled for the day.
     */
    int getEarliestBusyTime(ArrayList<Task> tasks) {

        assert (0 <= HOUR_EARLIEST && HOUR_EARLIEST < HOUR_LATEST) :
                "The earliest hour must be between 0 and the latest hour";

        for (int hour = HOUR_EARLIEST; hour < HOUR_LATEST; hour++) {
            if (isBusyTime(hour, tasks)) {
                return hour;
            }
        }
        return 25;
    }
}





