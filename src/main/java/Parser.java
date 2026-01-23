/**
 * Parser translate the user input into commands to execute
 */
import exceptions.InvalidCommandException;
import exceptions.InvalidArgumentException;

public class Parser {
    public Command parse(String cmd) throws InvalidCommandException, InvalidArgumentException{
        //Handle empty input
        if (cmd == null || cmd.trim().length() == 0) {
            throw new InvalidCommandException();
        }

        //Split the command up to extract main word
        String[] cmdTokens = cmd.trim().split(" ");
        String key = cmdTokens[0].toLowerCase();

        //Match the key
        switch(key){
        case "list":
            if (cmdTokens.length == 1) {
                return new Command("LIST", null);
            } else {
                throw new InvalidArgumentException("list");
            }
        case "mark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("MARK", args);
                } catch (NumberFormatException e){
                    throw new InvalidArgumentException("mark <int>");
                }
            } else {
                throw new InvalidArgumentException("mark <int>");
            }
        case "unmark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("UNMARK", args);
                } catch (NumberFormatException e){
                    throw new InvalidArgumentException("unmark <int>");
                }
            } else {
                throw new InvalidArgumentException("unmark <int>");
            }
        case "bye":
            if (cmdTokens.length == 1) {
                return new Command("EXIT", null);
            }
            throw new InvalidArgumentException("bye");
        case "todo":
            if (cmdTokens.length >= 2) { //todo <title>
                String title = cmd.substring(cmd.indexOf(" ") + 1).trim(); //only take <title>
                if (title.isEmpty()) {
                    throw new InvalidArgumentException("todo <task>");
                }
                return new Command("TODO", new String[]{title});
            }
            throw new InvalidArgumentException("todo <task>");
        case "deadline":
            if (cmd.contains(" /by ")) { //deadline <title> /by <date>
                String[] parts = cmd.split(" /by ", 2);
                if (parts.length == 2 && parts[0].trim().length() > 8) {
                    String title = parts[0].substring(parts[0].indexOf(" ") + 1).trim();
                    String by = parts[1].trim();
                    if (title.isEmpty() || by.isEmpty()) {
                        throw new InvalidArgumentException("deadline <task> /by <date>");
                    }
                    return new Command("DEADLINE", new String[]{title, by});
                }
            }
            throw new InvalidArgumentException("deadline <task> /by <date>");
        case "event":
            if (cmd.contains(" /from ") && cmd.contains(" /to ")) { //event <title> /from <time> /to <time>
                String[] firstSplit = cmd.split(" /from ", 2);

                // Extract title
                String title = firstSplit[0].substring(firstSplit[0].indexOf(" ") + 1).trim();

                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                // Extract times
                String start = secondSplit[0].trim();
                String end = secondSplit[1].trim();
                
                if (title.isEmpty() || start.isEmpty() || end.isEmpty()){
                    throw new InvalidArgumentException("event <task> /from <start_date> /to <end_date>");
                }
                return new Command("EVENT", new String[]{title, start, end});
            }
            throw new InvalidArgumentException("event <task> /from <start_date> /to <end_date>");
        default:
            throw new InvalidCommandException();
        }
    }
}