package momo.exceptions;

public class InvalidDateTimeException extends MomoException {
    public InvalidDateTimeException() {
        super("Ops. Wrong date format. Try: yyyy-MM-dd [HHmm]");
    }
}