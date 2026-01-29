/**
 * Parser translate the user input into commands to execute
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import exceptions.MomoException;
import exceptions.InvalidCommandException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidDateTimeException;

public class Parser {
    public Command parse(String cmd) throws MomoException{
        //Handle empty input
        if (cmd == null || cmd.trim().length() == 0) {
            throw new InvalidCommandException();
        }

        //Split the command up to extract main word
        String[] cmdTokens = cmd.trim().split(" ");
        String key = cmdTokens[0].toLowerCase();

        switch(key){
        //LIST
        case "list":
            if (cmdTokens.length == 1) {
                return new Command("LIST", null);
            } else {
                throw new InvalidArgumentException("list");
            }
        //MARK
        case "mark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("MARK", args);
                } catch (NumberFormatException err){
                    throw new InvalidArgumentException("mark <int>");
                }
            } else {
                throw new InvalidArgumentException("mark <int>");
            }
        //UNMARK
        case "unmark":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("UNMARK", args);
                } catch (NumberFormatException err){
                    throw new InvalidArgumentException("unmark <int>");
                }
            } else {
                throw new InvalidArgumentException("unmark <int>");
            }
        //DELETE
        case "delete":
            if (cmdTokens.length == 2) {
                try {
                    Integer.parseInt(cmdTokens[1]);
                    String[] args = new String[]{cmdTokens[1]};
                    return new Command("DELETE", args);
                } catch (NumberFormatException err){
                    throw new InvalidArgumentException("delete <int>");
                }
            } else {
                throw new InvalidArgumentException("delete <int>");
            }
        //BYE
        case "bye":
            if (cmdTokens.length == 1) {
                return new Command("EXIT", null);
            }
            throw new InvalidArgumentException("bye");
        //TODO
        case "todo":
            if (cmdTokens.length >= 2) { //todo <title>
                String title = cmd.substring(cmd.indexOf(" ") + 1).trim(); //only take <title>
                if (title.isEmpty()) {
                    throw new InvalidArgumentException("todo <task>");
                }
                return new Command("TODO", new String[]{title});
            }
            throw new InvalidArgumentException("todo <task>");
        //DEADLINE
        case "deadline":
            if (cmd.contains(" /by ")) { //deadline <title> /by <date>
                String[] parts = cmd.split(" /by ", 2);
                if (parts.length == 2 && parts[0].trim().length() > 8) {
                    String title = parts[0].substring(parts[0].indexOf(" ") + 1).trim();
                    String by = parts[1].trim();
                    if (title.isEmpty() || by.isEmpty()) {
                        throw new InvalidArgumentException("deadline <task> /by <date>");
                    }
                    LocalDateTime byDateTime = parseUserDateTime(by);
                    return new Command("DEADLINE", new String[]{title}, new LocalDateTime[]{byDateTime});
                }
            }
            throw new InvalidArgumentException("deadline <task> /by <date>");
        //EVENT
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
                LocalDateTime startDateTime = parseUserDateTime(start);
                LocalDateTime endDateTime = parseUserDateTime(end);
                return new Command("EVENT", new String[]{title},new LocalDateTime[]{startDateTime, endDateTime});
            }
            throw new InvalidArgumentException("event <task> /from <start_date> /to <end_date>");
        //DEFAULT
        default:
            throw new InvalidCommandException();
        }
    }

    private LocalDateTime parseUserDateTime(String input) throws InvalidDateTimeException {
    String x = input.trim();
    if (x.isEmpty()) {
        throw new InvalidDateTimeException();
    }

    // date-only: yyyy-MM-dd
    try {
        LocalDate d = LocalDate.parse(x, DateTimeFormatter.ISO_LOCAL_DATE);
        return d.atStartOfDay();
    } catch (DateTimeParseException ignored) {
        // continue
    }

    // date and time: yyyy-MM-dd HHmm
    try {
        String[] tokens = x.split("\\s+");
        if (tokens.length != 2) {
            throw new InvalidDateTimeException();
        }

        LocalDate date = LocalDate.parse(tokens[0], DateTimeFormatter.ISO_LOCAL_DATE);

        String t = tokens[1];
        if (t.length() != 4) {
            throw new InvalidDateTimeException();
        }

        int hh = Integer.parseInt(t.substring(0, 2));
        int mm = Integer.parseInt(t.substring(2, 4));
        LocalTime time = LocalTime.of(hh, mm); // validates time range

        return LocalDateTime.of(date, time);
    } catch (RuntimeException err) {
        throw new InvalidDateTimeException();
    }
}
}