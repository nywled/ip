/**
 * Ui class handles all user interaction
 */
import java.util.Scanner;
import java.util.ArrayList;

public class Ui {
    private static final String LOGO = " /\\_/\\\n"+ 
                                       "( o.o )\n"+
                                       " > ^ <";

    private static final String LINE = "==============================";

    public void showWelcome() {
        System.out.println(LOGO);
        System.out.println("Squeak! I'm Momo\nWhat can I do for you?");
    }

    public void showGoodbye() {
        System.out.println(LOGO);
        System.out.println("Bye ^-^ . Lets play again another time!");
    }

    public String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n>> ");
        return scanner.nextLine();
    }

    public void addTaskAck(String msg) {
        System.out.println(LINE);
        System.out.println("Added: " + msg);
        System.out.println(LINE);
    }

    public void showTaskList(ArrayList<Task> arr) {
        System.out.println(LINE);
        System.out.println("Jiayous! Here are the tasks in your list:");
        for (int i = 1; i <= arr.size(); i++) {
            System.out.println(i + "." + arr.get(i-1).toString());
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

    public void showInvalidCmdMsg() {
        System.out.println("Invalid command! Would you like to repeat for Momo again?");
    }
}