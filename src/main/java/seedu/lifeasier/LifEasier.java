package seedu.lifeasier;

import org.fusesource.jansi.AnsiConsole;

import seedu.lifeasier.commands.Command;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.ui.ScheduleUi;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.util.logging.LogManager;

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
    private ScheduleUi scheduleUi;
    private NoteHistory noteHistory;
    private TaskHistory taskHistory;

    public LifEasier(String fileNameTasks, String fileNameNotes) {
        ui = new Ui();
        parser = new Parser();
        tasks = new TaskList();
        notes = new NoteList();
        storage = new FileStorage(fileNameTasks, fileNameNotes, ui, notes, tasks);
        scheduleUi = new ScheduleUi();
        noteHistory = new NoteHistory();
        taskHistory = new TaskHistory();

        AnsiConsole.systemInstall();
    }

    /**
     * Runs the LifEasier program infinitely until termination by the user.
     */
    public void run(boolean showLogging) {

        if (!showLogging) {
            LogManager.getLogManager().reset();
        }

        storage.readSaveFiles();

        tasks.updateTasks(LocalDate.now());

        showStartupSequence();

        boolean isFinished = false;

        while (!isFinished) {

            String fullCommand = ui.readCommand();

            try {
                Command userCommand = parser.parseCommand(fullCommand, ui);
                userCommand.execute(ui, notes, tasks, storage, parser, noteHistory, taskHistory);
                isFinished = userCommand.isFinished();

            } catch (ParserException e) {
                ui.showParseUnknownCommandMessage();

            }

        }

        ui.showGoodbyeMessage();
        AnsiConsole.systemUninstall();
    }

    public void showStartupSequence() {
        ui.showLogo();
        ui.showGreetingMessage();
    }


    /**
     * Main entry-point for the LifEasier application.
     */
    public static void main(String[] args) {
        new LifEasier("saveFileTasks.txt", "saveFileNotes.txt").run(false);
    }
}
