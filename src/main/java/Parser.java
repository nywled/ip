/**
 * Parser translate the user input into commands to execute
 */
public class Parser {
    public Command parse(String cmd) {
        //Handle empty input
        if (cmd == null || cmd.trim().length() == 0) {
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
            return new Command("INVALID", null);
        case "mark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("MARK", args);
                } catch (NumberFormatException e){
                    //Invalid param --> fall through to default
                }
            } 
            return new Command("INVALID", null);
        case "unmark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("UNMARK", args);
                } catch (NumberFormatException e){
                    //Invalid param --> fall through to default
                }
            }
            return new Command("INVALID", null);
        case "bye":
            if (cmdTokens.length == 1) {
                return new Command("EXIT", null);
            }
            return new Command("INVALID", null);
        case "todo":
            if (cmdTokens.length >= 2) { //todo <title>
                String title = cmd.substring(cmd.indexOf(" ") + 1); //only take <title>
                return new Command("TODO", new String[]{title});
            }
            return new Command("INVALID", null);
        case "deadline":
            if (cmd.contains(" /by ")) { //deadline <title> /by <date>
                String[] parts = cmd.split(" /by ", 2);
                if (parts.length == 2 && parts[0].trim().length() > 8) {
                    String title = parts[0].substring(parts[0].indexOf(" ") + 1).trim();
                    String by = parts[1].trim();
                    return new Command("DEADLINE", new String[]{title, by});
                }
            }
            return new Command("INVALID", null);
        case "event":
            try {
                if (cmd.contains(" /from ") && cmd.contains(" /to ")) { //event <title> /from <time> /to <time>
                    String[] firstSplit = cmd.split(" /from ", 2);

                    // Extract title
                    String title = firstSplit[0].substring(firstSplit[0].indexOf(" ") + 1).trim();

                    String[] secondSplit = firstSplit[1].split(" /to ", 2);

                    // Extract times
                    String start = secondSplit[0].trim();
                    String end = secondSplit[1].trim();

                    return new Command("EVENT", new String[]{title, start, end});
                }
            } catch (Exception e) {
                //Invalid input --> fall to default
            }
            return new Command("INVALID", null);
        default:
            return new Command("INVALID", null);
        }
    }
}