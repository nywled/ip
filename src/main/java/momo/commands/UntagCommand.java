package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Removes a tag from an existing task.
 */
public class UntagCommand extends Command {
    private final int index;
    private final String tag;

    /**
     * Constructs a UntagCommand with the specified task index and tag.
     *
     * @param index The 1-based index of the task to untag.
     * @param tag The tag to be removed from the task.
     */
    public UntagCommand(int index, String tag) {
        this.index = index - 1;
        this.tag = tag;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes the specified tag from the selected task and persists the updated task list.
     * </p>
     *
     * @throws MomoException If an error occurs during command execution.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("untag <1-" + taskManager.getTaskListSize() + "> " + this.tag);
        }

        Task task = taskManager.getTask(index);
        task.removeTag(tag);
        taskManager.save();

        ui.showTagRemoved(task);
        return false;
    }
}
