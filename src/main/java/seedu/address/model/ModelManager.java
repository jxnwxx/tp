package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DoctorBase doctorBase;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;
    private Patient selectedPatient;
    private ViewMode currentViewMode;

    /**
     * Initializes a ModelManager with the given doctorBase and userPrefs.
     */
    public ModelManager(ReadOnlyDoctorBase doctorBase, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(doctorBase, userPrefs);

        logger.fine("Initializing with address book: " + doctorBase + " and user prefs " + userPrefs);

        this.doctorBase = new DoctorBase(doctorBase);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.doctorBase.getPatientList());
        selectedPatient = null;
        currentViewMode = ViewMode.PATIENT_LIST;
    }

    public ModelManager() {
        this(new DoctorBase(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getDoctorBaseFilePath() {
        return userPrefs.getDoctorBaseFilePath();
    }

    @Override
    public void setDoctorBaseFilePath(Path doctorBaseFilePath) {
        requireNonNull(doctorBaseFilePath);
        userPrefs.setDoctorBaseFilePath(doctorBaseFilePath);
    }

    //=========== DoctorBase ================================================================================

    @Override
    public void setDoctorBase(ReadOnlyDoctorBase doctorBase) {
        this.doctorBase.resetData(doctorBase);
    }

    @Override
    public ReadOnlyDoctorBase getDoctorBase() {
        return doctorBase;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return doctorBase.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        doctorBase.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        doctorBase.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        doctorBase.setPatient(target, editedPatient);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedDoctorBase}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    @Override
    public ObservableList<Appointment> getUpcomingAppointments() {
        return doctorBase.getUpcomingAppointmentList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return doctorBase.equals(otherModelManager.doctorBase)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPatients.equals(otherModelManager.filteredPatients);
    }

    @Override
    public Patient findPatientByNric(String targetNric) {
        requireNonNull(targetNric);
        for (Patient patient : doctorBase.getPatientList()) {
            if (patient.getNric().toString().equalsIgnoreCase(targetNric)) {
                return patient;
            }
        }
        return null;
    }

    @Override
    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    @Override
    public void setSelectedPatient(Patient patient) {
        selectedPatient = patient;
    }

    @Override
    public ViewMode getViewMode() {
        return currentViewMode;
    }

    @Override
    public void setViewMode(ViewMode viewMode) {
        requireNonNull(viewMode);
        this.currentViewMode = viewMode;
    }
}
