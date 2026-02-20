package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Deletes a specified task and saves to storage the updated task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a delete command for the given task index.
     *
     * @param index 1-based index of the task as entered by the user.
     */
    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that the index is within bounds, removes the task,
     * saves the updated task list, and displays a confirmation message.
     * </p>
     *
     * @throws InvalidArgumentException If the index is out of bounds.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("delete <1-" + taskManager.getTaskListSize() + ">");
        }

        Task removedTask = taskManager.removeTask(index);
        taskManager.save(); //task manager removeTask does not auto save

        ui.showDeleteTask(removedTask, taskManager.getTaskListSize());
        return false;
    }
}
