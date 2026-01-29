/**
 * Ui class handles all user interaction
 * This class is responsible for displaying outputs
 * and getting user input.
 */
package momo;

import java.util.ArrayList;

import java.util.Scanner;

import tasks.Task;

public class Ui {
    private static final String LOGO = " /\\_/\\\n"+ 
                                       "( o.o )\n"+
                                       " > ^ <";

    private static final String LINE = "==============================";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LOGO);
        System.out.println("Squeak! I'm Momo\nWhat can I do for you?");
    }

    public void showGoodbye() {
        System.out.println(LOGO);
        System.out.println("Bye ^-^ . Lets play again another time!");
    }

    public String readUserInput() {
        System.out.print("\n>> ");
        return scanner.nextLine();
    }

    public void addTaskAck(Task task, int size) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showTaskList(TaskManager taskManager) {
        System.out.println(LINE);
        System.out.println("Jiayous! Here are the tasks in your list:");
        for (int i = 1; i <= taskManager.getTaskListSize(); i++) {
            System.out.println(i + "." + taskManager.getTask(i-1).toString());
        }
        System.out.println(LINE);
    }

    public void showMarkTask(Task task) {
        System.out.println(LINE);
        System.out.println("Yipee! I've marked this task as done:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    public void showUnmarkTask(Task task) {
        System.out.println(LINE);
        System.out.println("Ok, I've marked this task as not done yet:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    public void showDeleteTask(Task task, int size) {
        System.out.println(LINE);
        System.out.println("Ok, I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showErrMsg(String msg) {
        System.out.println(msg);
    }

    public void showFatalErrMsg() {
        System.out.println("WAAAAA WHATS HAPPENING???!!!");
    }
}