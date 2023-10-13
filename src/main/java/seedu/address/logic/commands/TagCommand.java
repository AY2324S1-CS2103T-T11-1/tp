package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_STUDENT_DOES_NOT_EXIST;

import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.tag.Tag;

/**
 * Changes the tags of an existing student in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String ADD_TAGS = "add";
    public static final String DELETE_TAGS = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the tags of the student identified by the student number.\n"
            + "Existing tags will be overwritten by the input.\n"
            + "Use /add or /delete to add/delete tags without overwriting all tags.\n"
            + "Parameters: Student number (must be exist in address book) [/add] [/delete] t/[TAG]\n"
            + "Example: " + COMMAND_WORD + " A1234567N /add t/smart.";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added following tags to Student %1$s:\n";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed following tags from Student %1$s:\n";
    public static final String MESSAGE_REPLACE_ALL_TAG_SUCCESS = "Replace all tags of Student %1$s with:\n";
    public static final String MESSAGE_DELETE_ALL_TAG_SUCCESS = "Removed all tags from Student %1$s:\n";
    public static final String MESSAGE_TAG_FAILED = "There was an issue tagging the student.\n"
        + "Please check that the student with the student number exists or each tag has the “t/” prefix.\n";
    protected final StudentNumber studentNumber;
    protected final Set<Tag> tags;

    /**
     * @param studentNumber of the student in the filtered student list to edit their tags
     * @param tags of the student to be updated to
     */
    public TagCommand(StudentNumber studentNumber, Set<Tag> tags) {
        requireAllNonNull(studentNumber, tags);

        this.studentNumber = studentNumber;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        Person studentToTag;
        try {
            studentToTag = getStudentByStudentNumber(lastShownList, studentNumber);
        } catch (NullPointerException ive) {
            throw new CommandException(MESSAGE_STUDENT_DOES_NOT_EXIST);
        }

        Person editedStudent = new Person(
                studentToTag.getName(), studentToTag.getPhone(), studentToTag.getEmail(),
                studentToTag.getStudentNumber(), studentToTag.getClassNumber(), this.tags);

        model.setPerson(studentToTag, editedStudent);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedStudent));
    }

    /**
     * Generates a command execution success message based on whether
     * the tag is added to or removed from
     * {@code studentToTag}.
     */
    private String generateSuccessMessage(Person studentToTag) {
        String message = !tags.isEmpty() ? MESSAGE_REPLACE_ALL_TAG_SUCCESS : MESSAGE_DELETE_ALL_TAG_SUCCESS;
        return String.format(message, studentToTag.getName()) + studentToTag.getTags();
    }

    protected Person getStudentByStudentNumber(List<Person> list, StudentNumber studentNumber) {
        return list.stream()
            .filter(student -> student.getStudentNumber().equals(studentNumber))
            .findAny().orElseThrow(NullPointerException::new);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand e = (TagCommand) other;
        return studentNumber.equals(e.studentNumber)
                && tags.equals(e.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
