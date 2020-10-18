package seedu.lifeasier.storage;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.Deadline;
import seedu.lifeasier.tasks.Event;
import seedu.lifeasier.tasks.Lesson;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskStorageTest {

    public static final String SAVE_DELIMITER = "=-=";
    public static final String TEST_FILEPATH = "testSave.txt";
    public static final String EXPECTED_LESSON = "lesson" + SAVE_DELIMITER + "false" + SAVE_DELIMITER + "CS2113T"
            + SAVE_DELIMITER + "09-04-21 18:00" + SAVE_DELIMITER + "09-04-21 18:00" + System.lineSeparator();
    public static final String EXPECTED_EVENT = "event" + SAVE_DELIMITER + "false" + SAVE_DELIMITER + "Concert"
            + SAVE_DELIMITER + "09-04-21 18:00" + SAVE_DELIMITER + "09-04-21 18:00" + System.lineSeparator();
    public static final String EXPECTED_DEADLINE = "deadline" + SAVE_DELIMITER + "false" + SAVE_DELIMITER
            + "Return Books" + SAVE_DELIMITER + "09-04-21 18:00" + System.lineSeparator();

    private TaskStorage taskStorage;
    private FileCommand fileCommand;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskList tasks;

    public TaskStorageTest() {
        this.tasks = new TaskList();
        this.taskStorage = new TaskStorage(tasks, TEST_FILEPATH);
        this.fileCommand = new FileCommand();
        startTime = fileCommand.convertToLocalDateTime("09-04-21T18:00");
        endTime = fileCommand.convertToLocalDateTime("09-04-21T18:00");
    }

    @Test
    void readTasksSave_saveFileTasks_tasksRebuilt() {
        try {
            File testSaveFile = new File(TEST_FILEPATH);
            if (!testSaveFile.exists()) {
                if (testSaveFile.createNewFile()) {
                    System.out.println("Save file for testing created");
                }
            }
            fileCommand.clearSaveFile(TEST_FILEPATH);

            FileWriter fileWriter = new FileWriter(TEST_FILEPATH, true);
            fileWriter.write("deadline=-=false=-=Go Home=-=16-10-20 23:59");
            fileWriter.close();

            tasks.getTaskList().clear();
            taskStorage.readTasksSave();

            Task testTask = tasks.getTask(0);
            Boolean isCorrectType = testTask.getType().equals("deadline");
            Boolean isCorrectDescription = testTask.getDescription().equals("Go Home");
            Boolean isCorrectSize = tasks.getTaskList().size() == 1;

            assertTrue(isCorrectType && isCorrectDescription && isCorrectSize);

        } catch (IOException e) {
            System.out.println("Testing error - Could not read/write to file");
        }
    }

    @Test
    void createTaskList_saveDataCorrupted_limitedSavesRead() {
        File testSave = new File(TEST_FILEPATH);
        try {
            if (!testSave.exists()) {
                if (testSave.createNewFile()) {
                    System.out.println("New save created for testing");
                }
            }
            fileCommand.clearSaveFile(TEST_FILEPATH);

            FileWriter fileWriter = new FileWriter(TEST_FILEPATH, true);
            fileWriter.write("lesson=-=false=-=CS1231=-=17-10-20 09:00=-=17-10-20 12:00" + System.lineSeparator());
            fileWriter.write("deadline=-=false=-=Go Home=-=16-10-20 23:59" + System.lineSeparator());
            fileWriter.write("event=-=false=-=Another Concert=-=16-10-20 22:00=-=16-10-20 23:59"
                    + System.lineSeparator());
            fileWriter.write("lesson=-=false=-==-=20-10-20 09:00=-=20-10-20 12:00" + System.lineSeparator());
            fileWriter.write("lesson=-=false=-=CS2101=-=20-10-20 09:00=-=20-10-20 12:00" + System.lineSeparator());
            fileWriter.close();

            tasks.getTaskList().clear();
            Scanner fileScanner = new Scanner(testSave);
            taskStorage.createTaskList(fileScanner);

            //Only first 3 tasks will be added. Upon detected missing data, file reading will stop
            assertEquals(3, tasks.getTaskList().size());

        } catch (IOException e) {
            System.out.println("Testing error - Unable to read/write to file");
        }
    }

    @Test
    void writeToTaskSaveFile_allTaskTypes_writeSuccess() {
        File testSave = new File(TEST_FILEPATH);
        try {
            if (!testSave.exists()) {
                if (testSave.createNewFile()) {
                    System.out.println("New save created for testing");
                }
            }
            fileCommand.clearSaveFile(TEST_FILEPATH);

            ArrayList<Task> taskList = tasks.getTaskList();
            taskList.clear();
            String[] eventComponents = {"event", "false", "Concert", "09-04-21 09:00", "09-04-21 12:00"};
            String[] deadlineComponents = {"deadline", "false", "Go home", "09-04-21 09:00"};
            String[] lessonComponents = {"lesson", "false", "CS1231", "09-04-21 09:00", "09-04-21 12:00"};

            taskStorage.rebuildEvent(eventComponents, taskList, eventComponents[2], false);
            taskStorage.rebuildDeadline(deadlineComponents, taskList, deadlineComponents[2], false);
            taskStorage.rebuildLesson(lessonComponents, taskList, lessonComponents[2], false);

            taskStorage.writeToTaskSaveFile();
            taskList.clear();
            taskStorage.readTasksSave();

            assertEquals(3, taskList.size());

        } catch (IOException e) {
            System.out.println("Testing error - Unable to read/write to file");
        }
    }

    @Test
    void rebuildEvent_validInputs_eventAdded() {
        ArrayList<Task> taskList = tasks.getTaskList();
        String[] taskComponents = {"event", "false", "Concert", "09-04-21 09:00", "09-04-21 12:00"};
        Boolean taskStatus = fileCommand.convertToBoolean(taskComponents[1]);

        taskList.clear();
        taskStorage.rebuildEvent(taskComponents, taskList, taskComponents[2], taskStatus);

        assertTrue(taskList.size() == 1 && taskList.get(0).getType().equals("event"));
    }

    @Test
    void rebuildDeadline_validInputs_deadlineAdded() {
        ArrayList<Task> taskList = tasks.getTaskList();
        String[] taskComponents = {"deadline", "false", "Go home", "09-04-21 09:00"};
        Boolean taskStatus = fileCommand.convertToBoolean(taskComponents[1]);

        taskList.clear();
        taskStorage.rebuildDeadline(taskComponents, taskList, taskComponents[2], taskStatus);

        assertTrue(taskList.size() == 1 && taskList.get(0).getType().equals("deadline"));
    }

    @Test
    void rebuildLesson_validInputs_lessonAdded() {
        ArrayList<Task> taskList = tasks.getTaskList();
        String[] taskComponents = {"lesson", "false", "CS1231", "09-04-21 09:00", "09-04-21 12:00"};
        Boolean taskStatus = fileCommand.convertToBoolean(taskComponents[1]);

        taskList.clear();
        taskStorage.rebuildLesson(taskComponents, taskList, taskComponents[2], taskStatus);

        assertTrue(taskList.size() == 1 && taskList.get(0).getType().equals("lesson"));
    }

    @Test
    void convertDeadlineToString_invalidClass_ClassCastException() {
        Event testEvent = new Event("Test event", startTime, endTime);

        assertThrows(ClassCastException.class, () -> {
            taskStorage.convertDeadlineToString(testEvent, "deadline");
        });
    }

    @Test
    void convertEventToString_invalidClass_ClassCastException() {
        Deadline testDeadline = new Deadline("Test Deadline", startTime);

        assertThrows(ClassCastException.class, () -> {
            taskStorage.convertEventToString(testDeadline, "event");
        });
    }

    @Test
    void convertLessonToString_invalidClass_ClassCastException() {
        Deadline testDeadline = new Deadline("Test Deadline", startTime);

        assertThrows(ClassCastException.class, () -> {
            taskStorage.convertLessonToString(testDeadline, "lesson");
        });
    }

    @Test
    void convertLessonToString_validInput_conversionPasses() {
        Lesson testLesson = new Lesson("CS2113T", startTime, endTime);
        String convertedString = taskStorage.convertLessonToString(testLesson, "lesson");
        assertEquals(EXPECTED_LESSON, convertedString);
    }

    @Test
    void convertEventToString_validInput_conversionPasses() {
        Event testEvent = new Event("Concert", startTime, endTime);
        String convertedString = taskStorage.convertEventToString(testEvent, "event");
        assertEquals(EXPECTED_EVENT, convertedString);
    }

    @Test
    void convertDeadlineToString_validInput_conversionPasses() {
        Deadline testDeadline = new Deadline("Return Books", startTime);
        String convertedString = taskStorage.convertDeadlineToString(testDeadline, "deadline");
        assertEquals(EXPECTED_DEADLINE, convertedString);
    }

}