/**
 * Class containing all information of a todo task
 */
package momo.tasks;

public class Task {
    private String title;
    private boolean isComplete;

    public Task(String title) {
        this.title = title;
        this.isComplete = false;
    }

    public Task(String title, boolean isComplete){
        this.title = title;
        this.isComplete = isComplete;
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
        int status = isComplete ? 1 : 0;
        return ("|" + status + "|" + this.title);
    }

    @Override
    public String toString() {
        return ("[" + this.getStatusIcon() + "] " + this.title);
    }
}