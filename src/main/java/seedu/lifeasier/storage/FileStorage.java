package seedu.lifeasier.storage;

import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.tasks.*;
import seedu.lifeasier.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The FileStorage class will handle actions related to the reading and writing of the LifEasier save file.
 */
public class FileStorage {

    private static final String DIRECTORY_PATH = "/LifEasierSaves/";
    private static final String SAVE_DELIMITER = "=-=";

    private String filePathTasks;
    private String filePathNotes;
    private Ui ui;
    private NoteList notes;
    private TaskList tasks;

    public FileStorage(String fileNameTasks, String fileNameNotes, Ui ui, NoteList notes, TaskList tasks) {
        this.filePathTasks = DIRECTORY_PATH + fileNameTasks;
        this.filePathNotes = DIRECTORY_PATH + fileNameNotes;
        this.ui = ui;
        this.notes = notes;
        this.tasks = tasks;
    }

    /**
     * Reads and loads the stored information on the save file. If save files are missing, the method will create a new
     * save file and directory.
     */
    public void readSaveFiles() {
        File saveFileTasks = new File(filePathTasks);
        File saveFileNotes = new File(filePathNotes);

        if (!fileExists(saveFileTasks, saveFileNotes)) {
            createNewSaves(saveFileTasks, saveFileNotes);
        } else {
            System.out.println("Reading your save data...");
            readNotesSave();
        }
    }

    /**
     * Checks if both save files exist.
     *
     * @param saveFileTasks File object with file path to the save file which contains saved task information.
     * @param saveFileNotes File object with file path to the save file which contains saved note information.
     * @return True when both files exist.
     */
    private boolean fileExists(File saveFileTasks, File saveFileNotes) {
        return saveFileTasks.exists() && saveFileNotes.exists();
    }

    /**
     * Reads and loads all saved note information.
     */
    private void readNotesSave() {
        try {
            File saveFile = new File(filePathNotes);

            Scanner fileScanner = new Scanner(saveFile);
            createNoteList(fileScanner);

            System.out.println("Notes save file successfully read!");

        } catch (IOException e) {
            System.out.println("Something went wrong, unable to read from notes save file...");
        }

    }

    private void createNoteList(Scanner fileScanner) {
        while (fileScanner.hasNext()) {
            String noteInformation = fileScanner.nextLine();

            String[] noteComponents = noteInformation.split(SAVE_DELIMITER);
            String noteTitle = noteComponents[0];
            String noteDescription = noteComponents[1];

            notes.add(new Note(noteTitle, noteDescription));
        }
    }

    /**
     * Writes information of TaskList onto the save file for storage whenever there is a change.
     *
     * @throws IOException When the file cannot be found or is corrupted.
     */
    public void writeToTaskSaveFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filePathTasks, true);
        clearSaveFile(filePathTasks);

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
                System.out.println("Something went wrong while saving the tasks...");
                dataToSave = "";
                break;
            }
            fileWriter.write(dataToSave);
        }
        fileWriter.close();
    }

    private String generateLessonSave(Task task, String taskType) {
        Lesson lesson = (Lesson) task;
        return taskType + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER + lesson.getStart().toString()
                + SAVE_DELIMITER + lesson.getEnd().toString() + System.lineSeparator();
    }

    private String generateEventSave(Task task, String taskType) {
        Event event = (Event) task;
        return taskType + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER + event.getStart().toString()
                + SAVE_DELIMITER + event.getEnd().toString() + System.lineSeparator();
    }

    private String generateDeadlineSave(Task task, String taskType) {
        Deadline deadline = (Deadline) task;
        return taskType + SAVE_DELIMITER + task.getDescription() + SAVE_DELIMITER + deadline.getBy().toString()
                + System.lineSeparator();
    }

    /**
     * Writes information from the notes onto the notes save file for storage when there is a change to the notes.
     *
     * @throws IOException When the file cannot be found or is corrupted.
     */
    public void writeToNoteSaveFile() {
        try {
            FileWriter fileWriter = new FileWriter(filePathNotes, true);
            clearSaveFile(filePathNotes);
            ArrayList<Note> noteList = notes.getNotes();
            //Append note information into save file for notes
            for (Note note : noteList) {
                String noteToSave = generateNoteSave(note);
                fileWriter.write(noteToSave);
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while saving your notes...");
        }
    }

    private String generateNoteSave(Note note) {
        return note.getTitle() + SAVE_DELIMITER + note.getDescription() + System.lineSeparator();
    }

    /**
     * Clears all data from the specified save file.
     *
     * @param filePath File path to which file to clear information.
     */
    private void clearSaveFile(String filePath) {
        try {
            FileWriter fileClear = new FileWriter(filePath);
            fileClear.write("");
            fileClear.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while clearing the file...");
        }
    }

    /**
     * Determines if save directory and files are existent, before creating new save files and directories.
     */
    private void createNewSaves(File saveFileTasks, File saveFileNotes) {
        File fileDirectory = new File(DIRECTORY_PATH);

        //If directory exists, create missing save files
        if (directoryExists(fileDirectory)) {
            ui.showDirectoryDetected();

            if (!saveFileTasks.exists()) {
                createTaskSaveFile(saveFileTasks);
            }

            if (!saveFileNotes.exists()) {
                createNoteSaveFile(saveFileNotes);
            }

        } else {
            handleMissingSaveDirectory(fileDirectory, saveFileTasks, saveFileNotes);
        }
    }

    private boolean directoryExists(File fileDirectory) {
        return fileDirectory.exists();
    }

    /**
     * Handles when no save directory is detected and creates a new directory.
     *
     * @param fileDirectory File object which holds the directory path to be created.
     */
    private void handleMissingSaveDirectory(File fileDirectory, File saveFileTasks, File saveFileNotes) {
        ui.showNoDirectoryDetected();
        //Attempt creation of new save directory to hold save files
        if (createNewDirectory(fileDirectory)) {
            ui.showNewDirectoryCreated();
            createTaskSaveFile(saveFileTasks);
            createNoteSaveFile(saveFileNotes);
        } else {
            ui.showNewDirectoryFailCreated();
        }
    }

    private boolean createNewDirectory(File fileDirectory) {
        return fileDirectory.mkdir();
    }

    /**
     * Creates a new save file for storing tasks at the specified path.
     *
     * @param saveFileTasks File object which holds the path at which the save file is to be created.
     */
    private void createTaskSaveFile(File saveFileTasks) {
        ui.showCreatingNewSaveFile();

        try {
            if (saveFileTasks.createNewFile()) {
                ui.showFileCreationSuccess();
            }
        } catch (IOException e) {
            ui.showFileCreationError();
        }
    }

    private void createNoteSaveFile(File saveFileNotes) {
        ui.showCreatingNewNotesSaveFile();

        try {
            if (saveFileNotes.createNewFile()) {
                ui.showFileCreationSuccess();
            }
        } catch (IOException e) {
            ui.showFileCreationError();
        }
    }

}
