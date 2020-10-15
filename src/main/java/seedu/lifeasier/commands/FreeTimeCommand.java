package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.util.ArrayList;


public class FreeTimeCommand extends Command {

    public static final int HOURS_IN_A_DAY = 24;
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
        ui.printFreeTimeMessage(startHour, endHour, duration);


    }

    private boolean isFreeTime(int hour, ArrayList<Task> tasks) {

        for (Task task : tasks) {
            if (!(task instanceof Deadline) && task.isWithinTimeSlot(hour)) {
                return false;
            }
        }
        return true;
    }

    private int[] getLongestFreeTime(ArrayList<Task> tasks) {

        int[] longestFreeTime = new int[2];

        int startOfFreeTime = 24;
        int endOfFreeTime = 24;

        for (int hour = HOUR_EARLIEST; hour < HOUR_LATEST + 1; hour++) {

            if (isFreeTime(hour, tasks)) {
                endOfFreeTime = hour;

            } else {
                startOfFreeTime = hour + 1;
            }

            if ((endOfFreeTime - startOfFreeTime + 1) >=
                    (longestFreeTime[PARAM_END] - longestFreeTime[PARAM_START] + 1)) {

                longestFreeTime[PARAM_START] = startOfFreeTime;
                longestFreeTime[PARAM_END] = endOfFreeTime;
            }
        }

        return longestFreeTime;
    }
}
