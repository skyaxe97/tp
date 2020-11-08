package seedu.lifeasier.model.tasks;


import seedu.lifeasier.commands.ShowNotesCommand;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;


public class TaskList {
    public static final int INDEX_START = 0;
    public static final int INDEX_END = 1;

    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    protected static ArrayList<Task> taskList;
    private int displayIndexOfLastMatch = 0;

    HashMap<Integer,Integer> taskMap;

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

    public int getActualIndex(int i) {
        return taskMap.get(i);
    }

    /**
     * Add new event to taskList.
     *
     * @param description description of event.
     * @param start start date/time of event.
     * @param end end date/time of event.
     * @param recurrences number of times to repeat.
     */
    public Task addEvent(String description, LocalDateTime start, LocalDateTime end, int recurrences)
            throws TaskDuplicateException, TaskPastException {

        LocalDate today = LocalDate.now();
        if (start.toLocalDate().isBefore(today)) {
            throw new TaskPastException();
        }

        for (Task task : taskList) {
            if (task instanceof Event && (((Event) task).isDuplicate(description, start, end, recurrences))) {
                throw new TaskDuplicateException();
            }
        }

        assert start.isBefore(end) : "Start not before end!";

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
    public Task addLesson(String moduleCode, LocalDateTime start, LocalDateTime end, int recurrences)
            throws TaskDuplicateException, TaskPastException {

        LocalDate today = LocalDate.now();
        if (start.toLocalDate().isBefore(today)) {
            throw new TaskPastException();
        }

        for (Task task : taskList) {
            if (task instanceof Lesson && (((Lesson) task).isDuplicate(moduleCode, start, end, recurrences))) {
                throw new TaskDuplicateException();
            }
        }

        assert start.isBefore(end) : "Start not before end!";

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
    public Task addDeadline(String description, LocalDateTime by, int recurrences)
            throws TaskDuplicateException, TaskPastException {

        LocalDate today = LocalDate.now();
        if (by.toLocalDate().isBefore(today)) {
            throw new TaskPastException();
        }

        for (Task task : taskList) {
            if (task instanceof Deadline && (((Deadline) task).isDuplicate(description, by, recurrences))) {
                throw new TaskDuplicateException();
            }
        }

        Deadline deadline = new Deadline(description, by, recurrences);
        addTask(deadline);
        return deadline;
    }

    public void editTaskDescription(int displayIndex, Ui ui) {
        String newDescription;
        while (true) {
            newDescription = ui.readCommand();
            if (newDescription.equals("")) {
                ui.showEmptyNewDescriptionPrompt();
                continue;
            }
            break;
        }
        int actualIndex = taskMap.get(displayIndex);
        getTask(actualIndex).setDescription(newDescription);
        ui.showEditConfirmationMessage();
    }

    public void editModuleCode(int displayIndex, Ui ui, String moduleCode) {
        int actualIndex = taskMap.get(displayIndex);
        getTask(actualIndex).setDescription(moduleCode);
        ui.showEditConfirmationMessage();
    }

    public void editLessonTime(int displayIndex, Ui ui, LocalDateTime[] times) {
        int actualIndex = taskMap.get(displayIndex);
        getTask(actualIndex).setStart(times[INDEX_START]);
        getTask(actualIndex).setEnd(times[INDEX_END]);
        ui.showEditConfirmationMessage();
    }

    public void editEventTime(int displayIndex, Ui ui, LocalDateTime[] times) {
        int actualIndex = taskMap.get(displayIndex);
        getTask(actualIndex).setStart(times[INDEX_START]);
        getTask(actualIndex).setEnd(times[INDEX_END]);
        ui.showEditConfirmationMessage();
    }

    public void editDeadlineTime(int displayIndex, Ui ui, LocalDateTime[] times) {
        int actualIndex = taskMap.get(displayIndex);
        getTask(actualIndex).setStart(times[0]);
        ui.showEditConfirmationMessage();
    }

    public void deleteTask(int displayIndex, Ui ui) {
        try {
            int actualIndex = taskMap.get(displayIndex);
            taskList.remove(actualIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Index provided out of bounds");
            ui.showIndexOutOfBoundsError();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a valid number");
            ui.showNumberFormatError();
        }
    }

    public void printMatchingTasks(String type, String description, Ui ui) throws TaskNotFoundException {

        ui.showMatchingTasksPrompt(type);
        logger.log(Level.INFO, "Start of printing all matching " + type);
        taskMap = new HashMap<>();
        displayIndexOfLastMatch = 0;
        int firstDisplayIndex = 0;
        boolean noMatches = true;
        for (int i = 0; i < getTaskCount(); i++) {
            if (checkMatchingTasks(i, type, description)) {
                firstDisplayIndex++;
                String task = getTask(i).toString();
                ui.printMatchingTask(firstDisplayIndex, task);
                displayIndexOfLastMatch = firstDisplayIndex;

                taskMap.put(firstDisplayIndex, i);
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
        if (userInput > displayIndexOfLastMatch || userInput <= 0) {
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

            if (((task.isHappeningBefore(day)) && (task.getRecurrences() == 0)) || task.getRecurrences() < 0) {
                tasksToBeRemoved.add(task);

            } else if ((task.isHappeningBefore(day)) && (task.getRecurrences() > 0)) {
                task.moveAndUpdateRecurrences(day);
                if (task.getRecurrences() < 0) {
                    tasksToBeRemoved.add(task);
                }
            }
        }

        for (Task task : tasksToBeRemoved) {
            taskList.remove(task);
        }

    }

    public HashMap<Integer, Integer> createTaskMap() {
        return this.taskMap = new HashMap<>();
    }

    public void setMap(int displayIndex, int actualIndex) {
        taskMap.put(displayIndex, actualIndex);
    }

}
