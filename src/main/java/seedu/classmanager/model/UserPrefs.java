package seedu.classmanager.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.classmanager.commons.core.GuiSettings;
import seedu.classmanager.model.student.ClassDetails;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path classManagerFilePath = Paths.get("data" , "classmanager.json");
    private boolean isConfigured = false;
    private int tutorialCount = ClassDetails.DEFAULT_COUNT;
    private int assignmentCount = ClassDetails.DEFAULT_COUNT;
    private String theme = "dark";

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setClassManagerFilePath(newUserPrefs.getClassManagerFilePath());
        setConfigured(newUserPrefs.getConfigured());
        setAssignmentCount(newUserPrefs.getAssignmentCount());
        setTutorialCount(newUserPrefs.getTutorialCount());
        setTheme(newUserPrefs.getTheme());
    }

    private void setTheme(String theme) {
        this.theme = theme;
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getClassManagerFilePath() {
        return classManagerFilePath;
    }

    public boolean getConfigured() {
        return isConfigured;
    }

    public void setConfigured(boolean isConfigured) {
        this.isConfigured = isConfigured;
    }

    public void setClassManagerFilePath(Path classManagerFilePath) {
        requireNonNull(classManagerFilePath);
        this.classManagerFilePath = classManagerFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && classManagerFilePath.equals(otherUserPrefs.classManagerFilePath)
                && isConfigured == otherUserPrefs.isConfigured;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, classManagerFilePath, isConfigured);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : ").append(guiSettings);
        sb.append("\nLocal data file location : ").append(classManagerFilePath);
        sb.append("\nModule information configured : ").append(isConfigured);
        return sb.toString();
    }

    public void setAssignmentCount(int assignmentCount) {
        this.assignmentCount = assignmentCount;
    }

    public int getAssignmentCount() {
        return assignmentCount;
    }

    public void setTutorialCount(int tutorialCount) {
        this.tutorialCount = tutorialCount;
    }

    public int getTutorialCount() {
        return tutorialCount;
    }

    public void toggleColorTheme() {
        theme = theme.equals("dark") ? "light" : "dark";
    }

    public String getTheme() {
        return theme;
    }
}