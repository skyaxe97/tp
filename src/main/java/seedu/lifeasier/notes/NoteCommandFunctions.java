package seedu.lifeasier.notes;

public class NoteCommandFunctions {

    public static void checkEmptyList(NoteList notes) throws EmptyNoteListException {
        if (notes.size() == 0) {
            throw new EmptyNoteListException();
        }
    }

    public static void checkForIndexOutOfBounds(NoteList notes, int noteNumber) throws IndexOutOfBoundsException {
        if (noteNumber - 1 > notes.size()) {
            throw new IndexOutOfBoundsException();
        }
    }


}
