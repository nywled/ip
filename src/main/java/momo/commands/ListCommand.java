package momo.commands;

import momo.exceptions.MomoException;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Lists all tasks currently managed by the application.
 */
public class ListCommand extends Command {

    /**
     * {@inheritDoc}
     * <p>
     * Displays the task list using {@link Ui#showTaskList(TaskManager)}.
     * </p>
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        ui.showTaskList(taskManager);
        return false;
    }
}
