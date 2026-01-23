/**
 * Controller class handles all execution
 */
import tasks.*;

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
            //Exit of prog
            if (command.getAction().equals("EXIT")) {
                isExit = true;
                ui.showGoodbye();
            } else if (command.getAction().equals("LIST")) { //List all task
                ui.showTaskList(storage);
            } else if (command.getAction().equals("MARK")) { //Mark a task complete
                try {
                    int index = Integer.parseInt(command.getArgs()[0]) - 1;
                    Task task = storage.getTask(index);
                    task.setComplete();
                    ui.showMarkTask(task);
                } catch (IndexOutOfBoundsException e) {
                    ui.showInvalidCmdMsg();
                }
            } else if (command.getAction().equals("UNMARK")) { //Mark a task incomplete
                try {
                    int index = Integer.parseInt(command.getArgs()[0]) - 1;
                    Task task = storage.getTask(index);
                    task.setIncomplete();
                    ui.showUnmarkTask(task);
                } catch (IndexOutOfBoundsException e) {
                    ui.showInvalidCmdMsg();
                }
            } else if (command.getAction().equals("TODO")) { //Add a new Todo/Event/Deadline
                Todo newTask = new Todo(command.getArgs()[0]);
                storage.addTask(newTask);
                ui.addTaskAck(newTask, storage.getTaskListSize());
            } else if (command.getAction().equals("EVENT")) {
                Event newTask = new Event(command.getArgs()[0],command.getArgs()[1],command.getArgs()[2]);
                storage.addTask(newTask);
                ui.addTaskAck(newTask, storage.getTaskListSize());
            } else if (command.getAction().equals("DEADLINE")) {
                Deadline newTask = new Deadline(command.getArgs()[0],command.getArgs()[1]);
                storage.addTask(newTask);
                ui.addTaskAck(newTask, storage.getTaskListSize());
            } else if (command.getAction().equals("INVALID")) {//All other invalid commands
                ui.showInvalidCmdMsg();
            } else {
                ui.showInvalidCmdMsg();
            }
        }
    }
}