package momo.commands;

import java.time.LocalDateTime;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Deadline;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Creates a {@link Deadline} task and adds it to the task list.
 */
public class DeadlineCommand extends Command {
    private final String title;
    private final LocalDateTime dueDate;

    /**
     * Constructs a deadline command using the provided title and due date/time.
     *
     * @param title Title/description of the deadline task.
     * @param dueDate Due date/time of the deadline task.
     */
    public DeadlineCommand(String title, LocalDateTime dueDate) {
        this.title = title.trim();
        this.dueDate = dueDate;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates the input, creates a new {@link Deadline} task, adds it to the task list,
     * and displays an acknowledgement message.
     * </p>
     *
     * @throws InvalidArgumentException If the title is blank/missing or the due date is missing.
     */
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