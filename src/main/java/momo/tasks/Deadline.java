package momo.tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task. A deadline task has a single due date/time.
 * <p>
 * This class is a concrete {@link Task} subtype that stores a due date/time {@link java.time.LocalDateTime} and
 * customizes string/storage formats by prefixing the base task representation
 * with a type marker and a due date/time.
 * </p>
 */
public class Deadline extends Task {
    private LocalDateTime dueDate;

    /**
     * Constructs a new deadline task.
     *
     * @param title Title/description of the deadline task.
     * @param dueDate Due date/time of the task.
     */
    public Deadline(String title, LocalDateTime dueDate) {
        super(title);
        assert dueDate != null : "dueDate must not be null";
        this.dueDate = dueDate;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation prefixes the base task storage format with {@code "D"} to identify the task as deadline
     * and appends the due date/time value.
     * </p>
     */
    @Override
    public String toStorageString() {
        return ("D" + super.toStorageString() + "|" + this.dueDate.toString());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The returned string is prefixed with {@code "[D]"} to indicate a deadline task
     * and includes a formatted due date/time.
     * If the time component is midnight ({@link LocalTime#MIDNIGHT}), only the date is shown.
     * </p>
     */
    @Override
    public String toString() {
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("MMM dd yyyy");
        DateTimeFormatter dateTimePattern = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

        String formatted;
        if (this.dueDate.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            formatted = dueDate.format(datePattern);
        } else {
            formatted = dueDate.format(dateTimePattern);
        }
        return ("[D]" + super.toString() + " (by: " + formatted + ")");
    }
}
