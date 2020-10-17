package seedu.lifeasier.commands;

import org.junit.jupiter.api.Test;
import seedu.lifeasier.tasks.Event;
import seedu.lifeasier.tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SleepTimeCommandTest {

    @Test
    void isBusyTime_busyHour_true() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 10, 10);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 12, 59);
        tasks.add(new Event(description, start, end));

        boolean isBusyTime = command.isBusyTime(11, tasks);

        assertTrue(isBusyTime);
    }

    @Test
    void isBusyTime_freeHour_false() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 11, 10);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 12, 59);
        tasks.add(new Event(description, start, end));

        boolean isBusyTime = command.isBusyTime(22, tasks);

        assertFalse(isBusyTime);
    }

    @Test
    void getLatestBusyTime_busyAllDay_22() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some super long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 13, 0);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 23, 0);
        tasks.add(new Event(description, start, end));

        int latestBusyTime = command.getLatestBusyTime(tasks);

        assertEquals(22, latestBusyTime);
    }

    @Test
    void getLatestBusyTime_noTasks_minus1() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();

        int latestBusyTime = command.getLatestBusyTime(tasks);

        assertEquals(-1, latestBusyTime);
    }

    @Test
    void getEarliestBusyTime_notBusy_18() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String description = "Some super long activity";
        LocalDateTime start = LocalDateTime.of(2020, 10, 17, 18, 0);
        LocalDateTime end = LocalDateTime.of(2020, 10, 17, 20, 0);
        tasks.add(new Event(description, start, end));

        int earliestBusyTime = command.getEarliestBusyTime(tasks);

        assertEquals(18, earliestBusyTime);
    }

    @Test
    void getEarliestBusyTime_noTasks_25() {
        SleepTimeCommand command = new SleepTimeCommand();
        ArrayList<Task> tasks = new ArrayList<>();

        int earliestBusyTime = command.getEarliestBusyTime(tasks);

        assertEquals(25, earliestBusyTime);
    }

}