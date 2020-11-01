package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.model.tasks.TaskNotFoundException;
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
        tasks.printMatchingTasks(type, name, ui);
    }

    private int readUserInput(Ui ui, Parser parser, TaskList tasks) {
        while (true) {
            try {
                int newIndex = parser.checkIfValidNumber(ui, ui.readCommand()) - 1;
                tasks.checkForIndexOutOfBounds(newIndex);
                return newIndex;
            } catch (IndexOutOfBoundsException e) {
                ui.showIndexOutOfBoundsMessage();
                continue;
            }
        }
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
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            if (type.equals("")) {
                throw new ParserException();
            }
            logger.log(Level.INFO, "Start of DeleteTaskCommand...");

            logger.log(Level.INFO, "Printing all matching tasks...");
            printMatchingTasks(tasks, ui, type, name);
            ui.showSelectTaskToDelete(type);

            logger.log(Level.INFO, "Reading user input for choice of task to delete...");
            int userIndex = readUserInput(ui, parser, tasks);

            logger.log(Level.INFO, "Temporarily hold value of this Task");
            Task oldCopyOfTask = taskHistory.getCurrCopyOfTaskToDelete(tasks, userIndex);

            logger.log(Level.INFO, "Deleting task from taskList...");
            deleteTask(tasks, ui, userIndex);

            taskHistory.pushOldCopy(oldCopyOfTask);
            logger.log(Level.INFO, "Push old copy of Task into taskHistory");

            logger.log(Level.INFO, "Saving updated taskList to storage...");
            storage.saveTasks();
            ui.showDeleteConfirmationMessage();

        } catch (ParserException e) {
            logger.log(Level.SEVERE, "User input is invalid");
            ui.showInvalidInputMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input " + type + " name does not match any of the existing "
                    + type + " names.");
            ui.showNoMatchesMessage(type);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "User input is out of bounds");
            ui.showIndexOutOfBoundsMessage();
        }
        ui.printSeparator();
        logger.log(Level.INFO, "End of DeleteTaskCommand");
    }
}
