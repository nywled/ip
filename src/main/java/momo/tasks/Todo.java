/**
 * Todo class is a subclass of Task
 */
package momo.tasks;

public class Todo extends Task {

    public Todo(String title) {
        super(title);
    }

    public Todo(String title, boolean isComplete) {
        super(title, isComplete);
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