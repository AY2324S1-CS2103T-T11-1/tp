package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.grades.exceptions.InvalidTutorialIndexException;

/**
 * Marks a student's attendance.
 */
public class MarkPresentCommand extends Command {
    public static final String COMMAND_WORD = "mark-pre";
    public static final String MESSAGE_MARK_SUCCESS = "Successfully mark the student as present.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a student as present.\n"
            + "Parameters: INDEX "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STUDENT_NUMBER + "A0245234A";
    private final Index index;
    private final StudentNumber targetStudentNumber;

    /**
     * Constructs a MarkPresentCommand object.
     *
     * @param index of the class.
     * @param targetStudentNumber of the student.
     */
    public MarkPresentCommand(Index index, StudentNumber targetStudentNumber) {
        requireAllNonNull(index, targetStudentNumber);

        this.index = index;
        this.targetStudentNumber = targetStudentNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(targetStudentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student student = model.getStudent(targetStudentNumber);

        try {
            model.setStudent(student, student.markPresent(this.index));
        } catch (InvalidTutorialIndexException e) {
            throw new CommandException(e.getMessage());
        }

        if (model.isSelectedStudent(student)) {
            model.setSelectedStudent(student);
        }

        model.commitAddressBook();

        return new CommandResult(MESSAGE_MARK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkPresentCommand)) {
            return false;
        }

        MarkPresentCommand e = (MarkPresentCommand) other;
        return index.equals(e.index)
                && targetStudentNumber.equals(e.targetStudentNumber);
    }
}