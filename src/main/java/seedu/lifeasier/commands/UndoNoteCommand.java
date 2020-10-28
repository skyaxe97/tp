package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.Note;
import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.Ui;

public class UndoNoteCommand extends Command {
    private static final int DEFAULT_EDIT_NUMBER = -999999;

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        try {
            Note oldCopyOfNote = noteHistory.getLastNote();
            int lastNoteEditNumber = oldCopyOfNote.getEditNumber();

            if (lastNoteEditNumber > 0) {
                for (int i = 0; i < notes.size(); i++) {
                    int editNumOfCurrNote =  notes.get(i).getEditNumber();
                    if (editNumOfCurrNote == lastNoteEditNumber) {
                        notes.setNote(i, oldCopyOfNote);
                    }
                }
                ui.showUndoNoteEditMessage();

            } else if (lastNoteEditNumber < 0) {
                Note noteToRestore = new Note(oldCopyOfNote, DEFAULT_EDIT_NUMBER);
                notes.add(noteToRestore);
                ui.showUndoNoteDeleteMessage();
            }

            ui.showOldNote(noteHistory);
            noteHistory.popLastNote();
            storage.saveNote();

        } catch (IndexOutOfBoundsException e) {
            ui.showInvalidUndoAction();
        }
        ui.printSeparator();
    }
}
