package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new EditAppointmentCommand object
 */
public class EditAppointmentCommandParser implements Parser<EditAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointmentCommand
     * and returns an EditAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_INDEX,
                        PREFIX_APPOINTMENT_TITLE, PREFIX_APPOINTMENT_DATETIME);

        Nric nric;
        Index index;

        try {
            nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC, PREFIX_INDEX, PREFIX_APPOINTMENT_TITLE,
                PREFIX_APPOINTMENT_DATETIME);

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

        if (argMultimap.getValue(PREFIX_APPOINTMENT_TITLE).isPresent()) {
            editAppointmentDescriptor.setTitle(ParserUtil.parseTitle(argMultimap
                    .getValue(PREFIX_APPOINTMENT_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_APPOINTMENT_DATETIME).isPresent()) {
            editAppointmentDescriptor.setDateTime(ParserUtil.parseDateTime(argMultimap
                    .getValue(PREFIX_APPOINTMENT_DATETIME).get()));
        }

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(nric.toString(), index, editAppointmentDescriptor);
    }
}
