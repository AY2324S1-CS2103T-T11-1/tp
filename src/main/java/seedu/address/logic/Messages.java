package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The student index provided is invalid";
    public static final String MESSAGE_INVALID_STUDENT_NUMBER = "The student number provided is invalid";
    public static final String MESSAGE_NONEXISTENT_STUDENT_NUMBER = "The student number provided does not exist here.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "Lookup successful!\n"
            + "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_STUDENT_DOES_NOT_EXIST = "There is no student with the given student number.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append(";\n Phone: ")
                .append(student.getPhone())
                .append(";\n Email: ")
                .append(student.getEmail())
                .append(";\n Student Number: ")
                .append(student.getStudentNumber())
                .append(";\n Class Number: ")
                .append(student.getClassDetails())
                .append(";\n Tags: ");
        student.getTags().forEach(builder::append);
        return builder.toString();
    }

}