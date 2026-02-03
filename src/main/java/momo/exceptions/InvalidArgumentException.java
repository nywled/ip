package momo.exceptions;

/**
 * Exception thrown when a valid command is provided with invalid arguments (e.g OOB, missing parameters)
 */
public class InvalidArgumentException extends MomoException {

    /**
     * Constructs a new exception with a error message that includes a suggested correct command format.
     *
     * @param msg Suggested correct usage or command format.
     */
    public InvalidArgumentException(String msg) {
        super("Eeek >.<! Did you mean: " + msg);
    }
}