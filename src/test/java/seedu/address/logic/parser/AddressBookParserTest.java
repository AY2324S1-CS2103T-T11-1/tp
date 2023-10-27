package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CLASS_MANAGER_ALREADY_CONFIGURED;
import static seedu.address.logic.Messages.MESSAGE_CLASS_MANAGER_NOT_CONFIGURED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_COUNT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.logic.commands.ConfigCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.LookupCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.RecordClassPartCommand;
import seedu.address.logic.commands.SetGradeCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.ClassDetails;
import seedu.address.model.student.Comment;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentContainsKeywordsPredicate;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;
import seedu.address.testutil.TypicalStudents;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student), true);
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, true) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", true) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Student student = new StudentBuilder().build();
        DeleteCommand command = (DeleteCommand) parser.parseCommand(StudentUtil.getDeleteCommand(student), true);
        assertEquals(new DeleteCommand(student.getStudentNumber()), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " "
                + StudentUtil.getEditStudentDescriptorDetails(descriptor), true);
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_tag() throws Exception {
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD
            + " "
            + TypicalStudents.ALICE.getStudentNumber()
            + " "
            + StudentUtil.getTagDetails(TypicalStudents.ALICE), true);
        assertEquals(new TagCommand(TypicalStudents.ALICE.getStudentNumber(), TypicalStudents.ALICE.getTags()),
            command);
    }

    @Test
    public void parseCommand_mark() throws Exception {
        int tut = ClassDetails.DEFAULT_COUNT;
        Student student = new StudentBuilder().build();
        MarkCommand command = (MarkCommand) parser.parseCommand(MarkCommand.COMMAND_WORD + " "
                + tut + " " + PREFIX_STUDENT_NUMBER + student.getStudentNumber(),
                true);
        assertEquals(new MarkCommand(Index.fromOneBased(tut), student.getStudentNumber()), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, true) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", true) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_lookup() throws Exception {
        LookupCommand command = (LookupCommand) parser.parseCommand(
                LookupCommand.COMMAND_WORD + " c/t11 n/alice", true);
        assertEquals(new LookupCommand(new StudentContainsKeywordsPredicate("t11", null,
                "alice", null, null, null)), command);
    }

    @Test
    public void parseCommand_setGrade() throws Exception {
        SetGradeCommand command = (SetGradeCommand) parser.parseCommand(SetGradeCommand.COMMAND_WORD
                + STUDENT_NUMBER_DESC_AMY + SetGradeCommandParserTest.VALID_ASSIGNMENT_DESC
                + SetGradeCommandParserTest.VALID_GRADE_DESC, true);
        assertEquals(new SetGradeCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Integer.parseInt(SetGradeCommandParserTest.VALID_ASSIGNMENT),
                Integer.parseInt(SetGradeCommandParserTest.VALID_GRADE)), command);
    }

    @Test
    public void parseCommand_recordClassPart() throws Exception {
        RecordClassPartCommand command = (RecordClassPartCommand) parser.parseCommand(
                RecordClassPartCommand.COMMAND_WORD
                + STUDENT_NUMBER_DESC_AMY + RecordClassPartCommandParserTest.VALID_TUT_DESC
                + RecordClassPartCommandParserTest.VALID_PARTICIPATION_DESC, true);
        assertEquals(new RecordClassPartCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
                Integer.parseInt(RecordClassPartCommandParserTest.VALID_TUT),
                true), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, true) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", true) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, true) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", true) instanceof ListCommand);
    }

    @Test
    public void parseCommand_load() throws Exception {
        LoadCommand command = (LoadCommand) parser.parseCommand(
                LoadCommand.COMMAND_WORD + " " + PREFIX_FILE + "export-v1", true);
        Path path = Paths.get("data", "export-v1.json");
        assertEquals(new LoadCommand("export-v1", path), command);
    }

    @Test
    public void parseCommand_config_throwsParseException() throws Exception {
        assertThrows(ParseException.class,
                MESSAGE_CLASS_MANAGER_ALREADY_CONFIGURED, () -> parser.parseCommand("config", true));
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + VALID_STUDENT_NUMBER_AMY, true);
        assertEquals(new ViewCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY)), command);
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), ()
                        -> parser.parseCommand(ViewCommand.COMMAND_WORD
                        + " " + INVALID_STUDENT_NUMBER, true));
    }

    @Test
    public void parseCommand_comment() throws Exception {
        Student student = new StudentBuilder().build();
        Comment comment = new Comment("Struggling with tutorials.");
        CommentCommand command = (CommentCommand) parser.parseCommand(CommentCommand.COMMAND_WORD
                + " " + PREFIX_STUDENT_NUMBER + student.getStudentNumber()
                + " " + PREFIX_COMMENT + comment, true);
        assertEquals(new CommentCommand(student.getStudentNumber(), comment), command);
    }

    @Test
    public void parseCommand_theme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD, true) instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " 3", true) instanceof ThemeCommand);
    }

    @Test
    public void parseCommandNotConfigured_config() throws Exception {
        ConfigCommand command = (ConfigCommand) parser.parseCommand(ConfigCommand.COMMAND_WORD
                + " " + PREFIX_TUTORIAL_COUNT + "5"
                + " " + PREFIX_ASSIGNMENT_COUNT + 2, false);
        assertEquals(new ConfigCommand(5, 2), command);
    }

    @Test
    public void parseCommandNotConfigured_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, false) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", false) instanceof HelpCommand);
    }

    @Test
    public void parseCommandNotConfigured_theme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD, false) instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " 3", false) instanceof ThemeCommand);
    }

    @Test
    public void parseCommandNotConfigured_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, false) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", false) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                        -> parser.parseCommand("", true));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", true));
    }

    @Test
    public void parseCommandNotConfigured_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_CLASS_MANAGER_NOT_CONFIGURED, () -> parser.parseCommand("unknownCommand", false));
    }
}
