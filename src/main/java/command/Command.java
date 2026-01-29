package commands;

import momo.TaskManager;
import momo.Ui;

import exceptions.MomoException;

public abstract class Command {
    public abstract boolean execute(TaskManager taskManager, Ui ui) throws MomoException;
}