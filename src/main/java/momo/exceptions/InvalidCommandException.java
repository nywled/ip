package momo.exceptions;

/**
 * Exception thrown when a user enters an unrecognized or unsupported command.
 */
public class InvalidCommandException extends MomoException {

    /**
     * Constructs a new exception with a default error message.
     */
    public InvalidCommandException() {
        super("Sorry >.< but Momo don't understand. Would you repeat for Momo again?");
    }
}