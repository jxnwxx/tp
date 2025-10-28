package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.model.appointment.Appointment.DATETIME_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBaseBuilder;
import seedu.address.testutil.PatientBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new DoctorBase(), new DoctorBase(modelManager.getDoctorBase()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDoctorBaseFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDoctorBaseFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDoctorBaseFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDoctorBaseFilePath(null));
    }

    @Test
    public void setDoctorBaseFilePath_validPath_setsDoctorBaseFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDoctorBaseFilePath(path);
        assertEquals(path, modelManager.getDoctorBaseFilePath());
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInDoctorBase_returnsFalse() {
        assertFalse(modelManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInDoctorBase_returnsTrue() {
        modelManager.addPatient(ALICE);
        assertTrue(modelManager.hasPatient(ALICE));
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPatientList().remove(0));
    }

    @Test
    public void setSelectedPatient_thenGetSelectedPatient_returnsCorrectValue() {
        // Set to valid Patient
        modelManager.setSelectedPatient(ALICE);
        assertEquals(modelManager.getSelectedPatient(), ALICE);

        // Set to null
        modelManager.setSelectedPatient(null);
        assertEquals(modelManager.getSelectedPatient(), null);
    }

    @Test
    public void getUpcomingAppointments_noPatients_returnsEmptyList() {
        assertTrue(modelManager.getUpcomingAppointments().isEmpty());
    }

    @Test
    public void getUpcomingAppointments_delegatesToDoctorBase() {
        LocalDateTime now = LocalDateTime.now();
        Appointment future = new AppointmentBuilder().withDateTime(now.format(DATETIME_FORMAT)).build();

        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(future);

        Patient patient = new PatientBuilder().withAppointments(appointments).build();
        modelManager.addPatient(patient);

        ObservableList<Appointment> expected = modelManager.getDoctorBase().getUpcomingAppointmentList();
        ObservableList<Appointment> actual = modelManager.getUpcomingAppointments();

        assertEquals(expected, actual);
    }

    @Test
    public void getUpcomingAppointments_listIsUnmodifiable() {
        modelManager.addPatient(ALICE);

        ObservableList<Appointment> upcoming = modelManager.getUpcomingAppointments();
        assertThrows(UnsupportedOperationException.class, () ->
                upcoming.add(new AppointmentBuilder().build()));
    }

    @Test
    public void equals() {
        DoctorBase doctorBase = new DoctorBaseBuilder().withPatient(ALICE).withPatient(BENSON).build();
        DoctorBase differentDoctorBase = new DoctorBase();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(doctorBase, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(doctorBase, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different doctorBase -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDoctorBase, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(doctorBase, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDoctorBaseFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(doctorBase, differentUserPrefs)));
    }
}
