package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ECZEMA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.getTypicalDoctorBase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.testutil.PatientBuilder;

public class DoctorBaseTest {

    private final DoctorBase doctorBase = new DoctorBase();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), doctorBase.getPatientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> doctorBase.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDoctorBase_replacesData() {
        DoctorBase newData = getTypicalDoctorBase();
        doctorBase.resetData(newData);
        assertEquals(newData, doctorBase);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two patients with the same identity fields
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_ECZEMA)
                .build();
        List<Patient> newPatients = Arrays.asList(ALICE, editedAlice);
        DoctorBaseStub newData = new DoctorBaseStub(newPatients);

        assertThrows(DuplicatePatientException.class, () -> doctorBase.resetData(newData));
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> doctorBase.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInDoctorBase_returnsFalse() {
        assertFalse(doctorBase.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInDoctorBase_returnsTrue() {
        doctorBase.addPatient(ALICE);
        assertTrue(doctorBase.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInDoctorBase_returnsTrue() {
        doctorBase.addPatient(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_ECZEMA)
                .build();
        assertTrue(doctorBase.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> doctorBase.getPatientList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = DoctorBase.class.getCanonicalName() + "{patients=" + doctorBase.getPatientList() + "}";
        assertEquals(expected, doctorBase.toString());
    }

    /**
     * A stub ReadOnlyDoctorBase whose patients list can violate interface constraints.
     */
    private static class DoctorBaseStub implements ReadOnlyDoctorBase {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        DoctorBaseStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

        @Override
        public ObservableList<Appointment> getUpcomingAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
