package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditDeadlineCommand extends Command {
    private static final int DEADLINE_NUM_OF_TIME_ARGS = 1;
    private static Logger logger = Logger.getLogger(EditDeadlineCommand.class.getName());
    private String deadlineName;

    public EditDeadlineCommand(String deadlineName) {
        this.deadlineName = deadlineName;
    }

    public void printMatchingDeadlines(TaskList tasks, Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(Ui.PARAM_DEADLINE, code, ui);
    }

    private void editDeadlineName(TaskList tasks, int index, Ui ui) {
        tasks.editTaskDescription(index, ui);
    }

    private void editDeadlineTime(TaskList tasks, int index, Ui ui, Parser parser) {
        LocalDateTime[] times = parser.parseUserInputForEditDateTime(ui, DEADLINE_NUM_OF_TIME_ARGS);
        tasks.editDeadlineTime(index, ui, times);
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            logger.log(Level.INFO, "Start of EditDeadlineCommand");

            logger.log(Level.INFO, "Printing all matching deadlines...");
            printMatchingDeadlines(tasks, ui, deadlineName);
            ui.showSelectTaskToEdit(Ui.PARAM_DEADLINE);

            logger.log(Level.INFO, "Reading user input for choice of task to delete...");
            int userDeadlineChoice = parser.parseUserInputForEditTaskChoice(ui, tasks);

            logger.log(Level.INFO, "Temporarily hold value of this Deadline");
            Task oldCopyOfDeadline = taskHistory.getCurrCopyOfTaskToEdit(tasks, userDeadlineChoice);

            selectParameterToEdit(parser, ui, tasks, userDeadlineChoice);

            taskHistory.pushOldCopy(oldCopyOfDeadline);
            logger.log(Level.INFO, "Push old copy of Deadline into taskHistory");
            storage.saveTasks();

        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showIndexOutOfBoundsMessage();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatMessage();
        } catch (ParserException e) {
            logger.log(Level.SEVERE, "Input is not in the correct format");
            ui.showInvalidInputMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input Deadline name does not match any of the existing Deadline names.");
            ui.showNoMatchesMessage(Ui.PARAM_DEADLINE);
        }
        logger.log(Level.INFO, "End of EditDeadlineCommand");
    }

    private void selectParameterToEdit(Parser parser, Ui ui, TaskList tasks, int userDeadlineChoice) throws ParserException {
        ui.showSelectParameterToEdit();
        ui.showEditableParametersMessage(Ui.PARAM_DEADLINE);

        logger.log(Level.INFO, "Reading user input for choice of parameter to edit...");
        int userParamChoice = parser.parseValidUserInputForParameterEdit(ui);

        switch (userParamChoice) {

        case (1):
            ui.showInputMessage(Ui.PARAM_DEADLINE);
            editDeadlineName(tasks, userDeadlineChoice, ui);
            break;

        case (2):
            ui.showInputFormat(Ui.PARAM_DEADLINE);
            editDeadlineTime(tasks, userDeadlineChoice, ui, parser);
            break;

        default:
            throw new IndexOutOfBoundsException();
        }
        ui.printSeparator();
    }
}
