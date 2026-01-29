/**
 * Command class used by parser to communicate with controller
 */
import java.time.LocalDateTime;

public class Command {
    private final String action;
    private final String[] args;
    private final LocalDateTime[] timeArgs;

    public Command(String action, String[] args){
        this.action = action;
        this.args = args;
        this.timeArgs = null;
    }

    public Command(String action, String[] args, LocalDateTime[] timeArgs){
        this.action = action;
        this.args = args;
        this.timeArgs = timeArgs;
    }

    public String getAction() {
        return this.action;
    }

    public String[] getArgs() {
        return this.args;
    }

    public LocalDateTime[] getTimeArgs() {
        return this.timeArgs;
    }

}