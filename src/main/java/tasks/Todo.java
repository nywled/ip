/**
 * Todo class is a subclass of Task
 */
package tasks;

public class Todo extends Task {
    protected String startDate;
    protected String endDate;

    public Todo(String title) {
        super(title);
    }

    @Override
    public String toString() {
        return ("[T]" + super.toString());
    }
}