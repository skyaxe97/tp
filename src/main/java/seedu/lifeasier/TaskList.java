package seedu.lifeasier;

import seedu.tasks.Deadline;
import seedu.tasks.Event;
import seedu.tasks.Lesson;
import seedu.tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    protected static ArrayList<Task> taskList;
    protected static int taskCount = 0;
    private static final Ui ui = new Ui();

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public static void addNewTask (Task task) {
        taskList.add(task);
    }

    public static void addNewEvent (String args) {
        try {
            //parser function to return description, dateTime and duration as string array
            String description = Parser.parseEventAndLessonCommand(args)[0];
            String dateTimeString = Parser.parseEventAndLessonCommand(args)[1];
            LocalDateTime dateTime = Parser.getDateTime(dateTimeString);
            String duration = Parser.parseEventAndLessonCommand(args)[2];
            Event event = new Event(args, dateTime, duration);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    public static void addNewLesson (String args) {
        try {
            //parser function to return description, dateTime and duration as string array
            String description = Parser.parseEventAndLessonCommand(args)[0];
            String dateTimeString = Parser.parseEventAndLessonCommand(args)[1];
            LocalDateTime dateTime = Parser.getDateTime(dateTimeString);
            String duration = Parser.parseEventAndLessonCommand(args)[2];
            Lesson lesson = new Lesson(args, dateTime, duration);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    public static void addNewDeadline (String args) {
        try {
            //parser function to return description and dateTime as string array
            String description = Parser.parseDeadlineCommand(args)[0];
            String dateTimeString = Parser.parseDeadlineCommand(args)[1];
            LocalDateTime dateTime = Parser.getDateTime(dateTimeString);
            Deadline deadline = new Deadline(args, dateTime);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    public static void deleteItem(String index) {
        //delete
    }

    public static void markAsDone (String index) {
        int taskID = Integer.parseInt(index) - 1;
        try {
            taskList.get(taskID).markAsDone();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }
}
