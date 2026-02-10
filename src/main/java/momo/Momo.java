/**
 * Main entry point for chatbot.
 * @author e1384477
 */
package momo;

import momo.commands.Command;
import momo.controller.Controller;
import momo.exceptions.MomoException;
import momo.exceptions.StorageException;
import momo.parser.Parser;
import momo.tasks.TaskManager;
import momo.ui.Gui;
import momo.ui.Ui;

/**
 * Application launcher for Momo.
 */
public class Momo {
    private final Parser parser;
    private final TaskManager taskManager;
    private final Gui gui;

    private boolean isExit;

    /**
     * Constructs a {@code Momo} instance with default components.
     * <p>
     * Initializes the parser, task manager, and GUI handler.
     * </p>
     */
    public Momo() {
        this.taskManager = new TaskManager();
        this.parser = new Parser();
        this.gui = new Gui();
    }

    /**
     * Processes one user input and returns Momo's response string for GUI.
     *
     * @param input Raw user input.
     * @return Response message to display in GUI.
     */
    public String getResponse(String input) {
        gui.clear();

        try {
            Command command = parser.parse(input);
            isExit = command.execute(taskManager, gui);
            return gui.getOutput();
        } catch (StorageException err) {
            gui.showErrMsg(err.getMessage());
            gui.showFatalErrMsg();
            isExit = true;
            return gui.getOutput();
        } catch (MomoException err) {
            gui.showErrMsg(err.getMessage());
            return gui.getOutput();
        }
    }

    public boolean shouldExit() {
        return this.isExit;
    }

    /**
     * Command-line interface entry point for legacy use.
     * <p>
     * Initializes the CLI {@link Ui} and starts the main controller loop.
     * </p>
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Controller controller = new Controller(ui);
        controller.run();
    }
}

