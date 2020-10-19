package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddDeadlineCommand extends Command {

    private String description;
    private LocalDateTime by;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {
        tasks.addDeadline(description, by);
        storage.saveTasks();
        ui.showAddConfirmationMessage("Deadline: " + description + " by " + by.format(format));
    }
}