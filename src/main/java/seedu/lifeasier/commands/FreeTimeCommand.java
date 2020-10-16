package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.util.ArrayList;


public class FreeTimeCommand extends Command {
    public static final int PARAM_START = 0;
    public static final int PARAM_END = 1;
    public static final int HOUR_EARLIEST = 7;
    public static final int HOUR_LATEST = 24;


    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {

        ArrayList<Task> tasksFromToday = tasks.getTasksFromToday();
        int[] longestFreeTime = getLongestFreeTime(tasksFromToday);

        int startHour = longestFreeTime[PARAM_START];
        int endHour = longestFreeTime[PARAM_END];
        int duration = endHour - startHour;

        assert (startHour < endHour) : "The end cannot be before the start!";
        ui.printFreeTimeMessage(startHour, endHour, duration);


    }

    /**
     * Returns if there is nothing scheduled for a particular hour slot in a day.
     *
     * @param hour The time to be checked.
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return True if there is nothing scheduled.
     */
    private boolean isFreeTime(int hour, ArrayList<Task> tasks) {

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
    private int[] getLongestFreeTime(ArrayList<Task> tasks) {

        int[] longestFreeTime = new int[2];

        int startOfFreeTime = 24;
        int endOfFreeTime = 24;

        assert (0 < HOUR_LATEST && HOUR_LATEST < 25) : "The latest hour checked must be between 0 and 24";
        assert (0 <= HOUR_EARLIEST && HOUR_EARLIEST < HOUR_LATEST) :
                "The earliest hour must be between 0 and the latest hour";

        for (int hour = HOUR_EARLIEST; hour < HOUR_LATEST + 1; hour++) {

            if (isFreeTime(hour, tasks)) {
                endOfFreeTime = hour;

            } else {
                startOfFreeTime = hour + 1;
            }

            if ((endOfFreeTime - startOfFreeTime + 1)
                    >= (longestFreeTime[PARAM_END] - longestFreeTime[PARAM_START] + 1)) {

                longestFreeTime[PARAM_START] = startOfFreeTime;
                longestFreeTime[PARAM_END] = endOfFreeTime;
            }
        }

        return longestFreeTime;
    }
}
