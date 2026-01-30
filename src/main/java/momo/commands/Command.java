package momo.commands;

import momo.storage.TaskManager;
import momo.ui.Ui;

import momo.exceptions.MomoException;

public abstract class Command {
    public abstract boolean execute(TaskManager taskManager, Ui ui) throws MomoException;
}