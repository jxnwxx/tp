package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalDoctorBase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.DoctorBase;
import seedu.address.model.ReadOnlyDoctorBase;

public class JsonDoctorBaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDoctorBaseStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDoctorBase_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readDoctorBase(null));
    }

    private java.util.Optional<ReadOnlyDoctorBase> readDoctorBase(String filePath) throws Exception {
        return new JsonDoctorBaseStorage(Paths.get(filePath)).readDoctorBase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDoctorBase("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readDoctorBase("notJsonFormatDoctorBase.json"));
    }

    @Test
    public void readDoctorBase_invalidPatientDoctorBase_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readDoctorBase("invalidPatientsDoctorBase.json"));
    }

    @Test
    public void readDoctorBase_invalidAndValidPatientDoctorBase_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readDoctorBase("invalidAndValidPatientDoctorBase.json"));
    }

    @Test
    public void readAndSaveDoctorBase_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDoctorBase.json");
        DoctorBase original = getTypicalDoctorBase();
        JsonDoctorBaseStorage jsonDoctorBaseStorage = new JsonDoctorBaseStorage(filePath);

        // Save in new file and read back
        jsonDoctorBaseStorage.saveDoctorBase(original, filePath);
        ReadOnlyDoctorBase readBack = jsonDoctorBaseStorage.readDoctorBase(filePath).get();
        assertEquals(original, new DoctorBase(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonDoctorBaseStorage.saveDoctorBase(original, filePath);
        readBack = jsonDoctorBaseStorage.readDoctorBase(filePath).get();
        assertEquals(original, new DoctorBase(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonDoctorBaseStorage.saveDoctorBase(original); // file path not specified
        readBack = jsonDoctorBaseStorage.readDoctorBase().get(); // file path not specified
        assertEquals(original, new DoctorBase(readBack));

    }

    @Test
    public void saveDoctorBase_nullDoctorBase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDoctorBase(null, "SomeFile.json"));
    }

    /**
     * Saves {@code doctorBase} at the specified {@code filePath}.
     */
    private void saveDoctorBase(ReadOnlyDoctorBase doctorBase, String filePath) {
        try {
            new JsonDoctorBaseStorage(Paths.get(filePath))
                    .saveDoctorBase(doctorBase, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDoctorBase_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDoctorBase(new DoctorBase(), null));
    }
}
