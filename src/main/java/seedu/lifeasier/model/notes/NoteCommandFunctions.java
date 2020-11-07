package seedu.lifeasier.model.notes;

public class NoteCommandFunctions {

    /**
     * Checks whether the note list is empty.
     *
     * @param notes The notes stored currently.
     * @throws EmptyNoteListException If the list is empty.
     */
    public static void checkEmptyList(NoteList notes) throws EmptyNoteListException {
        if (notes.size() == 0) {
            throw new EmptyNoteListException();
        }
    }

    /**
     * Checks if the number is beyond the size of note list.
     *
     * @param notes The notes stored currently.
     * @param noteNumber The number the user inputs.
     * @throws IndexOutOfBoundsException If the number is beyond the size of the array.
     */
    public static void checkForIndexBeyondSize(NoteList notes, int noteNumber) throws IndexOutOfBoundsException {
        if (noteNumber - 1 > notes.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Checks if the number is in the array.
     *
     * @param noteNumber The number that the user inputs.
     * @param arr The array storing matching note numbers.
     * @throws IndexOutOfBoundsException If the number is not found in the array.
     */
    public static void checkForIndexOutOfBounds(int noteNumber, int[] arr) {
        for (int j : arr) {
            if (noteNumber == j) {
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Finds the number of matching note title in the list.
     *
     * @param notes The notes stored currently.
     * @param title The title to be searched.
     * @return the number of matches found.
     */
    public static int checkNumberOfNoteMatches(NoteList notes, String title) {
        int matchNumber = 0;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                matchNumber++;
            }
        }
        return matchNumber;
    }

    /**
     * Finds the corresponding note number of the matching title.
     *
     * @param notes The notes stored currently.
     * @param title The title to be searched.
     * @returns the note number of the matching title.
     */
    public static int findNoteNumber(NoteList notes, String title) {
        int noteNumber = -1;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                noteNumber = i;
            }
        }
        return noteNumber;
    }

    /**
     * Stores matching title of notes in an array.
     *
     * @param notes The notes stored currently.
     * @param title The title to be searched.
     * @param arr The array to be stored in.
     * @returns the array with matched note numbers.
     */
    public static int[] storeNoteMatches(NoteList notes, String title, int[] arr) {
        int j = 0;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getTitle().contains(title)) {
                arr[j] = i + 1;
                j++;
            }
        }
        return arr;
    }
}
