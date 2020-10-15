package seedu.lifeasier.commands;

import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.time.LocalTime;
import java.util.ArrayList;

public class FreeTimeCommand extends Command {

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {

        tasks.getTasksFromToday();


    }

    private ArrayList<LocalTime> getTimingsOfTasks(ArrayList<Task> tasks) {
        ArrayList<LocalTime> timesList = new ArrayList<>();
        for (Task task : tasks) {
            timesList.add(task.getStart().toLocalTime());
        }
        return timesList;
    }
}
