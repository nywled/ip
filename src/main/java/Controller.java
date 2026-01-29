/**
 * Controller class handles all execution
 */
import commands.Command;

import momo.Ui;
import momo.TaskManager;

import parser.Parser;

import exceptions.MomoException;
import exceptions.StorageException;

public class Controller{
    private final Ui ui;
    private final TaskManager taskManager;
    private final Parser parser;
    private boolean isExit;

    public Controller(Ui ui) {
        this.ui = ui;
        this.taskManager = new TaskManager();
        this.parser = new Parser();
        this.isExit = false;
    }

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