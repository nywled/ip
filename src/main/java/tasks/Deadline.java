/**
 * Deadline class is a subclass of Task
 */
package tasks;

public class Deadline extends Task {
    private String dueDate;

    public Deadline(String title, String dueDate) {
        super(title);
        this.dueDate = dueDate;
    }

    public Deadline(String title, boolean isComplete, String dueDate) {
        super(title, isComplete);
        this.dueDate = dueDate;
    }

    @Override
    public String toStorageString() {
        return ("D" + super.toStorageString() + "|" + this.dueDate);
    }

    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: " + dueDate + ")");
    }
}