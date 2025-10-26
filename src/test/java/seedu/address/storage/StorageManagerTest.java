package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPatients.getTypicalDoctorBase;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.DoctorBase;
import seedu.address.model.ReadOnlyDoctorBase;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonDoctorBaseStorage doctorBaseStorage = new JsonDoctorBaseStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(doctorBaseStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void doctorBaseReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonDoctorBaseStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonDoctorBaseStorageTest} class.
         */
        DoctorBase original = getTypicalDoctorBase();
        storageManager.saveDoctorBase(original);
        ReadOnlyDoctorBase retrieved = storageManager.readDoctorBase().get();
        assertEquals(original, new DoctorBase(retrieved));
    }

    @Test
    public void getDoctorBaseFilePath() {
        assertNotNull(storageManager.getDoctorBaseFilePath());
    }

}
