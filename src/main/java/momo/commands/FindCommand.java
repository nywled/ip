package momo.commands;

import java.util.ArrayList;

import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Searches for tasks whose titles or tags contain a given keyword.
 * <p>
 * Scans through all existing tasks managed by {@code TaskManager} and collects
 * tasks whose titles or tags contain the specified keyword. The matching tasks
 * are then displayed to the user via the {@code Ui}.
 */
public class FindCommand extends Command {
    private final String keyword;
    private final boolean isTag;

    /**
     * Constructs a find command using the provided keyword.
     *
     * @param keyword The keyword used to search for matching task titles.
     */
    public FindCommand(String keyword, boolean isTag) {
        this.keyword = keyword;
        this.isTag = isTag;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns and displays the results if any of the search base on title or tags.
     * </p>
     *
     * @throws MomoException If an error occurs during command execution.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        ArrayList<Task> searchResults = new ArrayList<>();

        if (isTag) { //If command is searching for a tag
            searchResults = taskManager.findTasksByTag(keyword);
        } else {
            searchResults = taskManager.findTasksByTitle(keyword);
        }

        ui.showMatchingTaskList(searchResults);
        return false;
    }
}
