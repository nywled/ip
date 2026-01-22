/**
 * Class containing all information of a todo task
 */

public class Task {
    private String title;
    private boolean isComplete;

    public Task(String title){
        this.title = title;
        this.isComplete = false;
    }

    public void toggleCompleteStatus(){
        isComplete = !isComplete;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        if (isComplete == true) {
            msg.append("[X] ");
        } else {
            msg.append("[ ] ");
        }
        msg.append(this.title);
        return msg.toString();
    }
}