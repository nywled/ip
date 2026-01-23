package exceptions;

public class InvalidArgumentException extends MomoException {
    public InvalidArgumentException(String msg) {
        super("Eeek >.<! Did you mean: " + msg);
    }
}