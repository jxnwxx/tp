package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyDoctorBase;

/**
 * A class to access DoctorBase data stored as a json file on the hard disk.
 */
public class JsonDoctorBaseStorage implements DoctorBaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDoctorBaseStorage.class);

    private Path filePath;

    public JsonDoctorBaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDoctorBaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDoctorBase> readDoctorBase() throws DataLoadingException {
        return readDoctorBase(filePath);
    }

    /**
     * Similar to {@link #readDoctorBase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyDoctorBase> readDoctorBase(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableDoctorBase> jsonDoctorBase = JsonUtil.readJsonFile(
                filePath, JsonSerializableDoctorBase.class);
        if (!jsonDoctorBase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDoctorBase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveDoctorBase(ReadOnlyDoctorBase doctorBase) throws IOException {
        saveDoctorBase(doctorBase, filePath);
    }

    /**
     * Similar to {@link #saveDoctorBase(ReadOnlyDoctorBase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDoctorBase(ReadOnlyDoctorBase doctorBase, Path filePath) throws IOException {
        requireNonNull(doctorBase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDoctorBase(doctorBase), filePath);
    }

}
