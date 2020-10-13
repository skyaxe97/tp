package seedu.lifeasier;

import seedu.lifeasier.commands.Command;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.timetableui.TimetableUi;
import seedu.lifeasier.ui.Ui;

/**
 * LifEasier is a CLI application that allows busy CEG students to schedule their day.
 * If you can type fast, LifEasier will get your scheduling done faster than traditional GUI calender apps.
 */
public class LifEasier {

    private Ui ui;
    private Parser parser;
    private TaskList tasks;
    private NoteList notes;
    private FileStorage storage;
    private TimetableUi timetable;

    public LifEasier(String fileNameTasks, String fileNameNotes) {

        ui = new Ui();
        parser = new Parser();
        tasks = new TaskList();
        notes = new NoteList();
        storage = new FileStorage(fileNameTasks, fileNameNotes, ui, notes, tasks);
        timetable = new TimetableUi();
    }

    /**
     * Runs the LifEasier program infinitely until termination by the user.
     */
    public void run() {
        boolean isFinished = false;

        storage.readSaveFiles();
        ui.showWelcomeMessage();
        timetable.showHome(tasks);
        
        while (!isFinished) {

            String fullCommand = ui.readCommand();

            try {
                Command userCommand = parser.parseCommand(fullCommand, ui);
                userCommand.execute(ui, notes, tasks, storage);
                isFinished = userCommand.isFinished();

            } catch (ParserException e) {
                ui.showParseUnknownCommandMessage();

            }

        }

        ui.showGoodbyeMessage();
    }

    /**
     * Main entry-point for the LifEasier application.
     */
    public static void main(String[] args) {
        new LifEasier("saveFileTasks.txt", "saveFileNotes.txt").run();
    }
}
