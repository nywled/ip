package momo.commands;

import java.time.LocalDateTime;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Event;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Creates an {@link Event} task with a start and end date/time and adds it to the task manager.
 */
public class EventCommand extends Command {
    private final String title;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Constructs an event command using the provided title and start/end date/time values.
     *
     * @param title Title/description of the event.
     * @param startDate Start date/time of the event.
     * @param endDate End date/time of the event.
     */
    public EventCommand(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title.trim();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates the input, ensures the end date/time is not before the start date/time,
     * creates a new {@link Event} task, adds it to the task list, and displays an acknowledgement message.
     * </p>
     *
     * @throws InvalidArgumentException If the title is blank/missing, any date/time is missing,
     *         or the end date/time is before the start date/time.
     */
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
