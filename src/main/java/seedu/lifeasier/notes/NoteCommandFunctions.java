package seedu.lifeasier.notes;

import seedu.lifeasier.ui.Ui;

public class NoteCommandFunctions {

    public static void checkEmptyList(NoteList notes) throws EmptyNoteListException {
        if (notes.size() == 0) {
            throw new EmptyNoteListException();
        }
    }

    public static void printMultipleMatches(Ui ui, NoteList notes, String title) {
        ui.printMultipleNoteMatches(notes, title);

    }

    public static void printAllNotes(Ui ui, NoteList notes) {
        ui.printAllNotes(notes);

    }

    public static void checkForIndexOutOfBounds(NoteList notes, int noteNumber) throws IndexOutOfBoundsException {
        if (noteNumber - 1 > notes.size()) {
            throw new IndexOutOfBoundsException();
        }
    }


}
