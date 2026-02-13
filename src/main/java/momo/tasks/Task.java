package momo.tasks;

/**
 * Represents a generic task with a title and completion status.
 * <p>
 * This class is the base task type that can be extended by more specific task
 * implementations (e.g., todo, deadline, event). It also provides simple
 * serialization support via {@link #toStorageString()}.
 * </p>
 */
public class Task {
    private final String title;
    private boolean isComplete;

    /**
     * Constructs a new task with the given title.
     *
     * @param title Title/description of the task.
     */
    public Task(String title) {
        assert title != null : "title is null";
        assert !title.isBlank() : "title must not be blank";
        this.title = title;
        this.isComplete = false;
    }

    public String getStatusIcon() {
        return (isComplete ? "X" : " ");
    }

    public String getTitle() {
        return this.title;
    }

    public void setComplete() {
        isComplete = true;
    }

    public void setIncomplete() {
        isComplete = false;
    }

    /**
     * Converts this task into a compact storage format.
     * <p>
     * Format: {@code |<status>|<title>} where status is {@code 1} (complete) or {@code 0} (incomplete).
     * </p>
     *
     * @return A string representation suitable for saving to storage.
     */
    public String toStorageString() {
        int status = isComplete ? 1 : 0;
        return ("|" + status + "|" + this.title);
    }

    @Override
    public String toString() {
        return ("[" + this.getStatusIcon() + "] " + this.title);
    }
}
