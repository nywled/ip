public class Ui {
    private static final String LOGO = " /\\_/\\\n"+ 
                                       "( o.o )\n"+
                                       " > ^ <";
    
    public void showWelcome() {
        System.out.println(LOGO);
        System.out.println("Hello! I'm Momo\nWhat can I do for you?\n");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}