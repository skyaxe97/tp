package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskDuplicateException;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.model.tasks.TaskPastException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.Parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddEventCommand extends Command {

    private static Logger logger = Logger.getLogger(AddEventCommand.class.getName());

    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int recurrences;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    public AddEventCommand(String description, LocalDateTime start, LocalDateTime end, int recurrences) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.recurrences = recurrences;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {

        try {
            logger.log(Level.INFO, "Adding event to taskList...");
            Task task = tasks.addEvent(description, start, end, recurrences);
            tasks.updateTasks(LocalDate.now());
            ui.showAddConfirmationMessage(task);
            logger.log(Level.INFO, "Saving updated taskList to storage...");
            storage.saveTasks();


        } catch (TaskDuplicateException e) {
            logger.log(Level.INFO, "Task is a duplicate! Showing error...");
            ui.showDuplicateTaskError(Ui.PARAM_EVENT);

        } catch (TaskPastException e) {
            logger.log(Level.INFO, "Task is in the past! Showing error...");
            ui.showPastTaskError(Ui.PARAM_EVENT);
        }
    }
}
