package momo.controller;

import momo.commands.Command;
import momo.exceptions.MomoException;
import momo.exceptions.StorageException;
import momo.parser.Parser;
import momo.tasks.TaskManager;
import momo.ui.Ui;

/**
 * Main controller responsible for coordinating the main execution loop: reads user input,
 * parsing, executes commands, and handles application-level exceptions.
 */
public class Controller{
    private final Ui ui;
    private final TaskManager taskManager;
    private final Parser parser;
    private boolean isExit;

    /**
     * Constructs a controller with the given UI.
     *
     * @param ui UI component used for user input and output.
     */
    public Controller(Ui ui) {
        this.ui = ui;
        this.taskManager = new TaskManager();
        this.parser = new Parser();
        this.isExit = false;
    }

    /**
     * Starts the main execution loop of the application.
     * <p>
     * This method displays the welcome message, repeatedly reads user input,
     * parses and executes commands, and handles exceptions.
     * </p>
     * <p>
     * Storage-related errors terminate the application, while other
     * {@link MomoException}s are reported and allow continued execution.
     * </p>
     */
    public void run() {
        ui.showWelcome();

        while(!isExit) {
            try {
                String userInput = ui.readUserInput().trim();
                Command command = parser.parse(userInput);

                isExit = command.execute(taskManager, ui);
            } catch (StorageException err) {
                ui.showErrMsg(err.getMessage());
                ui.showFatalErrMsg();
                isExit = true;
            } catch (MomoException err) {
                ui.showErrMsg(err.getMessage());
            }
        }
    }
}