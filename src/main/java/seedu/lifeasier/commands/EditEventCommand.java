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

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventCommand extends Command {
    private static final int EVENT_NUM_OF_TIME_ARGS = 2;
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

    public void editEventTime(TaskList tasks, int index, Ui ui, Parser parser) {
        LocalDateTime[] times = parser.parseUserInputForEditDateTime(ui, EVENT_NUM_OF_TIME_ARGS);
        tasks.editEventTime(index, ui, times);
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
            int userEventChoice = parser.parseUserInputForEditTaskChoice(ui, tasks);

            logger.log(Level.INFO, "Temporarily hold value of this Event");
            Task oldCopyOfEvent = taskHistory.getCurrCopyOfTaskToEdit(tasks, userEventChoice);

            selectParameterToEdit(parser, ui, tasks, userEventChoice);

            taskHistory.pushOldCopy(oldCopyOfEvent);
            logger.log(Level.INFO, "Push old copy of Event into taskHistory");
            storage.saveTasks();

        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showIndexOutOfBoundsError();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatError();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input event name does not match any of the existing event names.");
            ui.showNoMatchesError(Ui.PARAM_EVENT);
        }
        logger.log(Level.INFO, "End of EditEventCommand");
    }

    public void selectParameterToEdit(Parser parser, Ui ui, TaskList tasks, int userEventChoice) {
        ui.showSelectParameterToEditPrompt();
        ui.showEditableParametersMessage(Ui.PARAM_EVENT);

        logger.log(Level.INFO, "Reading user input for choice of parameter to edit...");
        int userParamChoice = parser.parseValidUserInputForParameterEdit(ui);

        switch (userParamChoice) {

        case (1):
            ui.showInputPrompt(Ui.PARAM_EVENT);
            editEventName(tasks, userEventChoice, ui);
            break;

        case (2):
            ui.showInputFormatPrompt(Ui.PARAM_EVENT);
            editEventTime(tasks, userEventChoice, ui, parser);
            break;

        default:
            throw new IndexOutOfBoundsException();
        }
    }
}