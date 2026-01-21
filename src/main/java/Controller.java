/**
 * Controller class handles all app logic
 */
public class Controller {
    private final Ui ui;
    private boolean isExit;

    public Controller(Ui ui) {
        this.ui = ui;
        this.isExit = false;
    }

    public void run() {
        ui.showWelcome();

        while(!isExit) {
            String userInput = ui.readUserInput();

            if (userInput.trim().toLowerCase().equals("bye")) {
                isExit = true;
                ui.showGoodbye();
            } else {
                ui.echoMessage(userInput);
            }
        }
    }
}