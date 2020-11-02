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

public class EditEventCommand extends Command {
    private static Logger logger = Logger.getLogger(EditEventCommand.class.getName());
    private String eventName;

    public EditEventCommand(String eventName) {
        this.eventName = eventName;
    }

    public void printMatchingEvents(TaskList tasks,Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(Ui.PARAM_EVENT, code, ui);
    }

    public void editEventName(TaskList tasks, int index, Ui ui) {
        tasks.editTaskDescription(index, ui);
    }

    public void editEventTime(TaskList tasks, int index, Ui ui) throws ParserException {
        tasks.editEventTime(index, ui);
    }

    private void checkForIndexOutOfBounds(TaskList tasks, int userInput) {
        tasks.checkForIndexOutOfBounds(userInput);
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            logger.log(Level.INFO, "Start of EditEventCommand");

            logger.log(Level.INFO, "Printing all matching events...");
            printMatchingEvents(tasks, ui, eventName);
            ui.showSelectTaskToEditPrompt(Ui.PARAM_EVENT);

            logger.log(Level.INFO, "Reading user input for choice of event to edit...");
            int userEventChoice = ui.readSingleIntInput() - 1;
            checkForIndexOutOfBounds(tasks, userEventChoice);

            logger.log(Level.INFO, "Temporarily hold value of this Event");
            Task oldCopyOfEvent = taskHistory.getCurrCopyOfTaskToEdit(tasks, userEventChoice);

            selectParameterToEdit(ui, tasks, userEventChoice);

            taskHistory.pushOldCopy(oldCopyOfEvent);
            logger.log(Level.INFO, "Push old copy of Event into taskHistory");
            storage.saveTasks();

        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showInvalidNumberError();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatError();
        } catch (ParserException e) {
            logger.log(Level.SEVERE, "Input is not in the correct format");
            ui.showInvalidInputMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input event name does not match any of the existing event names.");
            ui.showNoMatchesError(Ui.PARAM_EVENT);
        }
        logger.log(Level.INFO, "End of EditEventCommand");
    }

    public void selectParameterToEdit(Ui ui, TaskList tasks, int userEventChoice) throws ParserException {
        ui.showSelectParameterToEditPrompt();
        ui.showEditableParametersMessage(Ui.PARAM_EVENT);
        int userParamChoice = Integer.parseInt(ui.readCommand());
        logger.log(Level.INFO, "Reading user input for choice of parameter to edit...");

        switch (userParamChoice) {

        case (1):
            ui.showInputPrompt(Ui.PARAM_EVENT);
            editEventName(tasks, userEventChoice, ui);
            break;

        case (2):
            ui.showInputFormatPrompt(Ui.PARAM_EVENT);
            editEventTime(tasks, userEventChoice, ui);
            break;

        default:
            throw new IndexOutOfBoundsException();
        }
    }
}