package momo.commands;

import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Signals the controller to exit the main execution loop.
 */
public class ExitCommand extends Command {

    /**
     * {@inheritDoc}
     * <p>
     * Displays a goodbye message and signals the controller
     * to terminate the application.
     * </p>
     */
    @Override
    public boolean execute(TaskManager taskManager, Ui ui) {
        ui.showGoodbye();
        return true; // signal Controller to terminate loop
    }
}
