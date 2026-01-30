package momo.commands;

import momo.storage.TaskManager;
import momo.ui.Ui;

import momo.exceptions.MomoException;

public class ListCommand extends Command {

    @Override
    public boolean execute(TaskManager taskManager, Ui ui) throws MomoException {
        ui.showTaskList(taskManager);
        return false;
    }
}