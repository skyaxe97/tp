package seedu.lifeasier;
import seedu.lifeasier.commands.Command;
import seedu.notes.*;

/**
 * LifEasier is a CLI application that allows busy CEG students to schedule their day faster than traditional
 * GUI based apps if they can type fast
 */
public class LifEasier {

    private Ui ui;

    public LifEasier(String filePath) {

        ui = new Ui();
    }

    /**
     * Runs the LifEasier program infinitely until termination by the user
     */
    public void run() {
        boolean isFinished = false;
        NoteList notes = new NoteList();

        ui.showWelcomeMessage();

        while (!isFinished) {
            String fullCommand = ui.readCommand();
            Command userCommand = Parser.parseCommand(fullCommand);
            userCommand.execute(ui, notes);
            isFinished = userCommand.isFinished();
        }

        ui.showGoodbyeMessage();
    }

    /**
     * Main entry-point for the java.lifeasier.LifEasier application
     */
    public static void main(String[] args) {
        new LifEasier("saveFile.txt").run();
    }
}
