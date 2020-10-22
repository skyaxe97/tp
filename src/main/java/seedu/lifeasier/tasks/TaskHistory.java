package seedu.lifeasier.tasks;

import seedu.lifeasier.ui.Ui;

import java.util.ArrayList;

public class TaskHistory {

    private int changeCount;
    private ArrayList<Task> taskHistory;
    private TaskList tasks;

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

    public void addOldCopy(Task oldCopyOfTask, Ui ui) {
        taskHistory.add(oldCopyOfTask);
        incrementChangeCount();
        System.out.println("RESULTSSSSSSS:");
        System.out.println(taskHistory.toString());
    }
}
