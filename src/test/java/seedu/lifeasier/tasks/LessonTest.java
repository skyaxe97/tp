package seedu.lifeasier.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LessonTest {
    
    @Test
    public void moveAndUpdateRecurrences_pastDate_movedOneWeek() {
        String moduleCode = "cg1111";
        LocalDateTime start = LocalDateTime.of(20, 10, 22, 12, 00);
        LocalDateTime end = LocalDateTime.of(20, 10, 22, 13, 00);
        int recurrences = 11;
        
        Lesson lesson = new Lesson(moduleCode, start, end, recurrences);
        lesson.moveAndUpdateRecurrences();
        
    }

}