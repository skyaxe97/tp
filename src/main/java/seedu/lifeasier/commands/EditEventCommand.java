package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteHistory;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskHistory;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventCommand extends Command {
    private static Logger logger = Logger.getLogger(ShowNotesCommand.class.getName());
    private String eventName = "";

    public EditEventCommand(String eventName) {
        this.eventName = eventName;
    }

    public void printMatchingEvents(TaskList tasks,Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(ui.PARAM_EVENT, code);
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
            printMatchingEvents(tasks, ui, eventName);
            ui.showSelectTaskToEdit(Ui.PARAM_EVENT);
            int userEventChoice = ui.readSingleIntInput() - 1;
            checkForIndexOutOfBounds(tasks, userEventChoice);

            Task oldCopyOfEvent = taskHistory.getCurrCopyOfTaskToEdit(tasks, userEventChoice);
            logger.log(Level.INFO, "Temporarily hold value of this Event");

            ui.showSelectParameterToEdit();
            ui.showEditableParametersMessage(Ui.PARAM_EVENT);
            int userParamChoice = Integer.parseInt(ui.readCommand());

            switch (userParamChoice) {

            case (1):
                ui.showInputMessage(ui.PARAM_EVENT);
                editEventName(tasks, userEventChoice, ui);
                break;

            case (2):
                ui.showInputFormat(ui.PARAM_EVENT);
                editEventTime(tasks, userEventChoice, ui);
                break;

            default:
                throw new IndexOutOfBoundsException();
            }

            taskHistory.pushOldCopy(oldCopyOfEvent, ui);
            logger.log(Level.INFO, "Push old copy of Event into taskHistory");

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
            logger.log(Level.SEVERE, "Input event name does not match any of the existing event names.");
            ui.showNoMatchesMessage(ui.PARAM_EVENT);
        }
        storage.saveTasks();
        logger.log(Level.INFO, "End of EditEventCommand");
    }
}