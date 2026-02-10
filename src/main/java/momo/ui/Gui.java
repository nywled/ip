package momo.ui;

import java.util.ArrayList;

import momo.tasks.Task;
import momo.tasks.TaskManager;

/**
 * UI implementation for GUI mode that captures output instead of printing.
 */
public class Gui extends Ui {
    private final StringBuilder res = new StringBuilder();

    public void clear() {
        res.setLength(0);
    }

    public String getOutput() {
        return res.toString();
    }

    private void appendLine(String s) {
        res.append(s).append("\n");
    }

    // ---- Override Ui printing methods to write into buffer ----

    @Override
    public void showWelcome() {
        appendLine("Squeak! I'm Momo");
        appendLine("What can I do for you?");
    }

    @Override
    public void showGoodbye() {
        appendLine("Bye ^-^ . Lets play again another time!");
    }

    @Override
    public String readUserInput() {
        return "";
    }

    @Override
    public void displayAddedTask(Task task, int size) {
        appendLine("Got it! I've added this task:");
        appendLine(task.toString());
        appendLine("Now you have " + size + " tasks in the list.");
    }

    @Override
    public void showTaskList(TaskManager taskManager) {
        appendLine("Jiayous! Here are the tasks in your list:");
        for (int i = 1; i <= taskManager.getTaskListSize(); i++) {
            appendLine(i + "." + taskManager.getTask(i - 1).toString());
        }
    }

    @Override
    public void showMarkTask(Task task) {
        appendLine("Yipee! I've marked this task as done:");
        appendLine(task.toString());
    }

    @Override
    public void showUnmarkTask(Task task) {
        appendLine("Ok, I've marked this task as not done yet:");
        appendLine(task.toString());
    }

    @Override
    public void showDeleteTask(Task task, int size) {
        appendLine("Ok, I've removed this task:");
        appendLine(task.toString());
        appendLine("Now you have " + size + " tasks in the list.");
    }

    @Override
    public void showMatchingTaskList(ArrayList<Task> found) {
        appendLine("Here are the matching tasks in your lists");
        for (int i = 0; i < found.size(); i++) {
            appendLine((i + 1) + "." + found.get(i).toString());
        }
    }

    @Override
    public void showErrMsg(String msg) {
        appendLine(msg);
    }

    @Override
    public void showFatalErrMsg() {
        appendLine("WAAAAA WHATS HAPPENING???!!!");
    }
}
