package momo.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


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


public class ParserTest {
     private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    //-------Basic invalid input validation------
    @Test
    public void parse_null_throwsInvalidCommandException() {
        assertThrows(InvalidCommandException.class, () -> parser.parse(null), 
                "Expected InvalidCommandException when input is null");
    }

    @Test
    public void parse_blank_throwsInvalidCommandException() {
        assertThrows(InvalidCommandException.class, () -> parser.parse(" "),
                "Expected InvalidCommandException for blank input");
    }

    @Test
    public void parse_unknownCommand_throwsInvalidCommandException() {
        assertThrows(InvalidCommandException.class, () -> parser.parse("hello"),
                "Expected InvalidCommandException for unknown command");
    }

    //-------List-------
    //Valid input
    @Test
    public void parse_list_returnsListCommand() throws MomoException {
        Command c = parser.parse("list");
        assertTrue(c instanceof ListCommand, "Expected ListCommand for input: list");
    }

    //Invalid input
    @Test
    public void parse_list_withExtraArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("list extra"),
                "Expected InvalidArgumentException when list has extra arguments");
    }

    //-------Bye-------
    //Valid input
    @Test
    public void parse_bye_returnsExitCommand() throws MomoException {
        Command c = parser.parse("bye");
        assertTrue(c instanceof ExitCommand, "Expected ByeCommand for input: bye");
    }

    //Invalid input
    @Test
    public void parse_bye_withExtraArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("bye bye"),
                "Expected InvalidArgumentException when bye has extra arguments");
    }

    //------Mark------
    //Valid input
    @Test
    public void parse_mark_validIndex_returnsMarkCommand() throws MomoException {
        Command c = parser.parse("mark 2");
        assertTrue(c instanceof MarkCommand, "Expected MarkCommand for input: mark 2");
    }
    
    //Invalid input
    @Test
    public void parse_mark_missingIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("mark"),
                "Expected InvalidArgumentException when mark has missing arguments");
    }

    @Test
    public void parse_mark_nonInteger_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("mark two"),
                "Expected InvalidArgumentException when mark has wrong formatted arguments");
    }

    //------Unmark-----
    //Valid input
    @Test
    public void parse_unmark_validIndex_returnsUnmarkCommand() throws MomoException {
        Command c = parser.parse("unmark 1");
        assertTrue(c instanceof UnmarkCommand, "Expected UnmarkCommand for input: unmark 1");
    }

    //Invalid input
    @Test
    public void parse_unmark_missingIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("unmark"),
                "Expected InvalidArgumentException when unmark has missing arguments");
    }

    @Test
    public void parse_unmark_nonInteger_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("unmark five"),
                "Expected InvalidArgumentException when unmark has wrong formatted arguments");
    }

    //------Delete-----
    //Valid input
    @Test
    public void parse_delete_validIndex_returnsDeleteCommand() throws MomoException {
        Command c = parser.parse("delete 3");
        assertTrue(c instanceof DeleteCommand, "Expected DeleteCommand for input: delete 3");
    }

    //Invalid input
    @Test
    public void parse_delete_missingIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("delete"),
                "Expected InvalidArgumentException when delete has missing arguments");
    }

    @Test
    public void parse_delete_nonInteger_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("delete five"),
                "Expected InvalidArgumentException when delete has wrong formatted arguments");
    }

    //------TODO-----
    //Valid input
    @Test
    public void parse_todo_withTitle_returnsTodoCommand() throws MomoException {
        Command c = parser.parse("todo read book");
        assertTrue(c instanceof TodoCommand, "Expected TodoCommand for valid todo input");
    }

    //Invalid input
    @Test
    public void parse_todo_missingTitle_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("todo"), 
                "Expected InvalidArgumentException when todo has no task");
        assertThrows(InvalidArgumentException.class, () -> parser.parse("todo     "),
                "Expected InvalidArgumentException when todo task is empty");
    }

    //-------DEADLINE-------
    //Valid input
    @Test
    public void parse_deadline_dateOnly_returnsDeadlineCommand() throws MomoException {
        Command c = parser.parse("deadline submit report /by 2026-02-01");
        assertTrue(c instanceof DeadlineCommand, "Expected DeadlineCommand for valid date-only deadline");
    }

    @Test
    public void parse_deadline_dateTime_returnsDeadlineCommand() throws MomoException {
        Command c = parser.parse("deadline submit report /by 2026-02-01 0930");
        assertTrue(c instanceof DeadlineCommand, "Expected DeadlineCommand for valid date-time deadline");
    }
    
    //Invalid input
    @Test
    public void parse_deadline_missingBy_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline submit report"),
            "Expected InvalidArgumentException when deadline is missing /by");
    }

    @Test
    public void parse_deadline_emptyTask_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline  /by 2026-02-01"),
                "Expected InvalidArgumentException when deadline task is empty");
    }


    @Test
    public void parse_deadline_invalidDate_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("deadline submit report /by 2026-13-01"),
                "Expected InvalidDateTimeException for invalid deadline date");
    }

    @Test
    public void parse_deadline_invalidTime_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("deadline submit report /by 2026-02-01 2460"),
                "Expected InvalidDateTimeException for invalid deadline time");
    }

    @Test
    public void parse_deadline_wrongTimeFormat_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("deadline submit report /by 2026-02-01 9.30PM"),
                "Expected InvalidDateTimeException for wrong deadline time format");
    }

    @Test
    public void parse_deadline_wrongDateFormat_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("deadline submit report /by 01/02/2026 0930"),
                "Expected InvalidDateTimeException for wrong deadline date format");
    }

    //-------EVENT-------
    //Valid input
    @Test
    public void parse_event_dateOnly_returnsEventCommand() throws MomoException {
        Command c = parser.parse("event party /from 2026-02-01 /to 2026-02-02");
        assertTrue(c instanceof EventCommand, "Expected EventCommand for valid date-only event");
    }

    @Test
    public void parse_event_dateTime_returnsEventCommand() throws MomoException {
        Command c = parser.parse("event party /from 2026-02-01 0900 /to 2026-02-01 1100");
        assertTrue(c instanceof EventCommand, "Expected EventCommand for valid date-time event");
    }

    //Invalid input
    @Test
    public void parse_event_emptyTask_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, 
                () -> parser.parse("event  /from 2026-02-01 0900 /to 2026-02-01 1100"),
                "Expected InvalidArgumentException when event task is empty");
    }
  
    @Test
    public void parse_event_missingFromTo_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("event party"),
                "Expected InvalidArgumentException when event is missing /from and /to");
        assertThrows(InvalidArgumentException.class, () -> parser.parse("event party /from 2026-02-01"),
                "Expected InvalidArgumentException when event is missing /to");
        assertThrows(InvalidArgumentException.class, () -> parser.parse("event party /to 2026-02-01"),
                "Expected InvalidArgumentException when event is missing /from");
    }

    @Test
    public void parse_event_invalidDate_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("event party /from 2026-13-01 /to 2026-02-01"),
                "Expected InvalidDateTimeException for invalid event date");
    }

    @Test
    public void parse_event_invalidTime_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("event party /from 2026-02-01 2460 /to 2026-02-01 1100"),
                "Expected InvalidDateTimeException for invalid event time");
    }

    @Test
    public void parse_event_wrongDateFormat_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class,
                () -> parser.parse("event party /from 2026/02/01 0900 /to 2026-02-01 1100"),
                "Expected InvalidDateTimeException for wrong event date format");
    }
}
