package momo.commands;

import momo.exceptions.InvalidArgumentException;
import momo.exceptions.MomoException;
import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.tasks.Todo;
import momo.ui.Ui;

/**
 * Creates a {@link Todo} task and adds it to the task manager.
 */
public class TodoCommand extends Command {
    private final String title;

    /**
     * Constructs a todo command using the provided task title.
     *
     * @param title Title/description of the todo task.
     */
    public TodoCommand(String title) {
        this.title = title.trim();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates a new {@link Todo} task, adds it to the task list,
     * and displays an acknowledgement message.
     * </p>
     *
     * @throws InvalidArgumentException If the title is blank or missing.
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        Task task = new Todo(title);
        taskManager.addTask(task); // autosaves inside TaskManager
        ui.displayAddedTask(task, taskManager.getTaskListSize());

        return false;
    }
}
