/**
 * Command class used by parser to communicate with controller
 */
public class Command {
    private final String action;
    private final String args;

    public Command(String action, String args){
        this.action = action;
        this.args = args;
    }

    public String getAction() {
        return this.action;
    }

    public String getArgs() {
        return this.args;
    }

}