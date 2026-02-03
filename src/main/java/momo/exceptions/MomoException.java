package momo.exceptions;

/**
 * Root exception type for all Momo-specific runtime exceptions.
 * <p>
 * All application-specific runtime exceptions should extend this class.
 * It represents errors that occur during command processing, storage,
 * or application logic.
 * </p>
 */
public class MomoException extends RuntimeException {

    /**
     * Constructs a new Momo exception with the specified message.
     *
     * @param message Description of the error.
     */
    public MomoException(String message) {
        super(message);
    }
}
