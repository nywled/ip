/**
 * Class containing all information of a todo task
 */
public class Todo {
    private String title;

    public Todo(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }
}