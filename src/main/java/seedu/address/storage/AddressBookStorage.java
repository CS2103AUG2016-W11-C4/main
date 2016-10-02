package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskList;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.TaskList}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskList addressBook) throws IOException;

}
