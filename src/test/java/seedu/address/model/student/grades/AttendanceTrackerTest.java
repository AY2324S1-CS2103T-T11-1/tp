package seedu.address.model.student.grades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTrackerTest {

    @Test
    public void constructor_invalidNumOfTut_throwsIllegalArgumentException() {
        int invalidNumOfTut = -1;
        assertThrows(IllegalArgumentException.class, () -> new AttendanceTracker(invalidNumOfTut));
    }

    @Test
    public void isValidAttendance() {
        // invalid number of tutorials
        assertFalse(AttendanceTracker.isValidAttendance(-1)); // -negative number

        // valid number of tutorials
        assertTrue(AttendanceTracker.isValidAttendance(1));
        assertTrue(AttendanceTracker.isValidAttendance(2));
        assertTrue(AttendanceTracker.isValidAttendance(10));
    }

    @Test
    public void equals() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(13);

        // same values -> returns true
        assertTrue(attendanceTracker.equals(new AttendanceTracker(13)));

        // same object -> returns true
        assertTrue(attendanceTracker.equals(attendanceTracker));

        // null -> returns false
        assertFalse(attendanceTracker.equals(null));

        // different values -> returns false
        assertFalse(attendanceTracker.equals(new AttendanceTracker(26)));
    }

    @Test
    public void toStringMethod() {
        AttendanceTracker attendanceTracker = new AttendanceTracker(3);

        assertEquals("Attendance:\n"
                + "Tutorial 1: Absent\n"
                + "Tutorial 2: Absent\n"
                + "Tutorial 3: Absent\n", attendanceTracker.toString());
    }
}
