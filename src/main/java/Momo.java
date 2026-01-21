/**
 * Main entry point for chatbot
 * @author e1384477
 */
public class Momo {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Controller controller = new Controller(ui);
        controller.run();
    }
}
