/**
 * Parses the user input into standard format
 */
public class Parser {
    public Command parse(String cmd) {
        //Handle empty input
        if (cmd == null) {
            return new Command("INVALID", null);
        }
        cmd = cmd.trim();
        if (cmd.length() == 0) {
            return new Command("INVALID", null);
        }

        //Split the command up to extract main word
        String[] cmdTokens = cmd.split(" ");
        String key = cmdTokens[0].toLowerCase();

        //Match the key
        switch(key){
        case "list":
            if (cmdTokens.length == 1) {
                return new Command("LIST", null);
            }
        case "mark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    return new Command("MARK", cmdTokens[1]);
                } catch (NumberFormatException e){
                    return new Command("ADD", cmd);
                }
            } else {
                return new Command("ADD", cmd);
            }
        case "unmark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    return new Command("UNMARK", cmdTokens[1]);
                } catch (NumberFormatException e){
                    return new Command("ADD", cmd);
                }
            } else {
                return new Command("ADD", cmd);
            }
        case "bye":
            return new Command("EXIT", null);
        default:
            return new Command("ADD", cmd);
        }
    }
}