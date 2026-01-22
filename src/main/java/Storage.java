/**
 * Storage class handles all storage for the chatbot
 */
import java.util.ArrayList;

public class Storage{
    private ArrayList<Todo> todoList;

    public Storage() {
        todoList = new ArrayList<>();
    }

    public void addTodo(Todo task) {
        todoList.add(task);
    }

    public ArrayList<Todo> getTodoList() {
        return this.todoList;
    }
}