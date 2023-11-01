package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.ClassDetails;
import seedu.address.model.student.Student;
import seedu.address.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MarkPresentAllCommand.
 */
public class MarkPresentAllCommandTest {

    private final Model model = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() throws IllegalValueException, CommandException {
        Index i = Index.fromOneBased(ClassDetails.DEFAULT_COUNT);
        Student selectedStudent = TypicalStudents.getTypicalStudents().get(0);
        model.setSelectedStudent(selectedStudent);
        MarkPresentAllCommand markPresentAllCommand = new MarkPresentAllCommand(i);

        String expectedMessage = MarkPresentAllCommand.MESSAGE_MARK_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Student s : TypicalStudents.getTypicalStudents()) {
            Student markedStudent = s.copy();
            markedStudent.markPresent(i);
            expectedModel.setStudent(s, markedStudent);
        }
        expectedModel.commitAddressBook();

        assertCommandSuccess(markPresentAllCommand, model, expectedMessage, expectedModel, commandHistory);
        assertEquals(selectedStudent, model.getSelectedStudent().get(0));
    }

    @Test
    public void execute_invalidTutorialIndex_throwsCommandException() {
        Index i = Index.fromZeroBased(ClassDetails.DEFAULT_COUNT + 1);

        MarkPresentAllCommand markPresentAllCommand = new MarkPresentAllCommand(i);

        assertCommandFailure(
                markPresentAllCommand, model,
                String.format(ClassDetails.MESSAGE_INVALID_TUTORIAL_SESSION_NUMBER, ClassDetails.DEFAULT_COUNT),
                commandHistory);
    }

    @Test
    public void equals() {
        MarkPresentAllCommand markForFirstTutorial = new MarkPresentAllCommand(Index.fromOneBased(1));
        MarkPresentAllCommand markForSecondTutorial = new MarkPresentAllCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(markForFirstTutorial.equals(markForFirstTutorial));

        // null -> returns false
        assertFalse(markForFirstTutorial.equals(null));

        // different types -> returns false
        assertFalse(markForFirstTutorial.equals(1));

        // different tutorial index -> returns false
        assertFalse(markForFirstTutorial.equals(markForSecondTutorial));
    }

}
