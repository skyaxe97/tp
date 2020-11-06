package seedu.lifeasier.model.notes;

public class NoteCommandFunctions {

    public static void checkEmptyList(NoteList notes) throws EmptyNoteListException {
        if (notes.size() == 0) {
            throw new EmptyNoteListException();
        }
    }

    public static void checkForIndexBeyondSize(NoteList notes, int noteNumber) throws IndexOutOfBoundsException {
        if (noteNumber - 1 > notes.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void checkForIndexOutOfBounds(NoteList notes, int noteNumber, int[] arr) {
        for (int j : arr) {
            if (noteNumber == j) {
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public static int checkNumberOfNoteMatches(NoteList notes, String title) {
        int matchNumber = 0;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                matchNumber++;
            }
        }
        return matchNumber;
    }

    public static int findNoteNumber(NoteList notes, String title) {
        int noteNumber = -1;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                noteNumber = i;
            }
        }
        return noteNumber;
    }
}
