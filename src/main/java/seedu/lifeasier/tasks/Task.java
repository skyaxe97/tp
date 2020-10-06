package seedu.lifeasier.tasks;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected static int taskCounter = 0;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        taskCounter++;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbol
    }

    public void markAsDone() {
        this.isDone = true;
        taskCounter--;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
