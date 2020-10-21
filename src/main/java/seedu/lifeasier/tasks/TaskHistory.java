package seedu.lifeasier.tasks;

import java.util.ArrayList;

public class TaskHistory {

    private static int changeCount;
    private ArrayList<Task> taskHistory;
    private TaskList tasks;

    public TaskHistory(TaskList tasks) {
        this.changeCount = 0;
        this.taskHistory = new ArrayList<>();
        this.tasks = tasks;
    }
}
