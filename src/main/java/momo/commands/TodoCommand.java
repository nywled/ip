package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.storage.TaskManager;
import momo.tasks.Task;
import momo.tasks.Todo;
import momo.ui.Ui;

public class TodoCommand extends Command {
    private final String title;

    public TodoCommand(String title) {
        this.title = title.trim();
    }

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (title.isEmpty() || title == null) {
            throw new InvalidArgumentException("todo <task>");
        }

        Task task = new Todo(title);
        taskManager.addTask(task); // autosaves inside TaskManager
        ui.addTaskAck(task, taskManager.getTaskListSize());

        return false;
    }
}
