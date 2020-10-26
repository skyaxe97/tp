package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteHistory;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskHistory;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddEventCommand extends Command {

    private static Logger logger = Logger.getLogger(AddEventCommand.class.getName());

    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    public AddEventCommand(String description, LocalDateTime start, LocalDateTime end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        logger.log(Level.INFO, "Adding event to taskList...");
        tasks.addEvent(description, start, end);

        logger.log(Level.INFO, "Saving updated taskList to storage...");
        storage.saveTasks();

        ui.showAddConfirmationMessage("Event: " + description  + " from "
                + start.format(format) + " to " + end.format(format));
    }
}
