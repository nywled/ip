package momo.tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task. An event task has a start and end date/time.
 * <p>
 * This class is a concrete {@link Task} subtype that stores a start and end {@link java.time.LocalDateTime} and
 * customizes string/storage formats by prefixing the base task representation
 * with a type marker and start/end date/time.
 * </p>
 */
public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs an new event task.
     *
     * @param title Title/description of the event.
     * @param startDate Start date/time of the event.
     * @param endDate End date/time of the event.
     */
    public Event(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation prefixes the base task storage format with {@code "E"} to identify the task as event
     * and appends the start and end date/time values {@code <startDate>|<endDate>}.
     * </p>
     */
    @Override
    public String toStorageString() {
        return ("E" + super.toStorageString() + "|" + this.startDate.toString() + "|" + this.endDate.toString());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The returned string is prefixed with {@code "[E]"} to indicate an event task and includes a formatted time range.
     * If the time component is midnight ({@link LocalTime#MIDNIGHT}), only the date is shown.
     * </p>
     */
    @Override
    public String toString() {
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("MMM dd yyyy");
        DateTimeFormatter dateTimePattern = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

        String formattedStart;
        String formattedEnd;
        if (this.startDate.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            formattedStart = this.startDate.format(datePattern);
        } else {
            formattedStart = this.startDate.format(dateTimePattern);
        }

        if (this.endDate.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            formattedEnd = this.endDate.format(datePattern);
        } else {
            formattedEnd = this.endDate.format(dateTimePattern);
        }
        return ("[E]" + super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")");
    }
}
