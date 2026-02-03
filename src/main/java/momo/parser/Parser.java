package momo.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import momo.commands.Command;
import momo.commands.DeadlineCommand;
import momo.commands.DeleteCommand;
import momo.commands.EventCommand;
import momo.commands.ExitCommand;
import momo.commands.ListCommand;
import momo.commands.MarkCommand;
import momo.commands.TodoCommand;
import momo.commands.UnmarkCommand;
import momo.exceptions.InvalidArgumentException;
import momo.exceptions.InvalidCommandException;
import momo.exceptions.InvalidDateTimeException;
import momo.exceptions.MomoException;

/**
 * Parser validates raw user input and parses into executable {@link momo.commands.Command} objects.
 * Throws {@link momo.exceptions.MomoException} subclasses for invalid inputs.
 */
public class Parser {
    /**
     * Parses a user command string into a {@link Command}.
     * <p>
     * The first token is interpreted as a command keyword which is mapped to a {@link CommandType}.
     * Remaining tokens (or delimiter sections such as {@code /by}, {@code /from}, {@code /to}) are
     * parsed as arguments.
     * </p>
     *
     * @param cmd Raw user input.
     * @return A concrete {@link Command} corresponding to the user input.
     * @throws InvalidCommandException If the command keyword is missing or unrecognized.
     * @throws InvalidArgumentException If the command keyword is valid but arguments are invalid.
     * @throws InvalidDateTimeException If a date/time argument does not match the expected format.
     */
    public Command parse(String cmd) throws MomoException {
        //Handle empty input
        if (cmd == null || cmd.trim().length() == 0) {
            throw new InvalidCommandException();
        }

        //Split the command up to extract main word
        String[] cmdTokens = cmd.trim().split("\\s+");
        String key = cmdTokens[0].toLowerCase();

        //Enum matching
        CommandType type;
        try {
            type = CommandType.valueOf(key.toUpperCase());
        } catch (IllegalArgumentException err) {
            throw new InvalidCommandException();
        }

        switch(type) {
        //LIST
        case LIST:
            if (cmdTokens.length == 1) {
                return new ListCommand();
            }
            throw new InvalidArgumentException("list");
        //MARK
        case MARK:
            if (cmdTokens.length != 2) {
                throw new InvalidArgumentException("mark <int>");
            }
            try {
                return new MarkCommand(Integer.parseInt(cmdTokens[1]));
            } catch (NumberFormatException err) {
                throw new InvalidArgumentException("mark <int>");
            }
        //UNMARK
        case UNMARK:
            if (cmdTokens.length != 2) {
                throw new InvalidArgumentException("unmark <int>");
            }
            try {
                return new UnmarkCommand(Integer.parseInt(cmdTokens[1]));
            } catch (NumberFormatException err) {
                throw new InvalidArgumentException("unmark <int>");
            }
        //DELETE
        case DELETE:
            if (cmdTokens.length != 2) {
                throw new InvalidArgumentException("delete <int>");
            }
            try {
                return new DeleteCommand(Integer.parseInt(cmdTokens[1]));
            } catch (NumberFormatException err) {
                throw new InvalidArgumentException("delete <int>");
            }
        //BYE
        case BYE:
            if (cmdTokens.length == 1) {
                return new ExitCommand();
            }
            throw new InvalidArgumentException("bye");
        //TODo
        case TODO:
            if (cmdTokens.length >= 2) { //todo <title>
                String title = cmd.substring(cmd.indexOf(" ") + 1).trim(); //only take <title>
                if (title.isEmpty()) {
                    throw new InvalidArgumentException("todo <task>");
                }
                return new TodoCommand(title);
            }
            throw new InvalidArgumentException("todo <task>");
        //DEADLINE
        case DEADLINE:
            if (cmd.contains(" /by ")) { //deadline <title> /by <date>
                String[] parts = cmd.split(" /by ", 2);
                if (parts.length == 2 && parts[0].trim().length() > 8) {
                    String title = parts[0].substring(parts[0].indexOf(" ") + 1).trim();
                    String by = parts[1].trim();
                    if (title.isEmpty() || by.isEmpty()) {
                        throw new InvalidArgumentException("deadline <task> /by <date>");
                    }
                    LocalDateTime byDateTime = parseUserDateTime(by);
                    return new DeadlineCommand(title, byDateTime);
                }
            }
            throw new InvalidArgumentException("deadline <task> /by <date>");
        //EVENT
        case EVENT:
            if (cmd.contains(" /from ") && cmd.contains(" /to ")) { //event <title> /from <time> /to <time>
                String[] firstSplit = cmd.split(" /from ", 2);

                // Extract title
                String title = firstSplit[0].substring(firstSplit[0].indexOf(" ") + 1).trim();

                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                // Extract times
                String start = secondSplit[0].trim();
                String end = secondSplit[1].trim();

                if (title.isEmpty() || start.isEmpty() || end.isEmpty()) {
                    throw new InvalidArgumentException("event <task> /from <start_date> /to <end_date>");
                }
                LocalDateTime startDateTime = parseUserDateTime(start);
                LocalDateTime endDateTime = parseUserDateTime(end);
                return new EventCommand(title, startDateTime, endDateTime);
            }
            throw new InvalidArgumentException("event <task> /from <start_date> /to <end_date>");
        //DEFAULT
        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Parses user date/time input into a {@link LocalDateTime}.
     * <p>
     * Supported formats:
     * <ul>
     *   <li>{@code yyyy-MM-dd} (date only, interpreted as start of day)</li>
     *   <li>{@code yyyy-MM-dd HHmm} (date with 24-hour time)</li>
     * </ul>
     * </p>
     *
     * @param input User date/time string.
     * @return Parsed {@link LocalDateTime}.
     * @throws InvalidDateTimeException If the input is blank or does not match supported formats.
     */
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
