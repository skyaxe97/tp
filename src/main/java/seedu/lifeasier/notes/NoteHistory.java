package seedu.lifeasier.notes;

import java.util.ArrayList;

public class NoteHistory {

    private static int changeCount;
    private ArrayList<Note> noteHistory;
    private NoteList notes;

    public NoteHistory(NoteList notes) {
        this.changeCount = 0;
        this.noteHistory = new ArrayList<>();
        this.notes = notes;
    }

}
