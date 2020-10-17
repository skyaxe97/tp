package seedu.lifeasier.tasks;

import seedu.lifeasier.commands.ShowNotesCommand;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TaskList {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    protected static ArrayList<Task> taskList;
    protected static int taskCount;
    private int indexOfLastMatch;

    public TaskList() {
        taskList = new ArrayList<>();
        taskCount = 0;
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public static void addTask(Task task) {
        taskList.add(task);
        taskCount++;
    }

    public void increaseTaskCount() {
        taskCount++;
    }

    public void debugDisplayTaskList() {
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
    public static void addEvent(String description, LocalDateTime start, LocalDateTime end) {
        Event event = new Event(description, start, end);
        addTask(event);
    }

    /**
     * Add new Lesson to taskList.
     * @param moduleCode module code of lesson.
     * @param start start date/time of lesson.
     * @param end end date/time of lesson.
     */
    public static void addLesson(String moduleCode, LocalDateTime start, LocalDateTime end) {
        Lesson lesson = new Lesson(moduleCode, start, end);
        addTask(lesson);
    }

    /**
     * Adds a new Deadline to taskList.
     * @param description description of task.
     * @param by deadline of task.
     */
    public static void addDeadline(String description, LocalDateTime by) {
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
    }

    public void editTaskDescription(int index, Ui ui) {
        String newDescription = ui.readCommand();
        getTask(index).setDescription(newDescription);
        ui.showEditConfirmationMessage();
    }

    public void editLessonTime(int index, Ui ui) throws ParserException {
        Parser parser = new Parser();
        LocalDateTime[] times = parser.parseNewTimeInput(ui, ui.readCommand(), 2);
        if (times[0] == null) {
            throw new ParserException();
        }
        getTask(index).setStart(times[0]);
        getTask(index).setEnd(times[1]);
        ui.showEditConfirmationMessage();
    }

    public void editEventTime(int index, Ui ui) throws ParserException {
        LocalDateTime[] times;
        Parser parser = new Parser();
        times = parser.parseNewTimeInput(ui, ui.readCommand(), 2);
        if (times[0] == null) {
            throw new ParserException();
        }
        getTask(index).setStart(times[0]);
        getTask(index).setEnd(times[1]);
        ui.showEditConfirmationMessage();
    }

    public void editDeadlineTime(int index, Ui ui) throws ParserException {
        LocalDateTime[] times;
        Parser parser = new Parser();
        times = parser.parseNewTimeInput(ui, ui.readCommand(), 1);
        if (times[0] == null) {
            throw new ParserException();
        }
        getTask(index).setStart(times[0]);
        ui.showEditConfirmationMessage();
    }

    public void deleteTask(int index, Ui ui) {
        try {
            taskList.remove(index);
            taskCount--;
        } catch (IndexOutOfBoundsException e) {
            ui.showInvalidNumberMessage();
        } catch (NumberFormatException e) {
            ui.showNumberFormatMessage();
        }
    }

    public void printMatchingTasks(Ui ui, String type, String description) throws TaskNotFoundException {

        logger.log(Level.INFO, "Start of printing all matching " + type);
        indexOfLastMatch = 0;
        boolean noMatches = true;
        for (int i = 0; i < getTaskCount(); i++) {
            if (getTask(i).getType().equals(type)
                    && getTask(i).getDescription().contains(description)) {
                System.out.println((i + 1) + ". " + getTask(i).toString());
                indexOfLastMatch = i;
                noMatches = false;
            }
        }
        if (noMatches) {
            throw new TaskNotFoundException();
        }
        logger.log(Level.INFO, "Start of printing all matching " + type);
    }

    public void checkForIndexOutOfBounds(int userInput) {
        if (userInput > indexOfLastMatch || userInput < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void sort() {
        taskList.sort(Comparator.comparing(Task::getStart));
    }

}