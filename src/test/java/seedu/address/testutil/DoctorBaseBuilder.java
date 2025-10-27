package seedu.address.testutil;

import seedu.address.model.DoctorBase;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building DoctorBase objects.
 * Example usage: <br>
 *     {@code DoctorBase ab = new DoctorBaseBuilder().withPatient("John", "Doe").build();}
 */
public class DoctorBaseBuilder {

    private DoctorBase doctorBase;

    public DoctorBaseBuilder() {
        doctorBase = new DoctorBase();
    }

    public DoctorBaseBuilder(DoctorBase doctorBase) {
        this.doctorBase = doctorBase;
    }

    /**
     * Adds a new {@code Patient} to the {@code DoctorBase} that we are building.
     */
    public DoctorBaseBuilder withPatient(Patient patient) {
        doctorBase.addPatient(patient);
        return this;
    }

    public DoctorBase build() {
        return doctorBase;
    }
}
