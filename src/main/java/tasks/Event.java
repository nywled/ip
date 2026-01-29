/**
 * Event class is a subclass of Task
 */
package tasks;

public class Event extends Task {
    private String startDate;
    private String endDate;

    public Event(String title, String startDate, String endDate) {
        super(title);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toStorageString() {
        return ("E" + super.toStorageString() + "|" + this.startDate + "|" + this.endDate);
    }

    @Override
    public String toString() {
        return ("[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")");
    }
}