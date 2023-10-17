package seedu.address.model.student.grades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClassParticipationTest {

    @Test
    public void mark_success() {
        ClassParticipation classParticipation = new ClassParticipation();
        classParticipation.mark();
        assertTrue(classParticipation.getParticipated());
    }

    @Test
    public void unmark_success() {
        ClassParticipation classParticipation = new ClassParticipation();
        classParticipation.unmark();
        assertFalse(classParticipation.getParticipated());
    }

    @Test
    public void equals() {
        ClassParticipation normal = new ClassParticipation();
        ClassParticipation participated = new ClassParticipation();
        participated.mark();
        ClassParticipation didNotParticipate = new ClassParticipation();
        didNotParticipate.unmark();

        // same values -> returns true
        assertTrue(normal.equals(new ClassParticipation()));

        // same object -> returns true
        assertTrue(normal.equals(normal));

        // null -> returns false
        assertFalse(normal.equals(null));

        // different values -> returns false
        assertFalse(participated.equals(didNotParticipate));

        // different values -> returns false
        assertFalse(participated.equals(normal));
    }

    @Test
    public void toStringMethod() {
        ClassParticipation classParticipation = new ClassParticipation();

        // classParticipation should return Did not Participate by default
        assertEquals("Did not Participate", classParticipation.toString());

        classParticipation.mark();
        // presentAttendance should return Participated when true
        assertEquals("Participated", classParticipation.toString());



    }
}
