package seedu.lifeasier.tasks;

import seedu.lifeasier.ui.Ui;

import java.util.ArrayList;

public class TaskHistory {

    private int changeCount;
    private ArrayList<Task> taskHistory;
    private TaskList tasks;

    private static final int DEFAULT_EDIT_NUMBER = -999999;

    public TaskHistory(TaskList tasks) {
        this.changeCount = 0;
        this.taskHistory = new ArrayList<>();
        this.tasks = tasks;
    }

    public Task getLastTask() {
        return taskHistory.get(changeCount - 1);
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void incrementChangeCount() {
        this.changeCount++;
    }

    public void decrementChangeCount() {
        this.changeCount--;
    }

    public void pushOldCopy(Task oldCopyOfTask, Ui ui) {
        taskHistory.add(oldCopyOfTask);
        incrementChangeCount();
    }

    public void popLastTask() {
        int indexOfLastTask = changeCount - 1;
        taskHistory.remove(indexOfLastTask);
        decrementChangeCount();
    }

    public Task getCurrCopyOfTask(TaskList tasks, int userIndexChoice) {
        Task task = tasks.getTask(userIndexChoice);

        int taskEditNumber = task.getEditNumber();
        int editID;

        if (taskEditNumber == DEFAULT_EDIT_NUMBER) {
            editID = getChangeCount() + 1;
            tasks.getTask(userIndexChoice).setEditNumber(editID);
        } else {
            editID = taskEditNumber;
        }

        if (task instanceof Deadline) {
            return new Deadline(tasks.getTask(userIndexChoice), editID);
        } else if (task instanceof Event) {
            return new Event(tasks.getTask(userIndexChoice), editID);
        } else {
            return new Lesson(tasks.getTask(userIndexChoice), editID);
        }
    }
}
