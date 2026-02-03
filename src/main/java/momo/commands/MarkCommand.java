package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.storage.TaskManager;
import momo.tasks.Task;
import momo.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("mark <1-" + taskManager.getTaskListSize() + ">");
        }

        Task task = taskManager.getTask(index);
        task.setComplete();
        taskManager.save();

        ui.showMarkTask(task);
        return false;
    }
}
