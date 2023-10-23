package seedu.address.model.student.grades;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;

/**
 * Represents a Student's class participation grades in the class manager.
 * Guarantees: is valid as declared in {@link #isValidClassPart(int)}
 */
public class ClassParticipationTracker implements Tracker {

    public static final String MESSAGE_CONSTRAINTS = "Class Participation needs to be a positive integer";

    private ClassParticipation[] classPartList;

    /**
     * Constructs an {@code ClassParticipationTracker}.
     *
     * @param numOfTut A valid integer for the number of tutorials in a class.
     *
     */
    public ClassParticipationTracker(int numOfTut) {
        checkArgument(isValidClassPart(numOfTut), MESSAGE_CONSTRAINTS);
        classPartList = new ClassParticipation[numOfTut];
        IntStream.range(0, numOfTut).forEach(i -> classPartList[i] = new ClassParticipation());
    }

    /**
     * Constructs an {@code ClassParticipationTracker}. With a given class participation tracker list.
     *
     * @param classParticipationTracker A list of booleans representing the class participation.
     */
    public ClassParticipationTracker(List<Boolean> classParticipationTracker) {
        requireNonNull(classParticipationTracker);
        classPartList = new ClassParticipation[classParticipationTracker.size()];
        IntStream.range(0, classParticipationTracker.size())
                .forEach(i -> classPartList[i] = new ClassParticipation(classParticipationTracker.get(i)));
    }

    /**
     * Returns true if a given int is a valid number of tutorials.
     */
    public static boolean isValidClassPart(int numOfTut) {
        return numOfTut >= 0;
    }

    /**
     * Marks the class participation of a student.
     *
     * @param tutNum The tutorial number.
     */
    public void markParticipated(Index tutNum) {
        classPartList[tutNum.getZeroBased()].mark();
    }

    /**
     * Unmarks the class participation of a student.
     *
     * @param tutNum The tutorial number.
     */
    public void markDidNotParticipate(Index tutNum) {
        classPartList[tutNum.getZeroBased()].unmark();
    }

    /**
     * Marks the class participation of a student.
     *
     * @param tutNum The tutorial number.
     * @param participated Whether the student participated.
     */
    public void markParticipation(Index tutNum, boolean participated) {
        if (participated) {
            markParticipated(tutNum);
        } else {
            markDidNotParticipate(tutNum);
        }
    }

    /**
     * Returns a Json friendly version of the classParticipationTracker.
     */
    public List<Boolean> getJson() {
        List<Boolean> classParticipationTracker = new ArrayList<>();
        for (ClassParticipation classParticipation : classPartList) {
            classParticipationTracker.add(classParticipation.getParticipated());
        }
        return classParticipationTracker;
    }

    /**
     * Returns the percentage of class participation.
     *
     * @return Percentage of class participation.
     */
    public double getPercentage() {
        // Case when there are no tutorials
        if (classPartList.length == 0) {
            return 100;
        }
        int score = 0;
        int totalScore = 0;
        for (int i = 0; i < classPartList.length; i++) {
            if (classPartList[i] != null) {
                totalScore += 1;
                if (classPartList[i].getParticipated()) {
                    score += 1;
                }
            }
        }
        return (double) score / totalScore * 100;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Class Part Tracker:\n");
        for (int i = 0; i < classPartList.length; i++) {
            ret.append("Tutorial ").append(i + 1).append(": ").append(classPartList[i].toString()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassParticipationTracker)) {
            return false;
        }

        ClassParticipationTracker otherClassParticipationTracker = (ClassParticipationTracker) other;
        return Arrays.equals(classPartList, otherClassParticipationTracker.classPartList);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(classPartList);
    }

    /**
     * Updates the length of the class participation tracker. Whenever the tutorial count changes.
     */
    public void updateTutorialCountChange(int tutorialCount) {
        if (tutorialCount == classPartList.length) {
            return;
        }
        ClassParticipation[] newClassPartList = new ClassParticipation[tutorialCount];
        for (int i = 0; i < tutorialCount; i++) {
            if (i < classPartList.length) {
                newClassPartList[i] = classPartList[i];
            } else {
                newClassPartList[i] = new ClassParticipation();
            }
        }
        classPartList = newClassPartList;
    }
}
