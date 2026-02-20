package momo.ui;

import java.util.ArrayList;

import momo.tasks.Task;
import momo.tasks.TaskManager;

/**
 * UI implementation for GUI mode that captures output instead of printing.
 */
public class Gui extends Ui {
    private final StringBuilder res = new StringBuilder();

    /**
     * Clears all previously stored output from the buffer.
     */
    public void clear() {
        res.setLength(0);
    }

    /**
     * Returns the accumulated output as a string.
     *
     * @return Output generated since the last call to {@link #clear()}.
     */
    public String getOutput() {
        return res.toString();
    }

    /**
     * Appends a line of text to the output buffer.
     *
     * @param s Text to append.
     */
    private void appendLine(String s) {
        res.append(s).append("\n");
    }

    // ---- Override Ui printing methods to write into buffer ----
    /**
     * Displays the welcome message.
     */
    @Override
    public void showWelcome() {
        appendLine("Squeak! I'm Momo");
        appendLine("What can I do for you?");
    }

    /**
     * Displays the goodbye message.
     */
    @Override
    public void showGoodbye() {
        appendLine("Bye ^-^ . Lets play again another time!");
    }

    /**
     * Returns an empty string as GUI input is handled externally.
     *
     * @return An empty string.
     */
    @Override
    public String readUserInput() {
        return "";
    }

    /**
     * Displays an acknowledgement message after a task is added.
     *
     * @param task The task that was added.
     * @param size The current number of tasks in the list.
     */
    @Override
    public void displayAddedTask(Task task, int size) {
        appendLine("Got it! I've added this task:");
        appendLine(task.toString());
        appendLine("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays the full list of tasks.
     *
     * @param taskManager Task manager containing the tasks to display.
     */
    @Override
    public void showTaskList(TaskManager taskManager) {
        appendLine("Time to get productive!");
        for (int i = 1; i <= taskManager.getTaskListSize(); i++) {
            appendLine(i + "." + taskManager.getTask(i - 1).toString());
        }
    }

    /**
     * Displays a confirmation message for a completed task.
     *
     * @param task The task that was marked as completed.
     */
    @Override
    public void showMarkTask(Task task) {
        appendLine("Yipee! I've marked this task as done:");
        appendLine(task.toString());
    }

    /**
     * Displays a confirmation message for an uncompleted task.
     *
     * @param task The task that was marked as incomplete.
     */
    @Override
    public void showUnmarkTask(Task task) {
        appendLine("Ok, I've marked this task as not done yet:");
        appendLine(task.toString());
    }

    /**
     * Displays a confirmation message after a task is deleted.
     *
     * @param task The task that was removed.
     * @param size The current number of tasks remaining.
     */
    @Override
    public void showDeleteTask(Task task, int size) {
        appendLine("Ok, I've removed this task:");
        appendLine(task.toString());
        appendLine("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays a list of tasks that match a search query.
     *
     * @param found The list of tasks that match the search keyword.
     */
    @Override
    public void showMatchingTaskList(ArrayList<Task> found) {
        appendLine("Here are the matching tasks in your lists");
        if (found.size() == 0) {
            appendLine("No matching task found");
        } else {
            for (int i = 0; i < found.size(); i++) {
                appendLine("-" + found.get(i).toString());
            }
        }
    }

    /**
     * Displays a confirmation message after a tag is added to a task.
     *
     * @param task The task that was updated with the new tag.
     */
    @Override
    public void showTagAdded(Task task) {
        appendLine("I have added the tag:");
        appendLine(task.toString());
    }

    /**
     * Displays a confirmation message after a tag is removed from a task.
     *
     * @param task The task that was updated with the removed tag.
     */
    @Override
    public void showTagRemoved(Task task) {
        appendLine("I have removed the tag:");
        appendLine(task.toString());
    }

    /**
     * Displays a non-fatal error message.
     *
     * @param msg Error message to display.
     */
    @Override
    public void showErrMsg(String msg) {
        appendLine(msg);
    }

    /**
     * Displays a fatal error message before application termination.
     */
    @Override
    public void showFatalErrMsg() {
        appendLine("WAAAAA WHATS HAPPENING???!!!");
    }
}
