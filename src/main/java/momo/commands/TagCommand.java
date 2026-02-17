package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

public class TagCommand extends Command {
    private final int index;
    private final String tag;

    public TagCommand(int index, String tag) {
        this.index = index - 1;
        this.tag = tag;
    }


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

