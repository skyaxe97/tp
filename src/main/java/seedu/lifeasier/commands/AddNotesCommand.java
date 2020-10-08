package seedu.lifeasier.commands;

import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteList;

public class AddNotesCommand extends Command {

    private String title;
    private boolean isEmpty = true;


    public AddNotesCommand(String title) {
        this.title = title;
    }

    public String checkForEmpty(Ui ui) {
        String input = ui.readCommand();
        if (input.trim().length() > 0) {
            isEmpty = false;
        } else {
            System.out.println("Empty description! =O\n");
        }
        return input;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks) {
        String noteTitle = null;
        String noteDescription = null;

        ui.showNoteTitleMessage();
        while (isEmpty) {
            noteTitle = checkForEmpty(ui);
        }
        isEmpty = true;

        ui.showNoteDescriptionMessage();
        while (isEmpty) {
            noteDescription = checkForEmpty(ui);
        }
        notes.add(new Note(noteTitle, noteDescription));
        System.out.println("Ok! I've taken note of this note!\n");
    }
}
