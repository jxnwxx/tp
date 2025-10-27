package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.DoctorBase;
import seedu.address.model.ReadOnlyDoctorBase;

/**
 * Represents a storage for {@link DoctorBase}.
 */
public interface DoctorBaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDoctorBaseFilePath();

    /**
     * Returns DoctorBase data as a {@link ReadOnlyDoctorBase}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyDoctorBase> readDoctorBase() throws DataLoadingException;

    /**
     * @see #getDoctorBaseFilePath()
     */
    Optional<ReadOnlyDoctorBase> readDoctorBase(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyDoctorBase} to the storage.
     * @param doctorBase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDoctorBase(ReadOnlyDoctorBase doctorBase) throws IOException;

    /**
     * @see #saveDoctorBase(ReadOnlyDoctorBase)
     */
    void saveDoctorBase(ReadOnlyDoctorBase doctorBase, Path filePath) throws IOException;

}
