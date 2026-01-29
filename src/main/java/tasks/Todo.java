/**
 * Todo class is a subclass of Task
 */
package tasks;

public class Todo extends Task {
    public Todo(String title) {
        super(title);
    }

    @Override
    public String toStorageString() {
        return ("T" + super.toStorageString());
    }

    @Override
    public String toString() {
        return ("[T]" + super.toString());
    }
}