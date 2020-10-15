package seedu.lifeasier.notes;

public class NoteCommandFunctions {

    public static void checkEmptyList(NoteList notes) throws EmptyNoteListException {
        if (notes.size()==0) {
            throw new EmptyNoteListException();
        }
    }

    public static void printMultipleMatches(NoteList notes, String title) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                System.out.println(i + 1 + ". " + notes.get(i).getTitle() + "\n");
            }
        }

    }

    public static void printAllNotes(NoteList notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() + "\n");
        }

    }

    public static void checkForIndexOutOfBounds(NoteList notes, int noteNumber) throws IndexOutOfBoundsException {
        if (noteNumber - 1 > notes.size()) {
            throw new IndexOutOfBoundsException();
        }
    }


}
