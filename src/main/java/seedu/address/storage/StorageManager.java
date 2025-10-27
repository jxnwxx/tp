package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyDoctorBase;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of DoctorBase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DoctorBaseStorage doctorBaseStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code DoctorBaseStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(DoctorBaseStorage doctorBaseStorage, UserPrefsStorage userPrefsStorage) {
        this.doctorBaseStorage = doctorBaseStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ DoctorBase methods ==============================

    @Override
    public Path getDoctorBaseFilePath() {
        return doctorBaseStorage.getDoctorBaseFilePath();
    }

    @Override
    public Optional<ReadOnlyDoctorBase> readDoctorBase() throws DataLoadingException {
        return readDoctorBase(doctorBaseStorage.getDoctorBaseFilePath());
    }

    @Override
    public Optional<ReadOnlyDoctorBase> readDoctorBase(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return doctorBaseStorage.readDoctorBase(filePath);
    }

    @Override
    public void saveDoctorBase(ReadOnlyDoctorBase doctorBase) throws IOException {
        saveDoctorBase(doctorBase, doctorBaseStorage.getDoctorBaseFilePath());
    }

    @Override
    public void saveDoctorBase(ReadOnlyDoctorBase doctorBase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        doctorBaseStorage.saveDoctorBase(doctorBase, filePath);
    }

}
