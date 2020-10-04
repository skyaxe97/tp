package seedu.lifeasier;
import seedu.lifeasier.commands.Command;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.parser.ParserException;
import seedu.notes.*;

/**
 * LifEasier is a CLI application that allows busy CEG students to schedule their day faster than traditional
 * GUI based apps if they can type fast
 */
public class LifEasier {

    private Ui ui;
    private Parser parser;

    public LifEasier(String filePath) {

        ui = new Ui();
        parser = new Parser();
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
            try {

                Command userCommand = parser.parseCommand(fullCommand);
                userCommand.execute(ui, notes);
                isFinished = userCommand.isFinished();

            } catch (ParserException e) {
                System.out.println("There's been an error understanding your input! Please double check your input!");
            }

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
