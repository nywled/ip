package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.storage.TaskManager;
import momo.tasks.Task;
import momo.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("delete <1-" + taskManager.getTaskListSize() + ">");
        }

        Task removedTask = taskManager.removeTask(index);
        taskManager.save();

        ui.showDeleteTask(removedTask, taskManager.getTaskListSize());
        return false;
    }
}
