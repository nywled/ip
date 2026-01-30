/**
 * Exception class to handle unknown commands
 */
package momo.exceptions;

public class InvalidCommandException extends MomoException {
    public InvalidCommandException() {
        super("Sorry >.< but Momo don't understand. Would you repeat for Momo again?");
    }
}