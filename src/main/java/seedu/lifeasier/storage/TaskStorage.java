package seedu.lifeasier.storage;

import seedu.lifeasier.tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The TaskStorage class handles the reading and writing of the save file for tasks.
 */
public class TaskStorage {

    private static final String SAVE_DELIMITER = "=-=";
    public static final String DEFAULT_DATA = "\n";

    private TaskList tasks;
    private String filePathTasks;
    private FileCommand fileCommand;

    public TaskStorage(TaskList tasks, String filePathTasks) {
        this.tasks = tasks;
        this.filePathTasks = filePathTasks;
        this.fileCommand = new FileCommand();
    }

    protected void readTasksSave(String filePathTasks) {
        try {
            File saveFile = new File(filePathTasks);

            Scanner fileScanner = new Scanner(saveFile);
            createTaskList(fileScanner);

            System.out.println("Tasks have been successfully loaded!");
        } catch (IOException e) {
            System.out.println("Something went wrong, unable to read from tasks save file...");
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
                    System.out.println("Something went wrong while determining the tasks...");
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Encountered an error while reading from the save file - Data missing");
        }
    }

    private void rebuildLesson(String[] taskComponents, ArrayList<Task> taskList, String description, Boolean status)
            throws ArrayIndexOutOfBoundsException {
        LocalDateTime lessonStartTime = fileCommand.convertToLocalDateTime(taskComponents[3]);
        LocalDateTime lessonEndTime = fileCommand.convertToLocalDateTime(taskComponents[4]);

        //Create new event in tasks
        taskList.add(new Lesson(description, lessonStartTime, lessonEndTime, status));
    }

    private void rebuildEvent(String[] taskComponents, ArrayList<Task> taskList, String description, Boolean status)
            throws ArrayIndexOutOfBoundsException {
        LocalDateTime eventStartTime = fileCommand.convertToLocalDateTime(taskComponents[3]);
        LocalDateTime eventEndTime = fileCommand.convertToLocalDateTime(taskComponents[4]);

        //Create new event in tasks
        taskList.add(new Event(description, eventStartTime, eventEndTime, status));
    }

    private void rebuildDeadline(String[] taskComponents, ArrayList<Task> taskList, String description, Boolean status)
            throws ArrayIndexOutOfBoundsException {
        LocalDateTime deadlineTimeInfo = fileCommand.convertToLocalDateTime(taskComponents[3]);

        //Create new deadline in tasks
        taskList.add(new Deadline(description, deadlineTimeInfo, status));
    }

    /**
     * Writes information of TaskList onto the save file for storage whenever there is a change.
     *
     * @throws IOException When the file cannot be found or is corrupted.
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
                    dataToSave = generateDeadlineSave(task, taskType);
                    break;
                case "event":
                    dataToSave = generateEventSave(task, taskType);
                    break;
                case "lesson":
                    dataToSave = generateLessonSave(task, taskType);
                    break;
                default:
                    System.out.println("Something went wrong while determining the tasks...");
                    dataToSave = DEFAULT_DATA;
                    break;
                }
                fileWriter.write(dataToSave);
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while writing to the save file...");
        }
    }

    private String generateLessonSave(Task task, String taskType) {
        Lesson lesson = (Lesson) task;
        return taskType + SAVE_DELIMITER + task.getStatus() + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER
                + lesson.getStart().toString() + SAVE_DELIMITER + lesson.getEnd().toString() + System.lineSeparator();
    }

    private String generateEventSave(Task task, String taskType) {
        Event event = (Event) task;
        return taskType + SAVE_DELIMITER + task.getStatus() + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER
                + event.getStart().toString() + SAVE_DELIMITER + event.getEnd().toString() + System.lineSeparator();
    }

    private String generateDeadlineSave(Task task, String taskType) {
        Deadline deadline = (Deadline) task;
        return taskType + SAVE_DELIMITER + task.getStatus() + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER
                + deadline.getBy().toString() + System.lineSeparator();
    }

}
