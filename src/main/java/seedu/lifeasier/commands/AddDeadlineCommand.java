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

public class AddDeadlineCommand extends Command {

    private static Logger logger = Logger.getLogger(AddDeadlineCommand.class.getName());

    private String description;
    private LocalDateTime by;
    private int recurrences;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    public AddDeadlineCommand(String description, LocalDateTime by, int recurrences) {
        this.description = description;
        this.by = by;
        this.recurrences = recurrences;
    }

    /**
     * Adds a Deadline to the schedule if the Deadline is not in the past and if it does not already exist in the schedule.
     *
     * @param ui Ui object to display messages to the user.
     * @param notes NoteList containing user's notes.
     * @param tasks TaskList containing user's tasks.
     * @param storage Storage object to save tasks and notes to memory.
     * @param parser Parser object to parse user's inputs.
     * @param noteHistory NoteHistory object to store history of edited and deleted notes.
     * @param taskHistory TaskHistory object to store history of edited and deleted tasks.
     */
    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {

        try {
            logger.log(Level.INFO, "Adding deadline to taskList...");
            Task task = tasks.addDeadline(description, by, recurrences);
            ui.showAddConfirmationMessage(task);
            tasks.updateTasks(LocalDate.now());
            logger.log(Level.INFO, "Saving updated taskList to storage...");
            storage.saveTasks();

        } catch (TaskDuplicateException e) {
            logger.log(Level.INFO, "Task is a duplicate! Showing error...");
            ui.showDuplicateTaskError(Ui.PARAM_DEADLINE);

        } catch (TaskPastException e) {
            logger.log(Level.INFO, "Task is in the past! Showing error...");
            ui.showPastTaskError(Ui.PARAM_DEADLINE);
        }

    }
}
