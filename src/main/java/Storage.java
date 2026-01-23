/**
 * Storage class handles all storage for the chatbot
 */
import java.util.ArrayList;
import tasks.Task;

public class Storage{
    private ArrayList<Task> taskList;

    public Storage() {
        taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task removeTask(int i) {
        return taskList.remove(i);
    }

    public Task getTask(int i){
        return taskList.get(i);
    }

    public int getTaskListSize() {
        return taskList.size();
    }
}