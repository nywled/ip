package momo.commands;

import java.time.LocalDateTime;

import momo.storage.TaskManager;
import momo.ui.Ui;
import momo.tasks.Task;
import momo.tasks.Event;

import momo.exceptions.MomoException;
import momo.exceptions.InvalidArgumentException;

public class EventCommand extends Command {
    private final String title;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public EventCommand(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title.trim();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        if (title.isEmpty() || title == null || startDate == null || endDate == null) {
            throw new InvalidArgumentException("event <task> /startDate <start_date> /endDate <end_date>");
        }

        if (endDate.isBefore(startDate)) {
            throw new InvalidArgumentException("event end must be after start");
        }

        Task task = new Event(title, startDate, endDate);
        taskManager.addTask(task); // autosaves inside TaskManager
        ui.addTaskAck(task, taskManager.getTaskListSize());

        return false;
    }
}