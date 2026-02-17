package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Adds a tag to an existing task.
 */
public class TagCommand extends Command {
    private final int index;
    private final String tag;

    /**
     * Constructs a TagCommand with the specified task index and tag.
     *
     * @param index The 1-based index of the task to tag.
     * @param tag The tag to be added to the task.
     */
    public TagCommand(int index, String tag) {
        this.index = index - 1;
        this.tag = tag;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds the specified tag to the selected task and persists the updated task list.
     * </p>
     *
     * @throws MomoException If an error occurs during command execution.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("tag <1-" + taskManager.getTaskListSize() + "> " + this.tag);
        }

        Task task = taskManager.getTask(index);
        task.addTag(tag);
        taskManager.save(); //Must save as getTask() and setComplete() does not auto save

        ui.showTagAdded(task);
        return false;
    }
}

