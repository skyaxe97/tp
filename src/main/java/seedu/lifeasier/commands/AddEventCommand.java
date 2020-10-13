package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEventCommand extends Command {

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
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        tasks.addEvent(description, start, end);
        storage.saveTasks();
        ui.showAddConfirmationMessage("Event: " + description  + " from "
                + start.format(format) + " to " + end.format(format));
    }
}
