package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteTaskCommand extends Command {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    private String type;
    private String name;

    public DeleteTaskCommand(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Prints a list of all Tasks that match the user's input.
     *
     * @param tasks TaskList from which Tasks will be searched.
     * @param ui Ui object to display messages to the user.
     * @param type Type of task to be searched for.
     * @param name Name of task to be searched for.
     * @throws TaskNotFoundException If no matching tasks are found.
     */
    private void printMatchingTasks(TaskList tasks, Ui ui, String type, String name) throws TaskNotFoundException {
        tasks.printMatchingTasks(type, name);
    }

    private void checkForIndexOutOfBounds(TaskList tasks, int userInput) {
        tasks.checkForIndexOutOfBounds(userInput);
    }

    /**
     * Deletes the specified Task from the TaskList.
     *
     * @param tasks TaskList from which task is to be deleted.
     * @param ui Ui object to display messages to the user.
     * @param index Index of task to be deleted.
     */
    private void deleteTask(TaskList tasks, Ui ui, int index) {
        tasks.deleteTask(index, ui);
    }

    /**
     * Executes the delete task command and deletes the Task that the user specifies.
     *
     * @param ui Ui object to display messages to the user.
     * @param notes NoteList containing user's notes.
     * @param tasks TaskList containing user's tasks.
     * @param storage Storage object to save tasks and notes to memory.
     * @param parser Parser object to parse user's inputs.
     */
    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser) {
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
