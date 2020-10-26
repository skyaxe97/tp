package seedu.lifeasier.tasks;


import seedu.lifeasier.commands.ShowNotesCommand;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;


public class TaskList {
    public static final int INDEX_START = 0;
    public static final int INDEX_END = 1;

    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    protected static ArrayList<Task> taskList;
    private int indexOfLastMatch;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskCount() {
        return taskList.size();
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public void setTask(int index, Task task) {
        taskList.set(index, task);
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Add new event to taskList.
     *
     * @param description description of event.
     * @param start start date/time of event.
     * @param end end date/time of event.
     * @param recurrences number of times to repeat.
     */
    public Task addEvent(String description, LocalDateTime start, LocalDateTime end, int recurrences) {
        Event event = new Event(description, start, end, recurrences);
        addTask(event);
        return event;
    }

    /**
     * Add new Lesson to taskList.
     *
     * @param moduleCode module code of lesson.
     * @param start start date/time of lesson.
     * @param end end date/time of lesson.
     * @param recurrences number of times to repeat.
     */
    public Task addLesson(String moduleCode, LocalDateTime start, LocalDateTime end, int recurrences) {
        Lesson lesson = new Lesson(moduleCode, start, end, recurrences);
        addTask(lesson);
        return lesson;
    }

    /**
     * Adds a new Deadline to taskList.
     *
     * @param description description of task.
     * @param by deadline of task.
     * @param recurrences number of times to repeat.
     */
    public Task addDeadline(String description, LocalDateTime by, int recurrences) {
        Deadline deadline = new Deadline(description, by, recurrences);
        addTask(deadline);
        return deadline;
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
            logger.log(Level.SEVERE, "Time input is not in the correct format");
            throw new ParserException();
        }
        getTask(index).setStart(times[INDEX_START]);
        getTask(index).setEnd(times[INDEX_END]);
        ui.showEditConfirmationMessage();
    }

    public void editEventTime(int index, Ui ui) throws ParserException {
        LocalDateTime[] times;
        Parser parser = new Parser();
        times = parser.parseNewTimeInput(ui, ui.readCommand(), 2);
        if (times[0] == null) {
            logger.log(Level.SEVERE, "Time input is not in the correct format");
            throw new ParserException();
        }
        getTask(index).setStart(times[INDEX_START]);
        getTask(index).setEnd(times[INDEX_END]);
        ui.showEditConfirmationMessage();
    }

    public void editDeadlineTime(int index, Ui ui) throws ParserException {
        LocalDateTime[] times;
        Parser parser = new Parser();
        times = parser.parseNewTimeInput(ui, ui.readCommand(), 1);
        if (times[0] == null) {
            logger.log(Level.SEVERE, "Time input is not in the correct format");
            throw new ParserException();
        }
        getTask(index).setStart(times[0]);
        ui.showEditConfirmationMessage();
    }

    public void deleteTask(int index, Ui ui) {
        try {
            taskList.remove(index);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Index provided out of bounds");
            ui.showInvalidNumberMessage();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a valid number");
            ui.showNumberFormatMessage();
        }
    }

    public void printMatchingTasks(String type, String description) throws TaskNotFoundException {

        logger.log(Level.INFO, "Start of printing all matching " + type);
        indexOfLastMatch = 0;
        boolean noMatches = true;
        for (int i = 0; i < getTaskCount(); i++) {
            if (checkMatchingTasks(i, type, description)) {
                System.out.println((i + 1) + ". " + getTask(i).toString());
                indexOfLastMatch = i;
                noMatches = false;
            }
        }
        if (noMatches) {
            logger.log(Level.INFO, "No matching tasks found");
            throw new TaskNotFoundException();
        }
        logger.log(Level.INFO, "Start of printing all matching " + type);
    }

    private boolean checkMatchingTasks(int index, String type, String description) {
        return (getTask(index).getType().equals(type)
                && getTask(index).getDescription().contains(description));
    }

    public void checkForIndexOutOfBounds(int userInput) {
        if (userInput > indexOfLastMatch || userInput < 0) {
            logger.log(Level.SEVERE, "Index provided out of bounds");
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Returns an ArrayList of Tasks from one specific day.
     *
     * @param day Day to get tasks from.
     * @return ArrayList of Tasks from one specific day.
     */
    public ArrayList<Task> getTasksFromOneDay(LocalDate day) {
        return (ArrayList<Task>) taskList.stream()
                .filter((t) -> t.getStart().toLocalDate().equals(day))
                .collect(toList());
    }

    public void sort() {
        taskList.sort(Comparator.comparing(Task::getStart));
    }

    /**
     * Cleans up the taskList.
     * Deletes tasks if they have no more recurrences and are in the past.
     * Updates tasks' dates if they have more recurrences and are in the past.
     *
     * @param day Day behind which tasks will be updated and deleted.
     */
    public void updateTasks(LocalDate day) {

        ArrayList<Task> tasksToBeRemoved = new ArrayList<>();

        for (Task task : taskList) {

            if ((task.isHappeningBefore(day)) && (task.getRecurrences() == 0)) {
                tasksToBeRemoved.add(task);

            } else if ((task.isHappeningBefore(day)) && (task.getRecurrences() > 0)) {
                task.moveAndUpdateRecurrences();
            }
        }

        for (Task task : tasksToBeRemoved) {
            taskList.remove(task);
        }

    }

}
