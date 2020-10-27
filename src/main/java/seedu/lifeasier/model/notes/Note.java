package seedu.lifeasier.model.notes;

public class Note {
    private String title;
    private String description;
    protected int editNumber;

    private static final int DEFAULT_EDIT_NUMBER = -999999;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.editNumber = DEFAULT_EDIT_NUMBER;
    }

    public Note(Note note, int editNumber) {
        this.title = note.title;
        this.description = note.description;
        setEditNumber(editNumber);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "Title: " + title + "\n\n" + description + "\n";
    }

    public int getEditNumber() {
        return editNumber;
    }

    public void setEditNumber(int editNumber) {
        this.editNumber = editNumber;
    }
}
