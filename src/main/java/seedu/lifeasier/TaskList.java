package seedu.lifeasier;

import seedu.lifeasier.parser.Parser;
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

    //addEvent EVENT_NAME /date DATE /time START /to END
    public static void addEvent (String args) {
        try {
            //parser function to return description, start, end
            String description = Parser.magicParser(args);
            LocalDateTime start = Parser.magicParser(args);
            LocalDateTime end = Parser.magicParser(args);
            Event event = new Event(args, start, end);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    //addLesson /code MODULE_CODE /date DATE /time START /to END
    public static void addLesson (String args) {
        try {
            //parser function to return description, start, end
            String description = Parser.magicParser(args);
            LocalDateTime start = Parser.magicParser(args);
            LocalDateTime end = Parser.magicParser(args);
            Lesson lesson = new Lesson(args, start, end);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    //addDeadline DEADLINE_NAME /by DATETIME
    public static void addDeadline (String args) {
        try {
            //parser function to return description and by
            String description = Parser.magicParser(args);
            LocalDateTime by = Parser.magicParser(args);
            Deadline deadline = new Deadline(args, by);
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
