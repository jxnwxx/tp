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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAppointmentCommand.
 */
public class ListAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ListAppointmentCommand(null));
    }

    @Test
    public void execute_patientExists_success() throws Exception {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ListAppointmentCommand command = new ListAppointmentCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(ListAppointmentCommand.MESSAGE_SUCCESS, patient.getName(),
                patient.getNric());
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        patient = model.getFilteredPatientList().get(INDEX_SECOND_PATIENT.getZeroBased());
        command = new ListAppointmentCommand(INDEX_SECOND_PATIENT);

        expectedMessage = String.format(ListAppointmentCommand.MESSAGE_SUCCESS, patient.getName(),
                patient.getNric());
        expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        ListAppointmentCommand command = new ListAppointmentCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListAppointmentCommand cmd1 = new ListAppointmentCommand(INDEX_FIRST_PATIENT);
        ListAppointmentCommand cmd2 = new ListAppointmentCommand(INDEX_FIRST_PATIENT);
        ListAppointmentCommand cmd3 = new ListAppointmentCommand(INDEX_SECOND_PATIENT);

        // same object -> true
        assertTrue(cmd1.equals(cmd1));

        // same values -> true
        assertTrue(cmd1.equals(cmd2));

        // null -> false
        assertFalse(cmd1.equals(null));

        // different type -> false
        assertFalse(cmd1.equals(1));

        // different NRIC -> false
        assertFalse(cmd1.equals(cmd3));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ListAppointmentCommand command = new ListAppointmentCommand(targetIndex);
        String expected = ListAppointmentCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
