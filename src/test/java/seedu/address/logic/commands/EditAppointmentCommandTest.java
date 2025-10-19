package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy, HHmm");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        // change both title and dateTime
        Appointment editedAppointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(newPatient)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        // only change dateTime
        Appointment editedAppointment = new AppointmentBuilder()
                .withDateTime("20-02-2002, 1000").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(newPatient)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        // no change
        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(newPatient)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equalsTest() {
        // same object
        Appointment appointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(appointment).build();
        EditAppointmentCommand command = new EditAppointmentCommand("S1234567D", INDEX_FIRST_APPOINTMENT, descriptor);
        assertTrue(command.equals(command));

        // wrong type
        String str = "wrong type";
        assertFalse(command.equals(str));

        // wrong nric
        EditAppointmentCommand cmd1 = new EditAppointmentCommand("S1244567D", INDEX_FIRST_APPOINTMENT, descriptor);
        assertFalse(command.equals(cmd1));

        // wrong index
        EditAppointmentCommand cmd2 = new EditAppointmentCommand("S1234567D", INDEX_SECOND_APPOINTMENT, descriptor);
        assertFalse(command.equals(cmd2));

        // wrong appointment
        Appointment ppt = new AppointmentBuilder().withTitle("fake disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor desc1 = new EditAppointmentDescriptorBuilder(ppt).build();
        EditAppointmentCommand cmd3 = new EditAppointmentCommand("S1234567D", INDEX_FIRST_APPOINTMENT, desc1);
        assertFalse(command.equals(cmd3));

        // equal attributes
        Appointment appt = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor desc = new EditAppointmentDescriptorBuilder(appt).build();
        EditAppointmentCommand cmd4 = new EditAppointmentCommand("S1234567D", INDEX_FIRST_APPOINTMENT, desc);
        assertTrue(command.equals(cmd4));
    }
}
