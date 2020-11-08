package seedu.lifeasier.model.notes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The NoteHistory class represents the list of changes made to Note objects,
 * edits or deletions in particular.
 */
public class NoteHistory {
    private static Logger logger = Logger.getLogger(NoteHistory.class.getName());

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

    /**
     * Resets all noteHistory after archive command is called.
     */
    public void clearNoteHistory() {
        noteHistory.clear();
        changeCount = 0;
        editCount = 0;
        deleteCount = 0;
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

        int editNumOfLastNote = noteHistory.get(indexOfLastNote).getEditNumber();
        logger.log(Level.INFO, "Change editCount/deleteCount accordingly");
        if (!containsSameEditNumber(editNumOfLastNote)) {
            if (editNumOfLastNote > 0) {
                editCount--;
            } else {
                deleteCount++;
            }
        }

        logger.log(Level.INFO, "Popping the last note of noteHistory");
        noteHistory.remove(indexOfLastNote);
        decrementChangeCount();
    }

    public boolean containsSameEditNumber(int editNumOfLastTask) {
        return noteHistory.stream().anyMatch(t -> t.getEditNumber() == editNumOfLastTask);
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
            editCount++;
            editID = getEditCount();
            note.setEditNumber(editID);
        } else {
            editID = noteEditNumber;
        }

        logger.log(Level.INFO, "Make a deep copy of Task with a specific editID");
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

        logger.log(Level.INFO, "Make a deep copy of Task with a specific deleteID");
        return new Note(note, deleteID);
    }

    /**
     * Removes existing copies with the same editNumber of a particular Note
     * that was saved in the NoteHistory.
     *
     * @param noteToDelete reference Note to be deleted.
     */
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
