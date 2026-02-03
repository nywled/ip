package momo.commands;

import momo.exceptions.MomoException;
import momo.storage.TaskManager;
import momo.ui.Ui;

public abstract class Command {
    public abstract boolean execute(TaskManager taskManager, Ui ui) throws MomoException;
}
