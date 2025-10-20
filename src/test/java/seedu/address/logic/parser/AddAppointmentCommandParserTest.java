package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_FLU_SHOT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_FLU_SHOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_FLU_SHOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_FLU_SHOT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.testutil.AppointmentBuilder;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder()
            .withTitle(VALID_TITLE_DENTAL)
            .withDateTime(VALID_DATETIME_DENTAL)
            .build();

        assertParseSuccess(parser, "1" + TITLE_DESC_DENTAL + DATETIME_DESC_DENTAL,
                new AddAppointmentCommand(INDEX_FIRST_PATIENT, expectedAppointment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // Invalid index
        assertParseFailure(parser,
                "a" + TITLE_DESC_FLU_SHOT + DATETIME_DESC_FLU_SHOT,
                expectedMessage);

        // Missing Title prefix
        assertParseFailure(parser,
                "1" + VALID_TITLE_FLU_SHOT + DATETIME_DESC_FLU_SHOT,
                expectedMessage);

        // Missing Datetime prefix
        assertParseFailure(parser,
                "1" + TITLE_DESC_FLU_SHOT + VALID_DATETIME_FLU_SHOT,
                expectedMessage);

        // All prefixes missing
        assertParseFailure(parser,
                "1" + VALID_TITLE_FLU_SHOT + VALID_DATETIME_FLU_SHOT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Title
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + DATETIME_DESC_DENTAL,
                Title.MESSAGE_CONSTRAINTS);

        // Invalid DateTime
        assertParseFailure(parser, "1" + TITLE_DESC_DENTAL + INVALID_DATETIME_DESC,
                Appointment.DATETIME_MESSAGE_CONSTRAINTS);

        // Multiple invalids - only first invalid reported
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_DATETIME_DESC,
                Title.MESSAGE_CONSTRAINTS);
    }
}
