package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteHistory;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskHistory;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

public class UndoTaskCommand extends Command {

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            int lastTaskEditNumber = taskHistory.getLastTask().getEditNumber();
            for (int i = 0; i < tasks.getTaskCount(); i++) {
                int editNumOfCurrTask =  tasks.getTask(i).getEditNumber();
                if (editNumOfCurrTask == lastTaskEditNumber) {
                    Task oldCopyOfCurrTask = taskHistory.getLastTask();
                    tasks.setTask(i, oldCopyOfCurrTask);
                }
            }

            if (lastTaskEditNumber > 0) {
                ui.showUndoTaskEditMessage();
            } else {
                ui.showUndoTaskDeleteMessage();
            }
            System.out.println(taskHistory.getLastTask());

            taskHistory.popLastTask();

        } catch (IndexOutOfBoundsException e) {
            ui.showInvalidUndoAction();
        }
    }
}