package momo.ui;

import java.util.ArrayList;
import java.util.Scanner;

import momo.tasks.Task;
import momo.tasks.TaskManager;

/**
 * User interface component for input and output operations. Handles all user interaction for the chatbot.
 */
public class Ui {
    private static final String LOGO = " /\\_/\\\n"
                                        +
                                       "( o.o )\n"
                                        +
                                       " > ^ <";

    private static final String LINE = "==============================";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message and application logo.
     */
    public void showWelcome() {
        System.out.println(LOGO);
        System.out.println("Squeak! I'm Momo\nWhat can I do for you?");
    }

    /**
     * Displays the goodbye message and application logo.
     */
    public void showGoodbye() {
        System.out.println(LOGO);
        System.out.println("Bye ^-^ . Lets play again another time!");
    }

    /**
     * Prompts the user for input and reads a line from standard input.
     *
     * @return The user's input as a string.
     */
    public String readUserInput() {
        System.out.print("\n>> ");
        return scanner.nextLine();
    }

    /**
     * Displays an acknowledgement message after a task is added.
     *
     * @param task The task that was added.
     * @param size The current number of tasks in the list.
     */
    public void displayAddedTask(Task task, int size) {
        System.out.println(LINE);
        System.out.println("Got it! I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Displays the full list of tasks.
     *
     * @param taskManager Task manager containing the tasks to display.
     */
    public void showTaskList(TaskManager taskManager) {
        System.out.println(LINE);
        System.out.println("Jiayous! Here are the tasks in your list:");
        for (int i = 1; i <= taskManager.getTaskListSize(); i++) {
            System.out.println(i + "." + taskManager.getTask(i - 1).toString());
        }
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation message for a completed task.
     *
     * @param task The task that was marked as completed.
     */
    public void showMarkTask(Task task) {
        System.out.println(LINE);
        System.out.println("Yipee! I've marked this task as done:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation message for an uncompleted task.
     *
     * @param task The task that was marked as incomplete.
     */
    public void showUnmarkTask(Task task) {
        System.out.println(LINE);
        System.out.println("Ok, I've marked this task as not done yet:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation message after a task is deleted.
     *
     * @param task The task that was removed.
     * @param size The current number of tasks remaining.
     */
    public void showDeleteTask(Task task, int size) {
        System.out.println(LINE);
        System.out.println("Ok, I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Displays a list of tasks that match a search query.
     *
     * @param found The list of tasks that match the search keyword.
     */
    public void showMatchingTaskList(ArrayList<Task> found) {
        System.out.println("Here are the matching tasks in your lists");
        for (int i = 0; i < found.size(); i++) {
            System.out.println((i + 1) + "." + found.get(i).toString());
        }
    }

    public void showErrMsg(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays a fatal error message before application termination.
     */
    public void showFatalErrMsg() {
        System.out.println("WAAAAA WHATS HAPPENING???!!!");
    }
}
