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
import momo.commands.FindCommand;
import momo.commands.ListCommand;
import momo.commands.MarkCommand;
import momo.commands.TagCommand;
import momo.commands.TodoCommand;
import momo.commands.UnmarkCommand;
import momo.commands.UntagCommand;
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
        if (cmd == null || cmd.trim().length() == 0) { //Handle empty input
            throw new InvalidCommandException();
        }

        String[] cmdTokens = cmd.trim().split("\\s+"); //Split the command up to extract main word
        String trimmed = cmd.trim();

        CommandType type = matchEnum(cmdTokens[0]); //Enum matching

        switch(type) {
        case LIST:
            return parseListCommand(cmdTokens);
        case MARK:
            return parseMarkCommand(cmdTokens);
        case UNMARK:
            return parseUnmarkCommand(cmdTokens);
        case DELETE:
            return parseDeleteCommand(cmdTokens);
        case BYE:
            return parseByeCommand(cmdTokens);
        case TODO:
            return parseTodoCommand(trimmed, cmdTokens);
        case DEADLINE:
            return parseDeadlineCommand(trimmed);
        case EVENT:
            return parseEventCommand(trimmed);
        case FIND:
            return parseFindCommand(trimmed, cmdTokens);
        case TAG:
            return parseTagCommand(cmdTokens);
        case UNTAG:
            return parseUntagCommand(cmdTokens);
        default:
            throw new InvalidCommandException();
        }
    }

    private CommandType matchEnum(String commandWord) throws InvalidCommandException {
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidCommandException();
        }
    }

    private Command parseListCommand(String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length != 1) {
            throw new InvalidArgumentException("list");
        }

        return new ListCommand();
    }

    private Command parseByeCommand(String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length != 1) {
            throw new InvalidArgumentException("bye");
        }

        return new ExitCommand();
    }

    private Command parseMarkCommand(String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length != 2) {
            throw new InvalidArgumentException("mark <index>");
        }
        try {
            return new MarkCommand(Integer.parseInt(cmdTokens[1]));
        } catch (NumberFormatException err) {
            throw new InvalidArgumentException("mark <index>. Index must be an integer");
        }
    }

    private Command parseUnmarkCommand(String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length != 2) {
            throw new InvalidArgumentException("unmark <index>");
        }
        try {
            return new UnmarkCommand(Integer.parseInt(cmdTokens[1]));
        } catch (NumberFormatException err) {
            throw new InvalidArgumentException("unmark <index>. Index must be an integer");
        }
    }

    private Command parseDeleteCommand(String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length != 2) {
            throw new InvalidArgumentException("delete <index>");
        }
        try {
            return new DeleteCommand(Integer.parseInt(cmdTokens[1]));
        } catch (NumberFormatException err) {
            throw new InvalidArgumentException("delete <index>. Index must be an integer");
        }
    }

    private Command parseFindCommand(String cmd, String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length < 2) {
            throw new InvalidArgumentException("find <keyword>");
        }
        String keyword = cmd.substring(cmd.indexOf(" ") + 1).trim();
        if (keyword.isEmpty()) {
            throw new InvalidArgumentException("find <keyword>");
        }
        boolean isTag = false;

        if (keyword.startsWith("#")) {
            isTag = true;
            keyword = keyword.substring(1);
            if (keyword.isBlank()) {
                throw new InvalidArgumentException("find #<tag>");
            }
        }

        return new FindCommand(keyword, isTag);
    }

    private Command parseTodoCommand(String cmd, String[] cmdTokens) throws InvalidArgumentException {
        if (cmdTokens.length < 2) {
            throw new InvalidArgumentException("todo <task>");
        }

        String title = cmd.substring(cmd.indexOf(" ") + 1).trim(); //only take <title>
        if (title.isEmpty()) {
            throw new InvalidArgumentException("todo <task>");
        }
        return new TodoCommand(title);
    }

    private Command parseDeadlineCommand(String cmd) throws MomoException {
        if (!cmd.contains(" /by ")) {
            throw new InvalidArgumentException("deadline <task> /by <date>");
        }

        String[] parts = cmd.split(" /by ", 2);
        if (parts.length < 2) {
            throw new InvalidArgumentException("deadline <task> /by <date>");
        }

        String leftString = parts[0].trim(); // left: "deadline <title>"
        String byString = parts[1].trim();

        if (leftString.equals("deadline")) {
            throw new InvalidArgumentException("deadline <task> /by <date>");
        }
        String title = leftString.substring(leftString.indexOf(" ") + 1).trim(); // remove "deadline"

        if (title.isEmpty() || byString.isEmpty()) {
            throw new InvalidArgumentException("deadline <task> /by <date>");
        }

        LocalDateTime byDateTime = parseUserDateTime(byString);
        return new DeadlineCommand(title, byDateTime);
    }

    private Command parseEventCommand(String cmd) throws MomoException {
        if (!cmd.contains(" /from ") || !cmd.contains(" /to ")) {
            throw new InvalidArgumentException("event <task> /from <start_date/time> /to <end_date/time>");
        }

        String[] firstSplit = cmd.split(" /from ", 2);
        if (firstSplit.length < 2) {
            throw new InvalidArgumentException("event <task> /from <start_date/time> /to <end_date/time>");
        }

        String left = firstSplit[0].trim(); // "event <title>""
        String rest = firstSplit[1].trim(); // "<start> /to <end>"

        if (left.equals("event")) {
            throw new InvalidArgumentException("event <task> /from <start_date/time> /to <end_date/time>");
        }
        String title = left.substring(left.indexOf(" ") + 1).trim(); // remove "event"

        String[] secondSplit = rest.split(" /to ", 2);
        if (secondSplit.length < 2) {
            throw new InvalidArgumentException("event <task> /from <start_date/time> /to <end_date/time>");
        }

        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();

        if (title.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InvalidArgumentException("event <task> /from <start_date/time> /to <end_date/time>");
        }

        LocalDateTime fromDateTime = parseUserDateTime(from);
        LocalDateTime endDateTime = parseUserDateTime(to);

        return new EventCommand(title, fromDateTime, endDateTime);
    }

    private Command parseTagCommand(String[] cmdTokens) throws MomoException {
        if (cmdTokens.length != 3) {
            throw new InvalidArgumentException("tag <index> <tag1,tag2,...>");
        }

        int index;
        try {
            index = Integer.parseInt(cmdTokens[1]);
        } catch (NumberFormatException err) {
            throw new InvalidArgumentException("tag <index> <tag1,tag2,...>. Index should be a number");
        }

        String tag = cmdTokens[2].trim();
        if (tag.isEmpty()) {
            throw new InvalidArgumentException("tag <int> <tag1,tag2,...>");
        }

        return new TagCommand(index, tag);
    }

    private Command parseUntagCommand(String[] cmdTokens) throws MomoException {
        if (cmdTokens.length != 3) {
            throw new InvalidArgumentException("untag <index> <tag>");
        }

        int index;
        try {
            index = Integer.parseInt(cmdTokens[1]);
        } catch (NumberFormatException err) {
            throw new InvalidArgumentException("untag <index> <tag>. Index should be a number");
        }

        String tag = cmdTokens[2].trim();
        if (tag.isEmpty()) {
            throw new InvalidArgumentException("untag <int> <tag>");
        }

        return new UntagCommand(index, tag);
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
