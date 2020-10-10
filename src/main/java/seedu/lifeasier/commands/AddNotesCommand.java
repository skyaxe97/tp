package seedu.lifeasier.commands;

import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.storage.NoteStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.Note;
import seedu.lifeasier.notes.NoteList;

public class AddNotesCommand extends Command {

    private String title;

    public AddNotesCommand(String title) {
        this.title = title;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) {
        ui.showNoteTitleMessage();
        String noteTitle = ui.readCommand();

        ui.showNoteDescriptionMessage();
        String noteDescription = ui.readCommand();

        notes.add(new Note(noteTitle,noteDescription));
        System.out.println("Ok! I've taken note of this note!\n");

        storage.saveNote();
    }
}
