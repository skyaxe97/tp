package seedu.lifeasier.commands;

import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

public class ShowNotesCommand extends Command {

    private String title;

    public ShowNotesCommand(String title) {
        this.title = title;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks) {
        System.out.println("Please select the notes you want to view:\n");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println(i + 1 + ". " + notes.get(i).getTitle() + "\n");
        }
        int noteNumber = Integer.parseInt(ui.readCommand());
        System.out.println(notes.get(noteNumber - 1).toString());
    }
}
