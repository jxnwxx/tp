package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DoctorBase;
import seedu.address.testutil.TypicalPatients;

public class JsonSerializableDoctorBaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDoctorBaseTest");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsDoctorBase.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatientsDoctorBase.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientDoctorBase.json");

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializableDoctorBase dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializableDoctorBase.class).get();
        DoctorBase doctorBaseFromFile = dataFromFile.toModelType();
        DoctorBase typicalPatientsDoctorBase = TypicalPatients.getTypicalDoctorBase();
        assertEquals(doctorBaseFromFile, typicalPatientsDoctorBase);
    }

    @Test
    public void toModelType_invalidPatientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDoctorBase dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializableDoctorBase.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializableDoctorBase dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializableDoctorBase.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableDoctorBase.MESSAGE_DUPLICATE_PATIENT,
                dataFromFile::toModelType);
    }

}
