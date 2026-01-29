package commands;

import momo.TaskManager;
import momo.Ui;

import tasks.Task;

import exceptions.MomoException;
import exceptions.InvalidArgumentException;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (index < 0 || index >= taskManager.getTaskListSize()) {
            throw new InvalidArgumentException("unmark <1-" + taskManager.getTaskListSize() + ">");
        }

        Task task = taskManager.getTask(index);
        task.setIncomplete();
        taskManager.save();

        ui.showUnmarkTask(task);
        return false;
    }
}