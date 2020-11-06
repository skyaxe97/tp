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

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditLessonCommand extends Command {
    private static final int LESSON_NUM_OF_TIME_ARGS = 2;
    private static Logger logger = Logger.getLogger(EditLessonCommand.class.getName());
    private String code;

    public EditLessonCommand(String code) {
        this.code = code;
    }

    public void printLessonsMatchingCode(TaskList tasks,Ui ui, String code) throws TaskNotFoundException {
        tasks.printMatchingTasks(Ui.PARAM_LESSON, code, ui);
    }

    public void editLessonModuleCode(TaskList tasks, int index, Ui ui, Parser parser) {
        String moduleCode = ui.readCommand();
        if (!parser.checkIfValidModuleCode(moduleCode)) {
            moduleCode = parser.getValidModuleCode(ui);
        }
        tasks.editModuleCode(index, ui, moduleCode);
    }

    public void editLessonTime(TaskList tasks, int index, Ui ui, Parser parser) {
        LocalDateTime[] times = parser.parseUserInputForEditDateTime(ui, LESSON_NUM_OF_TIME_ARGS);
        tasks.editLessonTime(index, ui, times);
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
            int userLessonChoice = parser.parseUserInputForEditTaskChoice(ui, tasks);

            logger.log(Level.INFO, "Temporarily hold value of this Event");
            Task oldCopyOfLesson = taskHistory.getCurrCopyOfTaskToEdit(tasks, userLessonChoice);

            selectParameterToEdit(parser, ui, tasks, userLessonChoice);

            taskHistory.pushOldCopy(oldCopyOfLesson);
            logger.log(Level.INFO, "Push old copy of Event into taskHistory");
            storage.saveTasks();

        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "Input number is out of bounds");
            ui.showInvalidNumberMessage();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Input is not a number");
            ui.showNumberFormatMessage();
        } catch (TaskNotFoundException e) {
            logger.log(Level.SEVERE, "Input lesson name does not match any of the existing deadline names.");
            ui.showNoMatchesMessage(Ui.PARAM_LESSON);
        }
        logger.log(Level.INFO, "End of EditLessonCommand");
    }

    public void selectParameterToEdit(Parser parser, Ui ui, TaskList tasks, int userLessonChoice) {
        ui.showSelectParameterToEdit();
        ui.showEditableParametersMessage(Ui.PARAM_LESSON);

        logger.log(Level.INFO, "Reading user input for choice of parameter to edit...");
        int userParamChoice = parser.parseValidUserInputForParameterEdit(ui);

        switch (userParamChoice) {

        case (1):
            ui.showInputMessage(Ui.PARAM_LESSON);
            editLessonModuleCode(tasks, userLessonChoice, ui, parser);
            break;

        case (2):
            ui.showInputFormat(Ui.PARAM_LESSON);
            editLessonTime(tasks, userLessonChoice, ui, parser);
            break;

        default:
            throw new IndexOutOfBoundsException();
        }
        ui.printSeparator();
    }
}
