package momo.commands;

import java.util.ArrayList;

import momo.exceptions.MomoException;
import momo.storage.TaskManager;
import momo.tasks.Task;
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
    private ArrayList<Task> found;

    /**
     * Constructs a find command using the provided keyword.
     *
     * @param keyword The keyword used to search for matching task titles.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
        this.found = new ArrayList<>();
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
        for (int i = 0; i < taskManager.getTaskListSize(); i++) {
            Task task = taskManager.getTask(i);
            String taskTitle = task.getTitle();
            if (taskTitle.contains(keyword)) {
                found.add(task);
            }
        }
        ui.showMatchingTaskList(found);
        return false;
    }
}
