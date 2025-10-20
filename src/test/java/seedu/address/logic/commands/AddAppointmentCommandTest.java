package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.patient.Patient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddAppointmentCommand.
 */
public class AddAppointmentCommandTest {
    private static final Appointment VALID_APPOINTMENT = new Appointment(new Title("Dental Checkup"),
            LocalDateTime.of(2020, 02, 20, 20, 20));

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }


    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(
                null, new Appointment(new Title("A"), LocalDateTime.now())));
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(
                Index.fromOneBased(1), null));
    }

    @Test
    public void execute_patientExists_addSuccessful() throws Exception {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        AddAppointmentCommand command = new AddAppointmentCommand(INDEX_FIRST_PATIENT, VALID_APPOINTMENT);

        ArrayList<Appointment> updatedAppointments = new ArrayList<>(Stream.concat(patient.getAppointments().stream(),
                Stream.of(VALID_APPOINTMENT)).toList());

        Patient editedPatient = new Patient(patient.getName(), patient.getNric(), patient.getGender(),
                patient.getPhone(), patient.getEmail(), patient.getDob(), patient.getAddress(), patient.getTags(),
                updatedAppointments);

        String expectedMessage = String.format(
                AddAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(editedPatient),
                VALID_APPOINTMENT
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPatient(expectedModel.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased()),
                editedPatient);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patientNotFound_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        AddAppointmentCommand command = new AddAppointmentCommand(outOfBoundIndex, VALID_APPOINTMENT);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Appointment appt1 = new Appointment(new Title("A"), LocalDateTime.of(2025, 10, 1, 10, 0));
        Appointment appt2 = new Appointment(new Title("B"), LocalDateTime.of(2025, 11, 2, 11, 0));

        AddAppointmentCommand cmd1 = new AddAppointmentCommand(INDEX_FIRST_PATIENT, appt1);
        AddAppointmentCommand cmd2 = new AddAppointmentCommand(INDEX_FIRST_PATIENT, appt1);
        AddAppointmentCommand cmd3 = new AddAppointmentCommand(INDEX_SECOND_PATIENT, appt1);
        AddAppointmentCommand cmd4 = new AddAppointmentCommand(INDEX_FIRST_PATIENT, appt2);

        // same object -> true
        assertTrue(cmd1.equals(cmd1));

        // same values -> true
        assertTrue(cmd1.equals(cmd2));

        // null -> false
        assertFalse(cmd1.equals(null));

        // different type -> false
        assertFalse(cmd1.equals(1));

        // different NRIC or appointment -> false
        assertFalse(cmd1.equals(cmd3));
        assertFalse(cmd1.equals(cmd4));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Appointment appointment = new Appointment(new Title("Checkup"),
                LocalDateTime.of(2025, 10, 15, 9, 0));
        AddAppointmentCommand command = new AddAppointmentCommand(index, appointment);
        String expected = AddAppointmentCommand.class.getCanonicalName()
                + "{index=" + index + ", toAdd=" + appointment + "}";
        assertEquals(expected, command.toString());
    }
}
