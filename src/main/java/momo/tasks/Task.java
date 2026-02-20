package momo.tasks;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a generic task with a title and completion status.
 * <p>
 * This class is the base task type that can be extended by more specific task
 * implementations (e.g., todo, deadline, event). It also provides simple
 * serialization support via {@link #toStorageString()}.
 * </p>
 */
public class Task {
    private static final int COMPLETE_STATUS = 1;
    private static final int INCOMPLETE_STATUS = 0;
    private static final String COMPLETE_SYMBOL = "X";
    private static final String INCOMPLETE_SYMBOL = " ";
    private static final String ESC_BACKSLASH = "%5C";
    private static final String ESC_PIPE = "%7C";
    private static final String ESC_COMMA = "%2C";

    private final String title;
    private boolean isComplete;
    private final Set<String> tags;

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
        this.tags = new HashSet<>();
    }

    public String getStatusIcon() {
        return (this.isComplete ? COMPLETE_SYMBOL : INCOMPLETE_SYMBOL);
    }

    /**
     * Checks whether the task title contains the specified keyword.
     * <p>
     * The comparison is case-insensitive.
     * </p>
     *
     * @param keyword The keyword to search for.
     * @return {@code true} if the title contains the keyword, otherwise {@code false}.
     */
    public boolean containsKeyword(String keyword) {
        return this.title.toLowerCase().contains(keyword.toLowerCase());
    }

    public void setComplete() {
        this.isComplete = true;
    }

    public void setIncomplete() {
        this.isComplete = false;
    }

    /**
     * Adds a tag to this task.
     *
     * @param tag The tag to be added.
     */
    public void addTag(String tag) {
        assert tag != null : "tag is null";
        assert !tag.isBlank() : "tag must not be blank";
        this.tags.add(tag);
    }

    /**
     * Removes a tag from this task.
     *
     * @param tag The tag to be removed.
     */
    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    /**
     * Checks whether this task has the specified tag.
     *
     * @param tag The tag to check for.
     * @return {@code true} if the task contains the tag, otherwise {@code false}.
     */
    public boolean hasTag(String tag) {
        return this.tags.contains(tag);
    }

    /**
     * Converts this task into a compact storage format.
     * <p>
     * Format: {@code |<status>|<title>|<tags>} where status is {@code 1} (complete) or {@code 0} (incomplete).
     * </p>
     *
     * @return A string representation suitable for saving to storage.
     */
    public String toStorageString() {
        int status = this.isComplete ? COMPLETE_STATUS : INCOMPLETE_STATUS;
        String safeTitle = escapeField(this.title);

        String safeTags = tags.stream()
                .sorted()
                .map(this::escapeField)
                .collect(Collectors.joining(", "));

        return ("|" + status + "|" + safeTitle + "|" + safeTags);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getStatusIcon()).append("] ").append(title);
        if (!tags.isEmpty()) {
            sb.append(" (tags: ");
            sb.append(getSortedTagsString());
            sb.append(")");
        }
        return sb.toString();
    }

    private String getSortedTagsString() {
        String sortedTags = tags.stream().sorted()
                .collect(Collectors.joining(", "));
        return sortedTags;
    }

    private String escapeField(String s) {
        if (s == null) {
            return null;
        }
        return s.replace("\\", ESC_BACKSLASH)
                .replace("|", ESC_PIPE)
                .replace(",", ESC_COMMA);
    }
}
