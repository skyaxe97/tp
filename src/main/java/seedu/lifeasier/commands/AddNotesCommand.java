package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
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

    private String isValidTitle(Ui ui, String title) {
        String noteTitle = null;

        if (title.trim().length() > 0) {        // title is already inputted
            isEmpty = false;
            noteTitle = title;
        } else {
            ui.showNoteTitleMessage();
            while (isEmpty) {
                noteTitle = checkForEmpty(ui);
            }
        }
        return noteTitle;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        String noteDescription = null;

        final String noteTitle = isValidTitle(ui, title);

        isEmpty = true;
        ui.showNoteDescriptionMessage();

        while (isEmpty) {
            noteDescription = checkForEmpty(ui);
        }

        notes.add(new Note(noteTitle,noteDescription));
        System.out.println("Ok! I've taken note of this note!\n");

        storage.saveNote();
    }

}
