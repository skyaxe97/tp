package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;

public class SleepTimeCommand extends Command {

    public static final int HOUR_EARLIEST = 0;
    public static final int HOUR_LATEST = 24;

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {

        ArrayList<Task> tasksFromToday = tasks.getTasksFromOneDay(LocalDate.now());
        ArrayList<Task> tasksFromTomorrow = tasks.getTasksFromOneDay(LocalDate.now().plusDays(1));

        int earliestSleepTime = getLatestBusyTime(tasksFromToday) + 1;
        int latestWakeTime = getEarliestBusyTime(tasksFromTomorrow) - 1;

        int duration = (HOUR_LATEST - earliestSleepTime) + latestWakeTime;

        ui.printSleepTimeMessage(earliestSleepTime, latestWakeTime, duration);

    }

    /**
     * Returns if there is something scheduled for a particular hour slot in a day.
     *
     * @param hour The time to be checked.
     * @param tasks An ArrayList containing the tasks scheduled for that day.
     * @return True if there is something scheduled.
     */
    private boolean isBusyTime(int hour, ArrayList<Task> tasks) {

        for (Task task : tasks) {
            if (!(task instanceof Deadline) && task.isWithinTimeSlot(hour)) {
                return true;
            }
        }
        return false;
    }

    private int getLatestBusyTime(ArrayList<Task> tasks) {

        assert (0 < HOUR_LATEST && HOUR_LATEST < 25) : "The latest hour checked must be between 0 and 24";

        for (int hour = HOUR_LATEST - 1; hour > 0; hour--) {
            if (isBusyTime(hour, tasks)) {
                return hour;
            }
        }
        return 0;
    }

    private int getEarliestBusyTime(ArrayList<Task> tasks) {

        assert (0 <= HOUR_EARLIEST && HOUR_EARLIEST < HOUR_LATEST) :
                "The earliest hour must be between 0 and the latest hour";

        for (int hour = HOUR_EARLIEST; hour < HOUR_LATEST; hour++) {
            if (isBusyTime(hour, tasks)) {
                return hour;
            }
        }
        return 24;
    }
}





