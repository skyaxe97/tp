package seedu.lifeasier.commands;

import seedu.lifeasier.exceptions.TitleNotFoundException;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;
import seedu.lifeasier.notes.NoteList;

public class ShowNotesCommand extends Command {

    private String title;

    public ShowNotesCommand(String title) {
        this.title = title;
    }

    private void findTitle(Ui ui, NoteList notes, String title) throws TitleNotFoundException {
        int noteNumber = -1;
        int matchNumber = 0;
        int x = 1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                matchNumber++;
                noteNumber = i;
            }
        }

        switch (matchNumber) {
        case 0:     // no matches
            throw new TitleNotFoundException();
        case 1:
            System.out.println(notes.get(noteNumber).toString());
            break;
        default:
            System.out.println("Multiple matches found! Please select the one you are looking for:\n");
            for (int i = 0; i < notes.size(); i++) {
                if (notes.get(i).getTitle().contains(title)) {
                    System.out.println(x++ + ". " + notes.get(i).getTitle() + "\n");
                }
            }
            noteNumber = Integer.parseInt(ui.readCommand());
            System.out.println(notes.get(noteNumber).toString());
        }

    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage) throws
            IndexOutOfBoundsException, TitleNotFoundException {
        if (title.trim().length() > 0) {        // title is already inputted
            findTitle(ui, notes, title);
        } else {
            System.out.println("Please select the notes you want to view:\n");
            for (int i = 0; i < notes.size(); i++) {
                System.out.println((i + 1) + ". " + notes.get(i).getTitle() + "\n");
            }
            int noteNumber = Integer.parseInt(ui.readCommand());

            if (noteNumber - 1 > notes.size()) {
                throw new IndexOutOfBoundsException();
            }
            System.out.println(notes.get(noteNumber - 1).toString());
        }
    }
}
