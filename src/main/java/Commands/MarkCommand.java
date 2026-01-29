package commands;

import momo.TaskManager;
import momo.Ui;

import tasks.Task;

import exceptions.MomoException;
import exceptions.InvalidArgumentException;

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