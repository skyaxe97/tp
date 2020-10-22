package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FreeTimeCommand extends Command {

    private static Logger logger = Logger.getLogger(FreeTimeCommand.class.getName());

    public static final int PARAM_START = 0;
    public static final int PARAM_END = 1;
    public static final int HOUR_EARLIEST = 7;
    public static final int HOUR_LATEST = 24;


    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {

        logger.log(Level.INFO, "Getting tasks from today...");
        ArrayList<Task> tasksFromToday = tasks.getTasksFromOneDay(LocalDate.now());

        logger.log(Level.INFO, "Getting longest block of free time...");
        int[] longestFreeTime = getLongestFreeTime(tasksFromToday);

        int startHour = longestFreeTime[PARAM_START];
        int endHour = longestFreeTime[PARAM_END];
        int duration = endHour - startHour;

        assert (startHour < endHour) : "The end cannot be before the start!";

        logger.log(Level.INFO, "Showing free time message...");
        ui.showFreeTimeMessage(startHour, endHour, duration);

    }

    /**
     * Returns true if there is nothing scheduled for a particular hour slot in a day.
     *
     * @param hour The time to be checked.
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return True if there is nothing scheduled.
     */
    boolean isFreeTime(int hour, ArrayList<Task> tasks) {

        for (Task task : tasks) {
            if (!(task instanceof Deadline) && task.isWithinTimeSlot(hour)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the longest block of free time in the day's schedule.
     *
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return An integer array where the first index is the start of the block, and the second index is the end.
     */
    int[] getLongestFreeTime(ArrayList<Task> tasks) {

        int[] longestFreeTime = new int[2];

        int tempStartOfFreeTime = HOUR_EARLIEST;
        int tempEndOfFreeTime = HOUR_LATEST;

        assert (0 < HOUR_LATEST && HOUR_LATEST < 25) : "The latest hour checked must be between 0 and 24";
        assert (0 <= HOUR_EARLIEST && HOUR_EARLIEST < HOUR_LATEST) :
                "The earliest hour must be between 0 and the latest hour";

        for (int hour = HOUR_EARLIEST; hour < HOUR_LATEST; hour++) {

            if (isFreeTime(hour, tasks)) {
                tempEndOfFreeTime = hour + 1;

            } else {
                tempStartOfFreeTime = hour + 1;
            }

            int durationOfTempFreeTimeBlock = tempEndOfFreeTime - tempStartOfFreeTime + 1;
            int durationOfLongestFreeTimeBlock = longestFreeTime[PARAM_END] - longestFreeTime[PARAM_START] + 1;

            if (durationOfTempFreeTimeBlock >= durationOfLongestFreeTimeBlock) {
                longestFreeTime[PARAM_START] = tempStartOfFreeTime;
                longestFreeTime[PARAM_END] = tempEndOfFreeTime;
            }
        }

        return longestFreeTime;
    }
}
