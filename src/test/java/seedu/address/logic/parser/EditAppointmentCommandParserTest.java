package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_DENTAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandParserTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy, HHmm");
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE);

    private EditAppointmentCommandParser parser = new EditAppointmentCommandParser();


    @Test
    public void parse_allFields_success() {
        // all fields present
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().withTitle(VALID_TITLE_DENTAL)
                        .withDateTime(VALID_DATETIME_DENTAL).build();
        assertParseSuccess(parser, NRIC_DESC_AMY + INDEX_DESC_FIRST + TITLE_DESC_DENTAL + DATETIME_DESC_DENTAL,
                new EditAppointmentCommand(VALID_NRIC_AMY,
                        INDEX_FIRST_APPOINTMENT,
                        descriptor));
    }

    @Test
    public void parse_noDescriptor_fail() {
        // no descriptor
        assertParseFailure(parser, NRIC_DESC_AMY + INDEX_DESC_FIRST, EditAppointmentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_partDescriptor_success() {
        // no dateTime
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withTitle(VALID_TITLE_DENTAL).build();
        assertParseSuccess(parser, NRIC_DESC_AMY + INDEX_DESC_FIRST + TITLE_DESC_DENTAL,
                new EditAppointmentCommand(VALID_NRIC_AMY, INDEX_FIRST_APPOINTMENT, descriptor));

        // no title
        EditAppointmentDescriptor descriptor2 = new EditAppointmentDescriptorBuilder()
                .withDateTime(VALID_DATETIME_DENTAL).build();
        assertParseSuccess(parser, NRIC_DESC_AMY + INDEX_DESC_FIRST + DATETIME_DESC_DENTAL,
                new EditAppointmentCommand(VALID_NRIC_AMY, INDEX_FIRST_APPOINTMENT, descriptor2));
    }

    @Test
    public void parse_missingParts_fail() {
        // no fields specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // missing index
        assertParseFailure(parser, NRIC_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // missing nric
        assertParseFailure(parser, INDEX_DESC_FIRST, MESSAGE_INVALID_FORMAT);
    }

}
