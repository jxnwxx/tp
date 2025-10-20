package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_DENTAL;
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
        assertParseSuccess(parser, "1" + TITLE_DESC_DENTAL + DATETIME_DESC_DENTAL,
                new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor));
    }

    @Test
    public void parse_noDescriptor_fail() {
        // no descriptor
        assertParseFailure(parser, "1", EditAppointmentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_partDescriptor_success() {
        // no dateTime
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withTitle(VALID_TITLE_DENTAL).build();
        assertParseSuccess(parser, "1" + TITLE_DESC_DENTAL,
                new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor));

        // no title
        EditAppointmentDescriptor descriptor2 = new EditAppointmentDescriptorBuilder()
                .withDateTime(VALID_DATETIME_DENTAL).build();
        assertParseSuccess(parser, "1" + DATETIME_DESC_DENTAL,
                new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor2));
    }

    @Test
    public void parse_missingParts_fail() {
        // no fields specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

}
