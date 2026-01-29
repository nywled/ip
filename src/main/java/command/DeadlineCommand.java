package commands;

import java.time.LocalDateTime;

import momo.TaskManager;
import momo.Ui;

import tasks.Task;
import tasks.Deadline;

import exceptions.MomoException;
import exceptions.InvalidArgumentException;

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