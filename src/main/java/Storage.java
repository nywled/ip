/**
 * Storage class handles all storage for the chatbot
 */
import java.util.ArrayList;

public class Storage{
    private ArrayList<Task> taskList;

    public Storage() {
        taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }
}