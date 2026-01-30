package momo.commands;

import momo.storage.TaskManager;
import momo.ui.Ui;

import momo.exceptions.MomoException;

public class ExitCommand extends Command {

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) {
        ui.showGoodbye();
        return true; // signal Controller to terminate loop
    }
}