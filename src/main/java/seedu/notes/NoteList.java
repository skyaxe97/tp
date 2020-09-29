package seedu.notes;

import java.util.ArrayList;

public class NoteList {
    private ArrayList<Note> notes;

    public NoteList(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public NoteList() {
        this.notes = new ArrayList<>();
    }


    public Note get(int index) {
        return notes.get(index);
    }

    public void addNotes(Note note) {
        notes.add(note);
    }

}
