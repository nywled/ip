package commands;

import momo.TaskManager;
import momo.Ui;

import exceptions.MomoException;

public class ExitCommand extends Command {

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) {
        ui.showGoodbye();
        return true; // signal Controller to terminate loop
    }
}