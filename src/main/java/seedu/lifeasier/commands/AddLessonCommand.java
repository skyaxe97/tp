package seedu.lifeasier.commands;

import java.time.LocalDateTime;

public class AddLessonCommand extends Command {

    private String moduleCode;
    private LocalDateTime start;
    private LocalDateTime end;

    public AddLessonCommand(String moduleCode, LocalDateTime start, LocalDateTime end) {
        this.moduleCode = moduleCode;
        this.start = start;
        this.end = end;
    }

}
