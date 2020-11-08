package seedu.lifeasier.commands;

import seedu.lifeasier.model.notes.NoteHistory;
import seedu.lifeasier.model.notes.NoteList;
import seedu.lifeasier.model.tasks.TaskHistory;
import seedu.lifeasier.model.tasks.TaskList;
import seedu.lifeasier.parser.Parser;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.ui.ScheduleUi;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DisplayScheduleCommand extends Command {

    private String displayKeyword;
    private final ScheduleUi scheduleUi = new ScheduleUi();

    public DisplayScheduleCommand(String toDisplay) {
        this.displayKeyword = toDisplay;
    }

    @Override
    public void execute(Ui ui, NoteList notes, TaskList tasks, FileStorage storage, Parser parser,
                        NoteHistory noteHistory, TaskHistory taskHistory) {
        LocalDate currDate = LocalDate.now();
        switch (displayKeyword) {
        case "week":
            ui.printBlankLine();
            ui.printThickSeparator();
            scheduleUi.showHome(tasks, ui);
            ui.printThickSeparator();
            ui.printBlankLine();
            break;
        case "today":
            displayScheduleFor(currDate, tasks, ui);
            ui.printThickSeparator();
            ui.printBlankLine();
            break;
        case "tomorrow":
            displayScheduleFor(currDate.plus(1, ChronoUnit.DAYS), tasks, ui);
            ui.printThickSeparator();
            ui.printBlankLine();
            break;
        default:
            ui.showInvalidDisplayKeywordError();
        }
    }

    private void displayScheduleFor(LocalDate date, TaskList tasks, Ui ui) {
        int taskCountForThisDate = scheduleUi.getTaskCountForDay(tasks, date);

        if (taskCountForThisDate > 0) {
            ui.showDayScheduleMessage(displayKeyword);
            scheduleUi.displayDaySchedule(date, tasks);
        } else {
            ui.showNothingScheduledMessage(displayKeyword);
        }
        ui.printThickSeparator();
        ui.printBlankLine();
    }
}
