package seedu.lifeasier.commands;

import java.time.LocalDateTime;

public class AddLessonCommand extends Command {

    private String moduleCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AddLessonCommand(String moduleCode, LocalDateTime startTime,  LocalDateTime endTime) {
        this.moduleCode = moduleCode;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
