package seedu.lifeasier.commands;
import seedu.lifeasier.Ui;
import seedu.notes.*;


/**
 * The Command class will handle all the commands input from the user
 */
public class Command {

    private String commandType;
    protected boolean isTerminated;

    public Command() {
        this.commandType = null;
        this.isTerminated = false;
    }

    public Command(String commandType) {
        this.commandType = commandType;
        this.isTerminated = false;
    }

    /**
     * Executes the user command
     */
    public void execute(Ui ui, NoteList notes) {
        switch (commandType) {
        case "showNotes":
            System.out.println("Please select the notes you want to view:\n");
            for (int i=0 ; i<notes.size(); i++) {
                System.out.println(i+1 + ". " + notes.get(i).getTitle() + "\n");
            }
            int noteNumber = Integer.parseInt(ui.readCommand());
            System.out.println(notes.get(noteNumber-1).toString());
            break;
        case "addNotes":
            ui.showNoteTitleMessage();
            String noteTitle = ui.readCommand();
            ui.showNoteDescriptionMessage();
            String noteDescription = ui.readCommand();
            notes.add(new Note(noteTitle,noteDescription));
            System.out.println("Ok! I've taken note of this note!\n");
            break;
        case "exit":
            setExit();
            break;
        default:
            ui.showInvalidCommandError();
            break;
        }
    }

    public void setExit() {
        isTerminated = true;
    }

    /**
     * Returns status of whether the program has been terminated
     */
    public boolean isFinished() {
        return isTerminated;
    }
}
