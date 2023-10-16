package seedu.address.model.student;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s detail matches any of the keywords given.
 */
public class StudentContainsKeywordsPredicate implements Predicate<Student> {

    private final Optional<List<String>> classDetails;
    private final Optional<List<String>> emails;
    private final Optional<List<String>> names;
    private final Optional<List<String>> phones;
    private final Optional<List<String>> studentNumbers;
    private final Optional<List<String>> tags;

    /**
     * Constructs a {@code StudentContainsKeywordsPredicate} with the given keywords.
     */
    public StudentContainsKeywordsPredicate(String classDetails, String emails, String names,
                                            String phones, String studentNumbers, String tags) {
        this.classDetails = Optional.ofNullable(classDetails)
                .map(s -> Arrays.asList(s.split("\\s+")));
        this.emails = Optional.ofNullable(emails)
                .map(s -> Arrays.asList(s.split("\\s+")));
        this.names = Optional.ofNullable(names)
                .map(s -> Arrays.asList(s.split("\\s+")));
        this.phones = Optional.ofNullable(phones)
                .map(s -> Arrays.asList(s.split("\\s+")));
        this.studentNumbers = Optional.ofNullable(studentNumbers)
                .map(s -> Arrays.asList(s.split("\\s+")));
        this.tags = Optional.ofNullable(tags)
                .map(s -> Arrays.asList(s.split("\\s+")));
    }

    @Override
    public boolean test(Student student) {
        boolean isClassDetailsMatch = classDetails.map(Collection::stream)
                .map(stream -> stream.anyMatch(classDetail -> StringUtil
                        .containsWordIgnoreCase(student.getClassDetails().value, classDetail)))
                .orElse(true);

        boolean isEmailMatch = emails.map(Collection::stream)
                .map(stream -> stream.anyMatch(email -> StringUtil
                        .containsWordIgnoreCase(student.getEmail().value, email)))
                .orElse(true);

        boolean isNameMatch = names.map(Collection::stream)
                .map(stream -> stream.anyMatch(name -> StringUtil
                        .containsWordIgnoreCase(student.getName().fullName, name)))
                .orElse(true);

        boolean isPhoneMatch = phones.map(Collection::stream)
                .map(stream -> stream.anyMatch(phone -> StringUtil
                        .containsWordIgnoreCase(student.getPhone().value, phone)))
                .orElse(true);

        boolean isStudentNumberMatch = studentNumbers.map(Collection::stream)
                .map(stream -> stream.anyMatch(studentNumber -> StringUtil
                        .containsWordIgnoreCase(student.getStudentNumber().value, studentNumber)))
                .orElse(true);

        boolean isTagMatch = tags.map(Collection::stream)
                .map(stream -> stream
                        .anyMatch(tag -> student.getTags().stream()
                                     .anyMatch(t -> StringUtil.containsWordIgnoreCase(t.tagName, tag))))
                .orElse(true);

        return isClassDetailsMatch
                && isEmailMatch
                && isNameMatch
                && isPhoneMatch
                && isStudentNumberMatch
                && isTagMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentContainsKeywordsPredicate)) {
            return false;
        }

        StudentContainsKeywordsPredicate otherPredicate = (StudentContainsKeywordsPredicate) other;
        return otherPredicate.classDetails.equals(classDetails)
                && otherPredicate.emails.equals(emails)
                && otherPredicate.names.equals(names)
                && otherPredicate.phones.equals(phones)
                && otherPredicate.studentNumbers.equals(studentNumbers)
                && otherPredicate.tags.equals(tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("classDetails", classDetails)
                .add("email", emails)
                .add("name", names)
                .add("phone", phones)
                .add("studentNumber", studentNumbers)
                .add("tag", tags)
                .toString();
    }
}
