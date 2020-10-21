package seedu.lifeasier.notes;

import java.util.ArrayList;

public class NoteHistory {

    private static int changeCount;
    private ArrayList<Note> notesHistory;
    private NoteList notes;

    public NoteHistory(NoteList notes) {
        this.changeCount = 0;
        this.notesHistory = new ArrayList<>();
        this.notes = notes;
    }


}
