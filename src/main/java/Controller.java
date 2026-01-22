/**
 * Controller class handles all app logic
 */
public class Controller {
    private final Ui ui;
    private Storage storage;
    private boolean isExit;

    public Controller(Ui ui) {
        this.ui = ui;
        this.storage = new Storage();
        this.isExit = false;
    }

    public void run() {
        ui.showWelcome();

        while(!isExit) {
            String userInput = ui.readUserInput().trim();
            while (userInput.equals("")) { //Handle empty user input
                userInput = ui.readUserInput();
            }

            if (userInput.trim().toLowerCase().equals("bye")) {
                isExit = true;
                ui.showGoodbye();
            } else if (userInput.trim().toLowerCase().equals("list")) {
                ui.showTodoList(storage.getTodoList());
            } else {
                Todo newTodo = new Todo(userInput);
                storage.addTodo(newTodo);
                ui.addTodoAck(userInput);
            }
        }
    }
}