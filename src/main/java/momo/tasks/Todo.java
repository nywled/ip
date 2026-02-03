package momo.tasks;

/**
 * Represents a todo task. A todo task is a task without any associated date/time.
 * <p>
 * This class is a concrete {@link Task} subtype and customizes string/storage formats
 * by prefixing the base task representation with a type marker.
 * </p>
 */
public class Todo extends Task {

    /**
     * Constructs a new todo task
     *
     * @param title Title/description of the todo.
     */
    public Todo(String title) {
        super(title);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation prefixes the base task storage format with {@code "T"}
     * to identify the task as a todo.
     * </p>
     */
    @Override
    public String toStorageString() {
        return ("T" + super.toStorageString());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The returned string is prefixed with {@code "[T]"} to indicate a todo task.
     * </p>
     */
    @Override
    public String toString() {
        return ("[T]" + super.toString());
    }
}