package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddLessonCommand extends Command {

    private static Logger logger = Logger.getLogger(AddLessonCommand.class.getName());

    private String moduleCode;
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm");

    public AddLessonCommand(String moduleCode, LocalDateTime start, LocalDateTime end) {
        this.moduleCode = moduleCode;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {

        logger.log(Level.INFO, "Adding lesson to taskList...");
        tasks.addLesson(moduleCode, start, end);

        logger.log(Level.INFO, "Saving updated taskList to storage...");
        storage.saveTasks();
        ui.showAddConfirmationMessage("Lesson: " + moduleCode + " from "
                + start.format(format) + " to " + end.format(format));
    }
}
