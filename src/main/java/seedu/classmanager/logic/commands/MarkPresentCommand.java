package seedu.classmanager.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.classmanager.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.classmanager.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.classmanager.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.CommandHistory;
import seedu.classmanager.logic.Messages;
import seedu.classmanager.logic.commands.exceptions.CommandException;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.student.Student;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Marks a student's attendance.
 */
public class MarkPresentCommand extends Command {
    public static final String COMMAND_WORD = "present";
    public static final String MESSAGE_MARK_SUCCESS = "Successfully mark the student as present.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a student as present.\n"
            + "Parameters: "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_NUMBER + "A0245234A "
            + PREFIX_TUTORIAL_INDEX + "1";
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
    public CommandResult execute(Model model, CommandHistory commandHistory)
            throws CommandException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(targetStudentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student studentToMark = model.getStudent(targetStudentNumber);
        Student markedStudent = studentToMark.copy();

        try {
            markedStudent.markPresent(this.index);
            model.setStudent(studentToMark, markedStudent);
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.setSelectedStudent(markedStudent);
        model.commitClassManager();

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