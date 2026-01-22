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
    private Scanner scanner = new Scanner(System.in);

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

    public void addTodoAck(String msg) {
        System.out.println(LINE);
        System.out.println("Added: " + msg);
        System.out.println(LINE);
    }

    public void showTodoList(ArrayList<Todo> arr) {
        System.out.println(LINE);
        for (int i = 1; i <= arr.size(); i++) {
            System.out.println(i + ". " + arr.get(i-1).toString());
        }
        System.out.println(LINE);
    }
}