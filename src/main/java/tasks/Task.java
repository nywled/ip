/**
 * Class containing all information of a todo task
 */
package tasks;

public class Task {
    private String title;
    private boolean isComplete;

    public Task(String title){
        this.title = title;
        this.isComplete = false;
    }

    public String getStatusIcon() {
        return (isComplete ? "X" : " ");
    }

    public void setComplete(){
        isComplete = true;
    }

    public void setIncomplete() {
        isComplete = false;
    }

    public String toStorageString() {
        int status;
        if (this.isComplete == true) {
            status = 1;
        } else {
            status = 0;
        }
        return ("|" + status + "|" + this.title);
    }

    @Override
    public String toString() {
        return ("[" + this.getStatusIcon() + "] " + title);
    }
}