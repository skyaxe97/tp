package seedu.lifeasier.model.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The TaskHistory class represents the list of changes made to Task objects,
 * edits or deletions in particular.
 */
public class TaskHistory {
    private static Logger logger = Logger.getLogger(TaskHistory.class.getName());

    private int changeCount;
    private int editCount;
    private int deleteCount;

    private ArrayList<Task> taskHistory;

    private static final int DEFAULT_EDIT_NUMBER = -999999;

    public TaskHistory() {
        this.changeCount = 0;
        this.editCount = 0;
        this.deleteCount = 0;
        this.taskHistory = new ArrayList<>();
    }

    public Task getLastTask() {
        return taskHistory.get(changeCount - 1);
    }

    public int getChangeCount() {
        return changeCount;
    }

    public int getEditCount() {
        return editCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void incrementChangeCount() {
        this.changeCount++;
    }

    public void decrementChangeCount() {
        if (changeCount > 0) {
            this.changeCount--;
        }
    }

    public void pushOldCopy(Task oldCopyOfTask) {
        taskHistory.add(oldCopyOfTask);
        incrementChangeCount();
    }

    public void popLastTask() {
        int indexOfLastTask = changeCount - 1;

        int editNumOfLastTask = taskHistory.get(indexOfLastTask).getEditNumber();
        logger.log(Level.INFO, "Change editCount/deleteCount accordingly");
        if (!containsSameEditNumber(editNumOfLastTask)) {
            if (editNumOfLastTask > 0) {
                editCount--;
            } else {
                deleteCount++;
            }
        }

        logger.log(Level.INFO, "Popping the last task of taskHistory");
        taskHistory.remove(indexOfLastTask);
        decrementChangeCount();
    }

    public boolean containsSameEditNumber(int editNumOfLastTask) {
        return taskHistory.stream().anyMatch(t -> t.getEditNumber() == editNumOfLastTask);
    }

    /**
     * Returns a copy of the Task object before it is edited.
     *
     * @param tasks represents the TaskList object.
     * @param userListNumChoice the list number of the Task object the user wants to edit.
     *
     */
    public Task getCurrCopyOfTaskToEdit(TaskList tasks, int userListNumChoice) {
        Task task = tasks.getTask(tasks.getActualIndex(userListNumChoice));

        int taskEditNumber = task.getEditNumber();
        int editID;

        if (taskEditNumber == DEFAULT_EDIT_NUMBER) {
            editCount++;
            editID = getEditCount();
            task.setEditNumber(editID);
        } else {
            editID = taskEditNumber;
        }
        logger.log(Level.INFO, "Make a deep copy of Task with a specific editID");
        return copyTask(task, editID);
    }

    /**
     * Returns a copy of the Task object before it is deleted.
     *
     * @param tasks represents the TaskList object.
     * @param userListNumChoice the list number of the Task object the user wants to delete.
     *
     */
    public Task getCurrCopyOfTaskToDelete(TaskList tasks, int userListNumChoice) {
        Task task = tasks.getTask(tasks.getActualIndex(userListNumChoice));

        deletePrevCopiesOfThisTask(task);

        int deleteID = getDeleteCount() - 1;
        task.setEditNumber(deleteID);

        deleteCount--;

        logger.log(Level.INFO, "Make a deep copy of Task with a specific deleteID");
        return copyTask(task, deleteID);
    }

    /**
     * Removes existing copies with the same editNumber of a particular Task
     * that was saved in the TaskHistory.
     *
     * @param taskToDelete reference Task to be deleted.
     */
    private void deletePrevCopiesOfThisTask(Task taskToDelete) {
        int editNumber = taskToDelete.getEditNumber();
        Iterator<Task> iterator = taskHistory.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getEditNumber() == editNumber) {
                iterator.remove();
                decrementChangeCount();
            }
        }
    }

    public Task copyTask(Task task, int editID) {
        if (task instanceof Deadline) {
            return new Deadline(task, editID);
        } else if (task instanceof Event) {
            return new Event(task, editID);
        } else {
            return new Lesson(task, editID);
        }
    }

    public void printTaskHistory() {
        for (Task t : taskHistory) {
            System.out.println(t.toString() + " " + t.getEditNumber());
        }
    }
}
