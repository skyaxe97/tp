package seedu.lifeasier.notes;

import java.util.ArrayList;


public class NoteList {
    private final ArrayList<Note> notes;

    public NoteList(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public NoteList() {
        this.notes = new ArrayList<>();
    }

    public Note get(int index) {
        return notes.get(index);
    }

    public void add(Note note) {
        notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public int size() {
        return notes.size();
    }

    public void remove(int index) {
        notes.remove(index);
    }
}
