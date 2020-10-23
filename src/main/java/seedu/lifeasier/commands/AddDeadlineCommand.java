package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddDeadlineCommand extends Command {

    private String description;
    private LocalDateTime by;
    private int recurrences;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    public AddDeadlineCommand(String description, LocalDateTime by, int recurrences) {
        this.description = description;
        this.by = by;
        this.recurrences = recurrences;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {
        Task task = tasks.addDeadline(description, by, recurrences);
        storage.saveTasks();
        ui.showAddConfirmationMessage(task);
    }
}