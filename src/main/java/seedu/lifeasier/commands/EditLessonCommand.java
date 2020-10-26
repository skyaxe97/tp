package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteHistory;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.ParserException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskHistory;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.tasks.TaskNotFoundException;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.parser.Parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditLessonCommand extends Command {
    private static Logger logger = Logger.getLogger(EditLessonCommand.class.getName());
    private String code = "";

    public EditLessonCommand(String code) {
        this.code = code;
    }

    public void printLessonsMatchingCode(TaskList tasks,Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(ui.PARAM_LESSON, code);
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
            ui.showSelectTaskToEdit(Ui.PARAM_LESSON);

            logger.log(Level.INFO, "Reading user input for choice of lesson to edit...");
            int userLessonChoice = ui.readSingleIntInput() - 1;
            checkForIndexOutOfBounds(tasks, userLessonChoice);
            ui.showSelectParameterToEdit();
            ui.showEditableParametersMessage(Ui.PARAM_LESSON);

            logger.log(Level.INFO, "Reading user input for choice of parameter to edit...");
            int userParamChoice = Integer.parseInt(ui.readCommand());

            switch (userParamChoice) {

            case (1):
                ui.showInputMessage(ui.PARAM_LESSON);
                editLessonModuleCode(tasks, userLessonChoice, ui);
                break;

            case (2):
                ui.showInputFormat(ui.PARAM_LESSON);
                editLessonTime(tasks, userLessonChoice, ui);
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
            logger.log(Level.SEVERE, "Input deadline name does not match any of the existing deadline names.");
            ui.showNoMatchesMessage("deadline");
        }

        logger.log(Level.INFO, "Saving updated taskList to storage...");
        storage.saveTasks();
        logger.log(Level.INFO, "End of EditLessonCommand");
    }
}
