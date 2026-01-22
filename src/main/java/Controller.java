/**
 * Controller class handles all app logic
 */
public class Controller{
    private final Ui ui;
    private final Storage storage;
    private final Parser parser;
    private boolean isExit;

    public Controller(Ui ui) {
        this.ui = ui;
        this.storage = new Storage();
        this.parser = new Parser();
        this.isExit = false;
    }

    public void run() {
        ui.showWelcome();

        while(!isExit) {
            String userInput = ui.readUserInput().trim();
            Command command = parser.parse(userInput);

            if (command.getAction().equals("EXIT")) {
                isExit = true;
                ui.showGoodbye();
            }
            if (command.getAction().equals("LIST")) {
                ui.showTaskList(storage.getTaskList());
            }
            if (command.getAction().equals("MARK")) {
                try {
                    Task task = storage.getTask(Integer.parseInt(command.getArgs()));
                    task.SetComplete();
                    ui.showMarkTask(task);
                } catch (IndexOutOfBoundsException e) {
                    ui.showInvalidCmdMsg();
                }
            }
            if (command.getAction().equals("UNMARK")) {
                try {
                    Task task = storage.getTask(Integer.parseInt(command.getArgs()));
                    task.SetIncomplete();
                    ui.showUnmarkTask(task);
                } catch (IndexOutOfBoundsException e) {
                    ui.showInvalidCmdMsg();
                }
            }
            if (command.getAction().equals("ADD")) {
                Task newTask = new Task(command.getArgs());
                storage.addTask(newTask);
                ui.addTaskAck(command.getArgs());
            }
        }
    }
}