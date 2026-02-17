package momo.commands;

import java.util.ArrayList;
import java.util.stream.Collectors;

import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Command that searches for tasks whose titles contain a given keyword.
 * <p>
 * Scans through all existing tasks managed by {@code TaskManager} and collects
 * tasks whose titles contain the specified keyword. The matching tasks
 * are then displayed to the user via the {@code Ui}.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a find command using the provided keyword.
     *
     * @param keyword The keyword used to search for matching task titles.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Finds the tasks and displays the results if any
     * </p>
     *
     * @throws MomoException If an error occurs during command execution.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        ArrayList<Task> found = taskManager.getTasks().stream()
            .filter(task -> task.getTitle().contains(keyword))
            .collect(Collectors.toCollection(ArrayList::new));

        ui.showMatchingTaskList(found);
        return false;
    }
}
