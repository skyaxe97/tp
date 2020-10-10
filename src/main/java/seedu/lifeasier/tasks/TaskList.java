package seedu.lifeasier.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    protected static ArrayList<Task> taskList;
    protected static int taskCount = 0;
    public TaskList() {
        taskList = new ArrayList<>();
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }

    public static int getTaskCount() {
        return taskCount;
    }

    public static void addTask (Task task) {
        taskList.add(task);
        taskCount++;
    }

    /**
     * Add new event to taskList
     * @param description description of event
     * @param start start date/time of event
     * @param end end date/time of event
     */
    public static void addEvent (String description, LocalDateTime start, LocalDateTime end) {
        Event event = new Event(description, start, end);
        addTask(event);
    }

    /**
     * Add new Lesson to taskList
     * @param moduleCode module code of lesson
     * @param start start date/time of lesson
     * @param end end date/time of lesson
     */
    public static void addLesson (String moduleCode, LocalDateTime start, LocalDateTime end) {
        Lesson lesson = new Lesson(moduleCode, start, end);
        addTask(lesson);
    }

    /**
     * Adds a new Deadline to taskList
     * @param description description of task
     * @param by deadline of task
     */
    public static void addDeadline (String description, LocalDateTime by) {
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
    }
}
