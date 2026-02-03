/**
 * Event class is a subclass of Task
 */
package momo.tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String title, boolean isComplete, LocalDateTime startDate, LocalDateTime endDate) {
        super(title, isComplete);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toStorageString() {
        return ("E" + super.toStorageString() + "|" + this.startDate.toString() + "|" + this.endDate.toString());
    }

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
