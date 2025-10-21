package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAppointmentCommand;

public class DeleteAppointmentCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, "1",
                new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT));
    }

    @Test
    public void parse_invalidValue_failure() {
        // empty string
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // non-integer
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);

        // two valid index
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // one valid, one invalid, vice versa
        assertParseFailure(parser, "1 a", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "a 1", MESSAGE_INVALID_FORMAT);

    }
}
