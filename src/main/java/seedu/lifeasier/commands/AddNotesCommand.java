package seedu.lifeasier.commands;

public class AddNotesCommand extends Command{

    private String title;

    public AddNotesCommand(String title) {
        this.title = title;
    }
}
