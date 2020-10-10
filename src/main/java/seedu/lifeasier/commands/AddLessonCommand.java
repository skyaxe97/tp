package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

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

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {

    }
}
