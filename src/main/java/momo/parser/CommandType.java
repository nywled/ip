package momo.parser;

/**
 * Defines the set of command keywords supported by the parser. Each value represents
 * a user command that is converted to an executable command
 */
public enum CommandType {
    /** Lists all tasks. */
    LIST,
    /** Exits the application. */
    BYE,
    /** Marks a task as completed. */
    MARK,
    /** Marks a task as incomplete. */
    UNMARK,
    /** Deletes a task. */
    DELETE,
    /** Adds a todo task. */
    TODO,
    /** Adds a deadline task. */
    DEADLINE,
    /** Adds an event task. */
    EVENT,
    /** Finds all matching tasks */
    FIND,
    /** Tags a task*/
    TAG,
    /** Untags a task */
    UNTAG,
}
