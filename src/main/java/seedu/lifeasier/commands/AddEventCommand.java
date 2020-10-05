package seedu.lifeasier.commands;

import seedu.lifeasier.Ui;
import seedu.lifeasier.notes.NoteList;

import java.time.LocalDateTime;

public class AddEventCommand extends Command {

    private String description;
    private LocalDateTime start;
    private LocalDateTime end;

    public AddEventCommand(String description, LocalDateTime start, LocalDateTime end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(Ui ui, NoteList notes) {

    }
}
