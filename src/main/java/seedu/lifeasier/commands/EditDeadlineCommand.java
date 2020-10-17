package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditDeadlineCommand extends Command {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    private String deadlineName = "";

    public EditDeadlineCommand(String deadlineName) {
        this.deadlineName = deadlineName;
    }

    public void printMatchingDeadlines(TaskList tasks,Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(ui, ui.PARAM_DEADLINE, code);
    }

    public void editDeadlineName(TaskList tasks, int index, Ui ui) {
        tasks.editTaskDescription(index, ui);
    }

    public void editDeadlineTime(TaskList tasks, int index, Ui ui) throws ParserException {
        tasks.editDeadlineTime(index, ui);;
    }

    private void checkForIndexOutOfBounds(TaskList tasks, int userInput) {
        tasks.checkForIndexOutOfBounds(userInput);
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        try {
            logger.log(Level.INFO, "Start of EditDeadlineCommand");
            printMatchingDeadlines(tasks, ui, deadlineName);
            ui.showSelectTaskToEdit(Ui.PARAM_DEADLINE);
            int userDeadlineChoice = Integer.parseInt(ui.readCommand()) - 1;
            checkForIndexOutOfBounds(tasks, userDeadlineChoice);
            ui.showSelectParameterToEdit();
            ui.showEditableParameters(Ui.PARAM_DEADLINE);
            int userParamChoice = Integer.parseInt(ui.readCommand());

            switch (userParamChoice) {

            case (1):
                ui.showInputMessage(ui.PARAM_DEADLINE);
                editDeadlineName(tasks, userDeadlineChoice, ui);
                break;

            case (2):
                ui.showInputFormat(ui.PARAM_DEADLINE);
                editDeadlineTime(tasks, userDeadlineChoice, ui);
                break;

            default:
                throw new IndexOutOfBoundsException();
            }

        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showInvalidNumberMessage();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatMessage();
        } catch (ParserException e) {
            logger.log(Level.SEVERE, "Input is not in the correct format");
            ui.showInvalidInputMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input Deadline name does not match any of the existing Deadline names.");
            ui.showNoMatchesMessage("deadline");
        }
        storage.saveTasks();
        logger.log(Level.INFO, "End of EditDeadlineCommand");
    }
}
