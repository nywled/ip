/**
 * Ui class handles all user intraction
 */
import java.util.Scanner;

public class Ui {
    private static final String LOGO = " /\\_/\\\n"+ 
                                       "( o.o )\n"+
                                       " > ^ <";

    private Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LOGO);
        System.out.println("Squeak! I'm Momo\nWhat can I do for you?");
    }

    public void showGoodbye() {
        System.out.println(LOGO);
        System.out.println("Bye ^-^ . Hope to see you again soon!");
    }

    public String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n>> ");
        return scanner.nextLine();
    }

    public void echoMessage(String msg) {
        System.out.println(msg);
    }
}