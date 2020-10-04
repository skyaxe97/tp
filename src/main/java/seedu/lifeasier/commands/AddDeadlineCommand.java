package seedu.lifeasier.commands;

import java.time.LocalDateTime;

public class AddDeadlineCommand extends Command {

    private String description;
    private LocalDateTime by;

    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

}