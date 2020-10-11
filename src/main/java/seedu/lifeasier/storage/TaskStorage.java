package seedu.lifeasier.storage;

import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Event;
import seedu.lifeasier.tasks.Lesson;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * The TaskStorage class handles the reading and writing of the save file for tasks.
 */
public class TaskStorage {

    private static Logger logger = Logger.getLogger(TaskStorage.class.getName());
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private static final String SAVE_DELIMITER = "=-=";
    public static final String DEFAULT_DATA = "\n";

    private TaskList tasks;
    private String filePathTasks;
    private FileCommand fileCommand;
    private Ui ui;

    public TaskStorage() {
    }

    public TaskStorage(TaskList tasks, String filePathTasks) {
        this.tasks = tasks;
        this.filePathTasks = filePathTasks;
        this.fileCommand = new FileCommand();
        this.ui = new Ui();
    }

    protected void readTasksSave(String filePathTasks) {
        try {
            File saveFile = new File(filePathTasks);

            Scanner fileScanner = new Scanner(saveFile);
            createTaskList(fileScanner);

        } catch (IOException e) {
            ui.showFileReadError();
        }
    }

    private void createTaskList(Scanner fileScanner) {
        ArrayList<Task> taskList = tasks.getTaskList();

        try {
            while (fileScanner.hasNext()) {
                String taskInformation = fileScanner.nextLine();

                String[] taskComponents = taskInformation.split(SAVE_DELIMITER);
                String taskType = taskComponents[0];
                String taskDescription = taskComponents[2];
                Boolean taskStatus = fileCommand.convertToBoolean(taskComponents[1]);
                switch (taskType) {
                case "deadline":
                    rebuildDeadline(taskComponents, taskList, taskDescription, taskStatus);
                    break;
                case "event":
                    rebuildEvent(taskComponents, taskList, taskDescription, taskStatus);
                    break;
                case "lesson":
                    rebuildLesson(taskComponents, taskList, taskDescription, taskStatus);
                    break;
                default:
                    throw new StorageException();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showSaveDataMissingError();
        } catch (StorageException e) {
            ui.showUndeterminableTaskError();
        }
    }

    private void rebuildLesson(String[] taskComponents, ArrayList<Task> taskList, String description, Boolean status)
            throws ArrayIndexOutOfBoundsException {
        LocalDateTime lessonStartTime = fileCommand.convertToLocalDateTime(taskComponents[3]);
        LocalDateTime lessonEndTime = fileCommand.convertToLocalDateTime(taskComponents[4]);

        //Create new event in tasks
        taskList.add(new Lesson(description, lessonStartTime, lessonEndTime, status));
        tasks.increaseTaskCount();
    }

    private void rebuildEvent(String[] taskComponents, ArrayList<Task> taskList, String description, Boolean status)
            throws ArrayIndexOutOfBoundsException {
        LocalDateTime eventStartTime = fileCommand.convertToLocalDateTime(taskComponents[3]);
        LocalDateTime eventEndTime = fileCommand.convertToLocalDateTime(taskComponents[4]);

        //Create new event in tasks
        taskList.add(new Event(description, eventStartTime, eventEndTime, status));
        tasks.increaseTaskCount();
    }

    private void rebuildDeadline(String[] taskComponents, ArrayList<Task> taskList, String description, Boolean status)
            throws ArrayIndexOutOfBoundsException {
        LocalDateTime deadlineTimeInfo = fileCommand.convertToLocalDateTime(taskComponents[3]);

        //Create new deadline in tasks
        taskList.add(new Deadline(description, deadlineTimeInfo, status));
        tasks.increaseTaskCount();
    }

    /**
     * Writes information of TaskList onto the save file for storage whenever there is a change.
     */
    public void writeToTaskSaveFile() {
        try {
            FileWriter fileWriter = new FileWriter(filePathTasks, true);
            fileCommand.clearSaveFile(filePathTasks);

            String dataToSave;
            ArrayList<Task> taskList = tasks.getTaskList();
            //Append each tasks information into save file for tasks
            for (Task task : taskList) {
                String taskType = task.getType();
                switch (taskType) {
                case "deadline":
                    dataToSave = convertDeadlineToString(task, taskType);
                    break;
                case "event":
                    dataToSave = convertEventToString(task, taskType);
                    break;
                case "lesson":
                    dataToSave = convertLessonToString(task, taskType);
                    break;
                default:
                    dataToSave = DEFAULT_DATA;
                    fileWriter.write(dataToSave);
                    throw new StorageException();
                }
                fileWriter.write(dataToSave);
            }
            fileWriter.close();
        } catch (IOException e) {
            ui.showFileWriteError();
        } catch (ClassCastException e) {
            ui.showInvalidCastError();
        } catch (StorageException e) {
            ui.showUndeterminableTaskError();
        }
    }

    protected String convertLessonToString(Task task, String taskType) throws ClassCastException {
        Lesson lesson = (Lesson) task;
        return taskType + SAVE_DELIMITER + task.getStatus() + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER
                + lesson.getStart().format(DATE_TIME_FORMATTER) + SAVE_DELIMITER
                + lesson.getEnd().format(DATE_TIME_FORMATTER) + System.lineSeparator();
    }

    protected String convertEventToString(Task task, String taskType) throws ClassCastException {
        Event event = (Event) task;
        return taskType + SAVE_DELIMITER + task.getStatus() + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER
                + event.getStart().format(DATE_TIME_FORMATTER) + SAVE_DELIMITER
                + event.getEnd().format(DATE_TIME_FORMATTER) + System.lineSeparator();
    }

    protected String convertDeadlineToString(Task task, String taskType) throws ClassCastException {
        Deadline deadline = (Deadline) task;
        return taskType + SAVE_DELIMITER + task.getStatus() + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER
                + deadline.getBy().format(DATE_TIME_FORMATTER) + System.lineSeparator();
    }

}
