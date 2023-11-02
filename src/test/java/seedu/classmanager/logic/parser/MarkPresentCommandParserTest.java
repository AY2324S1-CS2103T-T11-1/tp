package seedu.classmanager.logic.parser;

import static seedu.classmanager.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.classmanager.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_TUTORIAL;
import static seedu.classmanager.logic.commands.CommandTestUtil.TEST_TUTORIAL_DESC;
import static seedu.classmanager.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.classmanager.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.classmanager.commons.core.index.Index;
import seedu.classmanager.logic.commands.MarkPresentCommand;
import seedu.classmanager.model.student.StudentNumber;

/**
 * Tests MarkPresentCommandParser.
 */
public class MarkPresentCommandParserTest {
    private MarkPresentCommandParser parser = new MarkPresentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        assertParseSuccess(parser, STUDENT_NUMBER_DESC_BOB + TEST_TUTORIAL_DESC,
                new MarkPresentCommand(Index.fromOneBased(TEST_TUTORIAL), new StudentNumber(VALID_STUDENT_NUMBER_BOB)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, STUDENT_NUMBER_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
        assertParseFailure(parser, TEST_TUTORIAL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentNumber_throwsParseException() {
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + TEST_TUTORIAL_DESC, StudentNumber.MESSAGE_CONSTRAINTS);
    }
}