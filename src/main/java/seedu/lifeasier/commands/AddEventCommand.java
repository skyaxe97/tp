package seedu.lifeasier.commands;

import java.time.LocalDateTime;

public class AddEventCommand extends Command{

    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AddEventCommand(String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
