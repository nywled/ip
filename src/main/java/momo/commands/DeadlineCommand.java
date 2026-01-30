package momo.commands;

import java.time.LocalDateTime;

import momo.storage.TaskManager;
import momo.ui.Ui;
import momo.tasks.Task;
import momo.tasks.Deadline;

import momo.exceptions.MomoException;
import momo.exceptions.InvalidArgumentException;

public class DeadlineCommand extends Command {
    private final String title;
    private final LocalDateTime dueDate;

    public DeadlineCommand(String title, LocalDateTime dueDate) {
        this.title = title.trim();
        this.dueDate = dueDate;
    }

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (title.isEmpty() || title == null || dueDate == null) {
            throw new InvalidArgumentException("deadline <task> /by <date>");
        }

        Task task = new Deadline(title, dueDate);
        taskManager.addTask(task); // autosaves inside TaskManager
        ui.addTaskAck(task, taskManager.getTaskListSize());

        return false;
    }
}