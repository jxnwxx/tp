package seedu.address.model;

/**
 * Represents the current view mode of the application UI.
 * The view mode determines what type of information is currently displayed to the user
 * (e.g. list of patients, or list of upcoming appointments).
 */
public enum ViewMode {
    PATIENT_LIST,
    PATIENT_APPOINTMENT_LIST,
    UPCOMING_APPOINTMENT_LIST
}
