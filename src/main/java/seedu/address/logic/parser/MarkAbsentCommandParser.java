package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAbsentCommand;
import seedu.address.logic.commands.MarkPresentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new MarkAbsentCommand object
 */
public class MarkAbsentCommandParser implements Parser<MarkAbsentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAbsentCommand
     * and returns a MarkAbsentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAbsentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT_NUMBER);

        if (!argMultimap.arePrefixesPresent(PREFIX_STUDENT_NUMBER) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPresentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_NUMBER);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        StudentNumber studentNumber = ParserUtil.parseStudentNumber(argMultimap.getValue(PREFIX_STUDENT_NUMBER).get());

        return new MarkAbsentCommand(index, studentNumber);
    }
}
