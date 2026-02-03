/**
 * Deadline class is a subclass of Task
 */
package momo.tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime dueDate;

    public Deadline(String title, LocalDateTime dueDate) {
        super(title);
        this.dueDate = dueDate;
    }

    public Deadline(String title, boolean isComplete, LocalDateTime dueDate) {
        super(title, isComplete);
        this.dueDate = dueDate;
    }

    @Override
    public String toStorageString() {
        return ("D" + super.toStorageString() + "|" + this.dueDate.toString());
    }

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
