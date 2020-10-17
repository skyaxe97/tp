package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;

import java.util.logging.*;

public class DeleteTaskCommand extends Command {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    private String type;
    private String name;

    public DeleteTaskCommand(String type, String name) {
        this.type = type;
        this.name = name;
    }

    private void printMatchingTasks(TaskList tasks, Ui ui, String type, String name) throws TaskNotFoundException {
        tasks.printMatchingTasks(ui, type, name);
    }

    private void checkForIndexOutOfBounds(TaskList tasks, int userInput) {
        tasks.checkForIndexOutOfBounds(userInput);
    }

    private void deleteTask (TaskList tasks, Ui ui, int index) {
        tasks.deleteTask(index, ui);
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        try {
            if (type.equals("")) {
                throw new ParserException();
            }
            logger.log(Level.INFO, "Start of DeleteTaskCommand");
            ui.showSelectTaskToDelete(type);
            printMatchingTasks(tasks, ui, type, name);
            int userTaskChoice = ui.readSingleIntInput() - 1;
            checkForIndexOutOfBounds(tasks, userTaskChoice);
            deleteTask(tasks, ui, userTaskChoice);
            storage.saveTasks();
            ui.showDeleteConfirmationMessage();
        } catch (ParserException e) {
            ui.showInvalidInputMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input " + type + " name does not match any of the existing "
                    + type + " names.");
            ui.showNoMatchesMessage(type);
        }
        logger.log(Level.INFO, "End of DeleteTaskCommand");
    }
}
