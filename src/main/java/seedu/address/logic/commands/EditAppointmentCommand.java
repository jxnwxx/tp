package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing patient's appointment in DoctorBase.
 */
public class EditAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "edit-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits appointment on patient's appointment list. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_INDEX + "INDEX "
            + "[" + PREFIX_APPOINTMENT_TITLE + "TITLE] "
            + "[" + PREFIX_APPOINTMENT_DATETIME + "DATE_TIME (dd-MM-yyyy, HHmm)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_INDEX + "1 "
            + PREFIX_APPOINTMENT_TITLE + "Dental Checkup "
            + PREFIX_APPOINTMENT_DATETIME + "10-10-2010, 0900\n";

    public static final String MESSAGE_SUCCESS = "appointment edited for %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person with this NRIC exists in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";


    private final String targetNric;
    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param targetNric nric of patient to change appointment
     * @param index index of appointment in patient's appointment list
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(String targetNric, Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(targetNric);
        requireNonNull(editAppointmentDescriptor);

        this.targetNric = targetNric;
        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson = model.findPersonByNric(targetNric);
        if (targetPerson == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        ArrayList<Appointment> updatedAppointments = new ArrayList<>(
                targetPerson.getAppointments());

        if (index.getZeroBased() >= updatedAppointments.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = updatedAppointments.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        updatedAppointments.set(index.getZeroBased(), editedAppointment);

        Person newPerson = new Person(targetPerson.getName(),
                targetPerson.getNric(),
                targetPerson.getGender(),
                targetPerson.getPhone(),
                targetPerson.getEmail(),
                targetPerson.getDob(),
                targetPerson.getAddress(),
                targetPerson.getTags(),
                updatedAppointments);

        model.setPerson(targetPerson, newPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newPerson)));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        Title updatedTitle = editAppointmentDescriptor.getTitle().orElse(appointmentToEdit.getTitle());
        LocalDateTime updatedDateTime = editAppointmentDescriptor.getDateTime().orElse(appointmentToEdit.getDateTime());

        return new Appointment(updatedTitle, updatedDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditAppointmentCommand otherEditAppointmentCommand = (EditAppointmentCommand) other;
        return index.equals(otherEditAppointmentCommand.index)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAppointmentDescriptor {
        private Title title;
        private LocalDateTime dateTime;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setTitle(toCopy.title);
            setDateTime(toCopy.dateTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, dateTime);
        }

        public void setTitle(Title title) { this.title = title; }

        public Optional<Title> getTitle() { return Optional.ofNullable(title); }

        public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

        public Optional<LocalDateTime> getDateTime() { return Optional.ofNullable(dateTime); }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            EditAppointmentDescriptor otherEditAppointmentDescriptor = (EditAppointmentDescriptor) other;
            return Objects.equals(title, otherEditAppointmentDescriptor.title)
                    && Objects.equals(dateTime, otherEditAppointmentDescriptor.dateTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("title", title)
                    .add("date", dateTime.toString())
                    .toString();
        }
    }

}
