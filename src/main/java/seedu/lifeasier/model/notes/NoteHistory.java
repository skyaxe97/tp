package seedu.lifeasier.model.notes;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The NoteHistory class represents the list of changes made to Note objects,
 * edits or deletions in particular.
 */
public class NoteHistory {

    private int changeCount;
    private int editCount;
    private int deleteCount;

    private ArrayList<Note> noteHistory;

    private static final int DEFAULT_EDIT_NUMBER = -999999;

    public NoteHistory() {
        this.changeCount = 0;
        this.editCount = 0;
        this.deleteCount = 0;
        this.noteHistory = new ArrayList<>();
    }

    public Note getLastNote() {
        return noteHistory.get(changeCount - 1);
    }

    public int getChangeCount() {
        return changeCount;
    }

    public int getEditCount() {
        return editCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void incrementChangeCount() {
        this.changeCount++;
    }

    public void decrementChangeCount() {
        if (changeCount > 0) {
            this.changeCount--;
        }
    }

    public void pushOldCopy(Note oldCopyOfNote) {
        noteHistory.add(oldCopyOfNote);
        incrementChangeCount();
    }

    public void popLastNote() {
        int indexOfLastNote = changeCount - 1;
        noteHistory.remove(indexOfLastNote);
        decrementChangeCount();
    }

    /**
     * Returns a copy of the Note object before it is edited.
     *
     * @param notes represents the NoteList object.
     * @param noteIndex the index of the Note object the user wants to edit.
     *
     */
    public Note getCurrCopyOfNoteToEdit(NoteList notes, int noteIndex) {
        Note note = notes.get(noteIndex);

        int noteEditNumber = note.getEditNumber();
        int editID;

        if (noteEditNumber == DEFAULT_EDIT_NUMBER) {
            editID = getEditCount() + 1;
            note.setEditNumber(editID);
        } else {
            editID = noteEditNumber;
        }
        editCount++;

        return new Note(note, editID);
    }

    /**
     * Returns a copy of the Note object before it is deleted.
     *
     * @param notes represents the NoteList object.
     * @param noteIndex the index of the Note object the user wants to delete.
     *
     */
    public Note getCurrCopyOfNoteToDelete(NoteList notes, int noteIndex) {
        Note note = notes.get(noteIndex);

        deletePrevCopiesOfThisNote(note);

        int deleteID = getDeleteCount() - 1;
        note.setEditNumber(deleteID);

        deleteCount--;

        return new Note(note, deleteID);
    }

    private void deletePrevCopiesOfThisNote(Note noteToDelete) {
        int editNumber = noteToDelete.getEditNumber();
        Iterator<Note> iterator = noteHistory.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (note.getEditNumber() == editNumber) {
                iterator.remove();
                decrementChangeCount();
            }
        }
    }
}
