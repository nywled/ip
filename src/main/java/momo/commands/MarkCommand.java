package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Marks a specified task as completed and saves to storage the updated task list.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Constructs a mark command for the given task index.
     *
     * @param index 1-based index of the task as entered by the user.
     */
    public MarkCommand(int index) {
        this.index = index - 1;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that the index is within bounds, marks the task as complete,
     * saves the updated task list, and displays a confirmation message.
     * </p>
     *
     * @throws InvalidArgumentException If the index is out of bounds.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("mark <1-" + taskManager.getTaskListSize() + ">");
        }

        Task task = taskManager.getTask(index);
        task.setComplete();
        taskManager.save(); //Must save

        ui.showMarkTask(task);
        return false;
    }
}