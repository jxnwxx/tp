package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewMode;
import seedu.address.model.patient.Patient;

/**
 * List appointments of the specified patient in the address book.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "list-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List appointments of a patient identified by the index number used in the displayed patient list.\n."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Listed all appointments for %1$s [%2$s]";
    public static final String MESSAGE_NOT_LISTING_PATIENTS = "Command only works when displaying patients."
            + " Run command list to display patients.";


    private final Index targetIndex;

    /**
     * Creates a ListAppointmentCommand to list appointments of the patient at the specified index.
     */
    public ListAppointmentCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (model.getViewMode() != ViewMode.PATIENT_LIST) {
            throw new CommandException(MESSAGE_NOT_LISTING_PATIENTS);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
        Patient targetPatient = lastShownList.get(targetIndex.getZeroBased());

        model.setSelectedPatient(targetPatient);
        model.setViewMode(ViewMode.PATIENT_APPOINTMENT_LIST);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetPatient.getName(),
                targetPatient.getNric()), false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListAppointmentCommand)) {
            return false;
        }

        ListAppointmentCommand otherListAppointmentCommand = (ListAppointmentCommand) other;
        return targetIndex.equals(otherListAppointmentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
