package commands;

import momo.TaskManager;
import momo.Ui;

import tasks.Task;
import tasks.Todo;

import exceptions.MomoException;
import exceptions.InvalidArgumentException;

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