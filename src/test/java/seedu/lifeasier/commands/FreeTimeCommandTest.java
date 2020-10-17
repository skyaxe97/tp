package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.notes.NoteList;
import seedu.lifeasier.storage.FileStorage;
import seedu.lifeasier.tasks.Event;
import seedu.lifeasier.tasks.Task;
import seedu.lifeasier.tasks.TaskList;
import seedu.lifeasier.ui.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FreeTimeCommandTest {

    @Test
    void isFreeTime_busyHour_false() {

        FreeTimeCommand command = new FreeTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some very long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 11, 10);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 12, 59);
        tasks.add(new Event(description, start, end));

        boolean isFreeTime = command.isFreeTime(11, tasks);

        assertFalse(isFreeTime);
    }

    @Test
    void isFreeTime_freeHour_true() {

        FreeTimeCommand command = new FreeTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();

        boolean isFreeTime = command.isFreeTime(11, tasks);

        assertTrue(isFreeTime);

    }

    @Test
    void getLongestFreeTime_busyDay_twoHoursFree() {

        FreeTimeCommand command = new FreeTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description1 = "Some very long activity";
        LocalDateTime start1 = LocalDateTime.of(2020, 10, 17, 11,0);
        LocalDateTime end1 = LocalDateTime.of(2020, 10, 17, 17, 0);
        tasks.add(new Event(description1, start1, end1));
        String description2 = "Another very long activity";
        LocalDateTime start2 = LocalDateTime.of(2020, 10, 17, 18, 0);
        LocalDateTime end2 = LocalDateTime.of(2020, 10, 17, 23, );
        tasks.add(new Event(description2, start2, end2));

        int[] longestFreeTime = command.getLongestFreeTime(tasks);

        assertEquals(4, (longestFreeTime[1] - longestFreeTime[0]));
    }


}