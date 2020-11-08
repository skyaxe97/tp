package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UndoNoteCommand extends Command {
    private static Logger logger = Logger.getLogger(UndoNoteCommand.class.getName());

    private static final int DEFAULT_EDIT_NUMBER = -999999;

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        if (noteHistory.getChangeCount() > 0) {
            Note oldCopyOfNote = noteHistory.getLastNote();
            int lastNoteEditNumber = oldCopyOfNote.getEditNumber();

            if (lastNoteEditNumber > 0) {
                for (int i = 0; i < notes.size(); i++) {
                    logger.log(Level.INFO, "Iterating through NoteHistory...");
                    int editNumOfCurrNote =  notes.get(i).getEditNumber();
                    if (editNumOfCurrNote == lastNoteEditNumber) {
                        logger.log(Level.INFO, "Revert Note to its previous copy");
                        notes.setNote(i, oldCopyOfNote);
                    }
                }
                ui.showUndoNoteEditMessage();

            } else if (lastNoteEditNumber < 0) {
                logger.log(Level.INFO, "Start restoring a deleted Note");
                Note noteToRestore = new Note(oldCopyOfNote, DEFAULT_EDIT_NUMBER);
                notes.add(noteToRestore);
                ui.showUndoNoteDeleteMessage();
            }

            ui.showOldNoteMessage(noteHistory);
            noteHistory.popLastNote();
            storage.saveNote();

        } else {
            ui.showInvalidUndoActionError();
        }
    }
}
