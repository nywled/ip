package momo.commands;

import momo.exceptions.MomoException;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Base type for all executable user commands in the application.
 * <p>
 * Each concrete command represents an action to be performed on the application's
 * state and is executed with access to a {@code TaskManager} and {@code Ui}.
 * </p>
 */
public abstract class Command {

    /**
     * Executes the command using the given task manager and UI.
     *
     * @param taskManager Task manager that provides access to and modifies the tasks.
     * @param ui UI handler used to display messages to the user.
     * @return {@code true} if the application should exit after executing this command,
     *         otherwise {@code false}.
     * @throws MomoException If the command cannot be executed due to invalid input,
     *         storage failure, or other errors.
     */
    public abstract boolean execute(TaskManager taskManager, Ui ui) throws MomoException;
}