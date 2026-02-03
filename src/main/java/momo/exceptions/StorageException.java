package momo.exceptions;

/**
 * Exception thrown when an error occurs during task storage operations.
 * <p>
 * This exception represents failures related to reading from or writing to
 * persistent storage, such as corrupted data or file access issues.
 * </p>
 */
public class StorageException extends MomoException {

    /**
     * Constructs a new storage exception with the specified message.
     *
     * @param message Description of the storage error.
     */
    public StorageException(String message) {
        super(message);
    }

}
