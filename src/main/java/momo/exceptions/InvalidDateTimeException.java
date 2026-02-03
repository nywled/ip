package momo.exceptions;

/**
 * Exception thrown when a date/time string cannot be parsed or does not
 * match the expected input format.
 */
public class InvalidDateTimeException extends MomoException {

    /**
     * Constructs a new exception with a default error message indicating
     * the expected date/time format.
     */
    public InvalidDateTimeException() {
        super("Ops. Wrong date format. Try: yyyy-MM-dd [HHmm]");
    }
}