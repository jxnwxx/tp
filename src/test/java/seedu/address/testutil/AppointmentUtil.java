package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {

    /**
     * Returns an add command string for adding the {@code appointment}.
     */
    public static String getAddAppointmentCommand(Index index, Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + index.getOneBased()
                + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_APPOINTMENT_TITLE + appointment.getTitle().title + " ");
        sb.append(PREFIX_APPOINTMENT_DATETIME + appointment.getDateTimeAsString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditAppointmentDescriptor}'s details.
     */
    public static String getEditAppointmentDescriptorDetails(EditAppointmentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_APPOINTMENT_TITLE)
                .append(title.title).append(" "));
        descriptor.getDateTime().ifPresent(datetime -> sb.append(PREFIX_APPOINTMENT_DATETIME)
                .append(datetime.toString()).append(" "));
        return sb.toString();
    }
}
