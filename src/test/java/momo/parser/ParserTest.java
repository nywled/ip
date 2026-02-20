package momo.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void parse_listWithExtraSpaces_returnsListCommand() throws MomoException {
        Command c = parser.parse("   list     ");
        assertTrue(c instanceof ListCommand, "Expected ListCommand for input with extra spaces");
    }

    //Invalid input
    @Test
    public void parse_listWithExtraArgs_throwsInvalidArgument() {
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
    public void parse_byeWithExtraArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("bye bye"),
                "Expected InvalidArgumentException when bye has extra arguments");
    }

    //------Mark------
    //Valid input
    @Test
    public void parse_markValidIndex_returnsMarkCommand() throws MomoException {
        Command c = parser.parse("mark 2");
        assertTrue(c instanceof MarkCommand, "Expected MarkCommand for input: mark 2");
    }

    //Invalid input
    @Test
    public void parse_markMissingIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("mark"),
                "Expected InvalidArgumentException when mark has missing arguments");
    }

    @Test
    public void parse_markNonInteger_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("mark two"),
                "Expected InvalidArgumentException when mark has wrong formatted arguments");
    }

    //------Unmark-----
    //Valid input
    @Test
    public void parse_unmarkValidIndex_returnsUnmarkCommand() throws MomoException {
        Command c = parser.parse("unmark 1");
        assertTrue(c instanceof UnmarkCommand, "Expected UnmarkCommand for input: unmark 1");
    }

    //Invalid input
    @Test
    public void parse_unmarkMissingIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("unmark"),
                "Expected InvalidArgumentException when unmark has missing arguments");
    }

    @Test
    public void parse_unmarkNonInteger_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("unmark five"),
                "Expected InvalidArgumentException when unmark has wrong formatted arguments");
    }

    //------Delete-----
    //Valid input
    @Test
    public void parse_deleteValidIndex_returnsDeleteCommand() throws MomoException {
        Command c = parser.parse("delete 3");
        assertTrue(c instanceof DeleteCommand, "Expected DeleteCommand for input: delete 3");
    }

    //Invalid input
    @Test
    public void parse_deleteMissingIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("delete"),
                "Expected InvalidArgumentException when delete has missing arguments");
    }

    @Test
    public void parse_deleteNonInteger_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("delete five"),
                "Expected InvalidArgumentException when delete has wrong formatted arguments");
    }

    //------Todo-----
    //Valid input
    @Test
    public void parse_todoWithTitle_returnsTodoCommand() throws MomoException {
        Command c = parser.parse("todo read book");
        assertTrue(c instanceof TodoCommand, "Expected TodoCommand for valid todo input");
    }

    //Invalid input
    @Test
    public void parse_todoMissingTitle_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("todo"),
                "Expected InvalidArgumentException when todo has no task");
        assertThrows(InvalidArgumentException.class, () -> parser.parse("todo     "),
                "Expected InvalidArgumentException when todo task is empty");
    }

    //-------DEADLINE-------
    //Valid input
    @Test
    public void parse_deadlineDateOnly_returnsDeadlineCommand() throws MomoException {
        Command c = parser.parse("deadline submit report /by 2026-02-01");
        assertTrue(c instanceof DeadlineCommand, "Expected DeadlineCommand for valid date-only deadline");
    }

    @Test
    public void parse_deadlineDateTime_returnsDeadlineCommand() throws MomoException {
        Command c = parser.parse("deadline submit report /by 2026-02-01 0930");
        assertTrue(c instanceof DeadlineCommand, "Expected DeadlineCommand for valid date-time deadline");
    }

    //Invalid input
    @Test
    public void parse_deadlineMissingBy_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline submit report"),
            "Expected InvalidArgumentException when deadline is missing /by");
    }

    @Test
    public void parse_deadlineEmptyTask_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline  /by 2026-02-01"),
                "Expected InvalidArgumentException when deadline task is empty");
    }

    @Test
    public void parse_deadlineNoTitleButHasBy_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline /by 2026-02-01"));
    }

    @Test
    public void parse_deadlineWrongDelimiterSpacing_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline task/by 2026-02-01"));
        assertThrows(InvalidArgumentException.class, () -> parser.parse("deadline task /by2026-02-01"));
    }

    @Test
    public void parse_deadlineInvalidDate_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () -> parser.parse("deadline submit report /by 2026-13-01"),
                "Expected InvalidDateTimeException for invalid deadline date");
    }

    @Test
    public void parse_deadlineInvalidTime_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () -> parser.parse("deadline submit report /by 2026-02-01 2460"),
                "Expected InvalidDateTimeException for invalid deadline time");
    }

    @Test
    public void parse_deadlineWrongTimeFormat_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () -> parser.parse("deadline submit report /by 2026-02-01 9.30PM"),
                "Expected InvalidDateTimeException for wrong deadline time format");
    }

    @Test
    public void parse_deadlineWrongDateFormat_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () -> parser.parse("deadline submit report /by 01/02/2026 0930"),
                "Expected InvalidDateTimeException for wrong deadline date format");
    }

    //-------EVENT-------
    //Valid input
    @Test
    public void parse_eventDateOnly_returnsEventCommand() throws MomoException {
        Command c = parser.parse("event party /from 2026-02-01 /to 2026-02-02");
        assertTrue(c instanceof EventCommand, "Expected EventCommand for valid date-only event");
    }

    @Test
    public void parse_eventDateTime_returnsEventCommand() throws MomoException {
        Command c = parser.parse("event party /from 2026-02-01 0900 /to 2026-02-01 1100");
        assertTrue(c instanceof EventCommand, "Expected EventCommand for valid date-time event");
    }

    //Invalid input
    @Test
    public void parse_eventEmptyTask_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () ->
                parser.parse("event  /from 2026-02-01 0900 /to 2026-02-01 1100"),
                "Expected InvalidArgumentException when event task is empty");
    }

    @Test
    public void parse_eventNoTitleButHasFromTo_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () ->
                parser.parse("event /from 2026-02-01 0900 /to 2026-02-01 1100"));
    }

    @Test
    public void parse_eventWrongDelimiterSpacing_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () ->
                parser.parse("event party/from 2026-02-01 /to 2026-02-02"));
        assertThrows(InvalidArgumentException.class, () ->
                parser.parse("event party /from 2026-02-01/to 2026-02-02"));
    }

    @Test
    public void parse_eventMissingFromTo_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("event party"),
                "Expected InvalidArgumentException when event is missing /from and /to");
        assertThrows(InvalidArgumentException.class, () -> parser.parse("event party /from 2026-02-01"),
                "Expected InvalidArgumentException when event is missing /to");
        assertThrows(InvalidArgumentException.class, () -> parser.parse("event party /to 2026-02-01"),
                "Expected InvalidArgumentException when event is missing /from");
    }

    @Test
    public void parse_eventInvalidDate_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () ->
                parser.parse("event party /from 2026-13-01 /to 2026-02-01"),
                "Expected InvalidDateTimeException for invalid event date");
    }

    @Test
    public void parse_eventInvalidTime_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () ->
                parser.parse("event party /from 2026-02-01 2460 /to 2026-02-01 1100"),
                "Expected InvalidDateTimeException for invalid event time");
    }

    @Test
    public void parse_eventWrongDateFormat_throwsInvalidDateTime() {
        assertThrows(InvalidDateTimeException.class, () ->
                parser.parse("event party /from 2026/02/01 0900 /to 2026-02-01 1100"),
                "Expected InvalidDateTimeException for wrong event date format");
    }

    //-------FIND-------
    // Valid input
    @Test
    public void parse_findKeyword_returnsFindCommand() throws MomoException {
        Command c = parser.parse("find book");
        assertTrue(c instanceof FindCommand, "Expected FindCommand for input: find book");
    }

    @Test
    public void parse_findKeywordWithSpaces_returnsFindCommand() throws MomoException {
        Command c = parser.parse("find read book");
        assertTrue(c instanceof FindCommand, "Expected FindCommand for input: find read book");
    }

    @Test
    public void parse_findTag_returnsFindCommand() throws MomoException {
        Command c = parser.parse("find #school");
        assertTrue(c instanceof FindCommand, "Expected FindCommand for input: find #school");
    }

    @Test
    public void parse_findTagWithSpaces_returnsFindCommand() throws MomoException {
        Command c = parser.parse("find #very Important Tag");
        assertTrue(c instanceof FindCommand, "Expected FindCommand for input: find #very Important Tag");
    }

    // Invalid input
    @Test
    public void parse_findMissingKeyword_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("find"),
                "Expected InvalidArgumentException when find has missing keyword");
    }

    @Test
    public void parse_findBlankKeyword_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("find     "),
                "Expected InvalidArgumentException when find keyword is blank");
    }

    @Test
    public void parse_findHashOnly_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("find #"),
                "Expected InvalidArgumentException when find tag is missing");
    }

    @Test
    public void parse_findHashThenSpaces_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("find #     "),
                "Expected InvalidArgumentException when find tag is blank");
    }

    //-------TAG-------
    // Valid input
    @Test
    public void parse_tagValid_returnsTagCommand() throws MomoException {
        Command c = parser.parse("tag 1 school");
        assertTrue(c instanceof TagCommand, "Expected TagCommand for input: tag 1 school");
    }

    @Test
    public void parse_tagMultipleTags_returnsTagCommand() throws MomoException {
        Command c = parser.parse("tag 2 work,urgent,cs");
        assertTrue(c instanceof TagCommand, "Expected TagCommand for input: tag 2 work,urgent,cs");
    }

    @Test
    public void parse_tagWithExtraSpaces_returnsTagCommand() throws MomoException {
        Command c = parser.parse("   tag   2   work,urgent   ");
        assertTrue(c instanceof TagCommand);
    }

    // Invalid input
    @Test
    public void parse_tagMissingArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("tag"),
                "Expected InvalidArgumentException when tag has missing args");
    }

    @Test
    public void parse_tagMissingTag_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("tag 1"),
                "Expected InvalidArgumentException when tag is missing");
    }

    @Test
    public void parse_tagExtraArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("tag 1 a b"),
                "Expected InvalidArgumentException when tag has extra args (parser expects exactly 3 tokens)");
    }

    @Test
    public void parse_tagMultipleTagsWithSpace_returnsTagCommand() throws MomoException {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("tag 2 work, urgent, cs"),
                "Expected InvalidArgumentException when multiple tags have space (parser expects no spaces)");
    }

    @Test
    public void parse_tagNonIntegerIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("tag one school"),
                "Expected InvalidArgumentException when tag index is not an integer");
    }

    @Test
    public void parse_tagBlankTagToken_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("tag 1     "),
                "Expected InvalidArgumentException when tag token is blank/missing");
    }

    //-------UNTAG-------
    // Valid input
    @Test
    public void parse_untagValid_returnsUntagCommand() throws MomoException {
        Command c = parser.parse("untag 1 school");
        assertTrue(c instanceof UntagCommand, "Expected UntagCommand for input: untag 1 school");
    }

    // Invalid input
    @Test
    public void parse_untagMissingArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("untag"),
                "Expected InvalidArgumentException when untag has missing args");
    }

    @Test
    public void parse_untagMissingTag_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("untag 1"),
                "Expected InvalidArgumentException when untag tag is missing");
    }

    @Test
    public void parse_untagExtraArgs_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("untag 1 a b"),
                "Expected InvalidArgumentException when untag has extra args (parser expects exactly 3 tokens)");
    }

    @Test
    public void parse_untagNonIntegerIndex_throwsInvalidArgument() {
        assertThrows(InvalidArgumentException.class, () -> parser.parse("untag one school"),
                "Expected InvalidArgumentException when untag index is not an integer");
    }
}
