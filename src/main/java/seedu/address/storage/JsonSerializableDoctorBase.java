package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DoctorBase;
import seedu.address.model.ReadOnlyDoctorBase;
import seedu.address.model.patient.Patient;

/**
 * An Immutable DoctorBase that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableDoctorBase {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDoctorBase} with the given patients.
     */
    @JsonCreator
    public JsonSerializableDoctorBase(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
    }

    /**
     * Converts a given {@code ReadOnlyDoctorBase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDoctorBase}.
     */
    public JsonSerializableDoctorBase(ReadOnlyDoctorBase source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code DoctorBase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DoctorBase toModelType() throws IllegalValueException {
        DoctorBase doctorBase = new DoctorBase();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (doctorBase.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            doctorBase.addPatient(patient);
        }
        return doctorBase;
    }

}
