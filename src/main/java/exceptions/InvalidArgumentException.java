/**
 * exception class to handle known commands with invalid arguments
 * e.g out-of bound, unknown
 */
package exceptions;

public class InvalidArgumentException extends MomoException {
    public InvalidArgumentException(String msg) {
        super("Eeek >.<! Did you mean: " + msg);
    }
}