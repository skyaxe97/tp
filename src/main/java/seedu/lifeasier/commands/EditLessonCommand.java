package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.model.tasks.Task;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.model.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.parser.Parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditLessonCommand extends Command {
    private static Logger logger = Logger.getLogger(EditLessonCommand.class.getName());
    private String code;

    public EditLessonCommand(String code) {
        this.code = code;
    }

    public void printLessonsMatchingCode(TaskList tasks,Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(Ui.PARAM_LESSON, code, ui);
    }

    public void editLessonModuleCode(TaskList tasks, int index, Ui ui) {
        tasks.editTaskDescription(index, ui);
    }

    public void editLessonTime(TaskList tasks, int index, Ui ui) throws ParserException {
        tasks.editLessonTime(index, ui);
    }

    private void checkForIndexOutOfBounds(TaskList tasks, int userInput) {
        tasks.checkForIndexOutOfBounds(userInput);
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            logger.log(Level.INFO, "Start of EditLessonCommand");

            logger.log(Level.INFO, "Printing all matching lessons...");
            printLessonsMatchingCode(tasks, ui, code);
            ui.showSelectTaskToEditPrompt(Ui.PARAM_LESSON);

            logger.log(Level.INFO, "Reading user input for choice of lesson to edit...");
            int userLessonChoice = ui.readSingleIntInput() - 1;
            checkForIndexOutOfBounds(tasks, userLessonChoice);

            logger.log(Level.INFO, "Temporarily hold value of this Event");
            Task oldCopyOfLesson = taskHistory.getCurrCopyOfTaskToEdit(tasks, userLessonChoice);

            selectParameterToEdit(ui, tasks, userLessonChoice);

            taskHistory.pushOldCopy(oldCopyOfLesson);
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
            ui.showInvalidCommandFormatMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input deadline name does not match any of the existing deadline names.");
            ui.showNoMatchesError(Ui.PARAM_LESSON);
        }
        logger.log(Level.INFO, "End of EditLessonCommand");
    }

    public void selectParameterToEdit(Ui ui, TaskList tasks, int userLessonChoice) throws ParserException {
        ui.showSelectParameterToEditPrompt();
        ui.showEditableParametersMessage(Ui.PARAM_LESSON);
        int userParamChoice = Integer.parseInt(ui.readCommand());

        switch (userParamChoice) {

        case (1):
            ui.showInputPrompt(Ui.PARAM_LESSON);
            editLessonModuleCode(tasks, userLessonChoice, ui);
            break;

        case (2):
            ui.showInputFormatPrompt(Ui.PARAM_LESSON);
            editLessonTime(tasks, userLessonChoice, ui);
            break;

        default:
            throw new IndexOutOfBoundsException();
        }
    }
}
