package seedu.lifeasier.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskList {
    protected ArrayList<Task> taskList;
    protected int taskCount;

    public TaskList() {
        taskList = new ArrayList<>();
        taskCount = 0;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public void addTask(Task task) {
        taskList.add(task);
        taskCount++;
    }

    public void increaseTaskCount() {
        taskCount++;
    }

    public void displayTaskList() {
        System.out.println("This is what is in the taskList:");
        for (Task task : taskList) {
            System.out.println(task.toString());
        }
    }

    /**
     * Add new event to taskList.
     * @param description description of event.
     * @param start start date/time of event.
     * @param end end date/time of event.
     */
    public void addEvent(String description, LocalDateTime start, LocalDateTime end) {
        Event event = new Event(description, start, end);
        addTask(event);
    }

    /**
     * Add new Lesson to taskList.
     * @param moduleCode module code of lesson.
     * @param start start date/time of lesson.
     * @param end end date/time of lesson.
     */
    public void addLesson(String moduleCode, LocalDateTime start, LocalDateTime end) {
        Lesson lesson = new Lesson(moduleCode, start, end);
        addTask(lesson);
    }

    /**
     * Adds a new Deadline to taskList.
     * @param description description of task.
     * @param by deadline of task.
     */
    public void addDeadline(String description, LocalDateTime by) {
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
    }

    public void getTasksFromToday() {
        ArrayList<Task> taskListFromToday = (ArrayList<Task>) taskList.stream()
                .filter((t) -> t.getStart().toLocalDate().equals(LocalDate.now()))
                .collect(toList());

        for (Task task : taskListFromToday) {
            System.out.println(task.toString());
        }
    }

    public void sort() {
        taskList.sort(Comparator.comparing(Task::getStart));
    }
}