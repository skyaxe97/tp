package seedu.lifeasier;

import seedu.lifeasier.commands.Command;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

/**
 * LifEasier is a CLI application that allows busy CEG students to schedule their day faster than traditional
 * GUI based apps if they can type fast.
 */
public class LifEasier {

    private Ui ui;
    private Parser parser;
    private TaskList tasks;
    private NoteList notes;
    private FileStorage storage;

    public LifEasier(String fileNameTasks, String fileNameNotes) {

        ui = new Ui();
        parser = new Parser();
        tasks = new TaskList();
        notes = new NoteList();
        storage = new FileStorage(fileNameTasks, fileNameNotes, ui);
    }

    /**
     * Runs the LifEasier program infinitely until termination by the user.
     */
    public void run() {
        boolean isFinished = false;

        storage.readTaskSaveFile();

        ui.showWelcomeMessage();

        while (!isFinished) {
            String fullCommand = ui.readCommand();
            try {

                Command userCommand = parser.parseCommand(fullCommand);
                userCommand.execute(ui, notes, tasks);
                isFinished = userCommand.isFinished();

            } catch (ParserException e) {
                System.out.println("There's been an error understanding your input! Please double check your input!");
            }

        }

        ui.showGoodbyeMessage();
    }

    /**
     * Main entry-point for the LifEasier application
     */
    public static void main(String[] args) {
        new LifEasier("saveFileTasks.txt", "saveFileNotes.txt").run();
    }
}
