package commands;

import momo.TaskManager;
import momo.Ui;

import exceptions.MomoException;

public class ListCommand extends Command {

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        ui.showTaskList(taskManager);
        return false;
    }
}