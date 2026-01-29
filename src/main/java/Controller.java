/**
 * Controller class handles all execution
 */
import tasks.Task;
import tasks.Todo;
import tasks.Deadline;
import tasks.Event;

import exceptions.InvalidCommandException;
import exceptions.InvalidArgumentException;
import exceptions.StorageException;

public class Controller{
    private final Ui ui;
    private final TaskManager taskManager;
    private final Parser parser;
    private boolean isExit;

    public Controller(Ui ui) {
        this.ui = ui;
        this.taskManager = new TaskManager();
        this.parser = new Parser();
        this.isExit = false;
    }

    public void run() {
        ui.showWelcome();

        while(!isExit) {
            try {
                String userInput = ui.readUserInput().trim();
                Command command = parser.parse(userInput);
                //EXIT
                if (command.getAction().equals("EXIT")) {
                    isExit = true;
                    ui.showGoodbye();
                //LIST
                } else if (command.getAction().equals("LIST")) {
                    ui.showTaskList(taskManager);
                //MARK
                } else if (command.getAction().equals("MARK")) {
                    int index = Integer.parseInt(command.getArgs()[0]) - 1;
                    if (index < 0 || index >= taskManager.getTaskListSize()) {
                        throw new InvalidArgumentException("mark <int>. Selection out of range");
                    }
                    Task task = taskManager.getTask(index);
                    task.setComplete();
                    ui.showMarkTask(task);
                    taskManager.save();
                //UNMARK
                } else if (command.getAction().equals("UNMARK")) {
                    int index = Integer.parseInt(command.getArgs()[0]) - 1;
                    if (index < 0 || index >= taskManager.getTaskListSize()) {
                        throw new InvalidArgumentException("unmark <int>. Selection out of range");
                    }
                    Task task = taskManager.getTask(index);
                    task.setIncomplete();
                    ui.showUnmarkTask(task);
                    taskManager.save();
                //DELETE
                } else if (command.getAction().equals("DELETE")) {
                    int index = Integer.parseInt(command.getArgs()[0]) - 1;
                    if (index < 0 || index >= taskManager.getTaskListSize()) {
                        throw new InvalidArgumentException("delete <int>. Selection out of range");
                    }
                    Task task = taskManager.removeTask(index);
                    ui.showDeleteTask(task, taskManager.getTaskListSize());
                //TODO
                } else if (command.getAction().equals("TODO")) {
                    Todo newTask = new Todo(command.getArgs()[0]);
                    taskManager.addTask(newTask);
                    ui.addTaskAck(newTask, taskManager.getTaskListSize());
                //EVENT
                } else if (command.getAction().equals("EVENT")) {
                    Event newTask = new Event(command.getArgs()[0],command.getArgs()[1],command.getArgs()[2]);
                    taskManager.addTask(newTask);
                    ui.addTaskAck(newTask, taskManager.getTaskListSize());
                //DEADLINE
                } else if (command.getAction().equals("DEADLINE")) {
                    Deadline newTask = new Deadline(command.getArgs()[0],command.getArgs()[1]);
                    taskManager.addTask(newTask);
                    ui.addTaskAck(newTask, taskManager.getTaskListSize());
                //ERROR HANDLING
                } else {
                    throw new InvalidCommandException();
                }
            } catch (InvalidCommandException err) {
                ui.showErrMsg(err.getMessage());
            } catch (InvalidArgumentException err) {
                ui.showErrMsg(err.getMessage());
            } catch (StorageException err) {
                ui.showErrMsg(err.getMessage());
                ui.showFatalErrMsg();
                isExit = true;
            }
            
        }
    }
}