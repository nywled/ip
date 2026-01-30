package momo.storage;

import java.util.ArrayList;

import momo.tasks.Task;

public class TaskManager {
    private final Storage storage;
    private final ArrayList<Task> taskList;

    public TaskManager() {
        this.storage = new Storage();
        this.taskList = storage.loadTasks(); // load from file into memory
    }

    public void addTask(Task task) {
        taskList.add(task);
        storage.saveTasks(taskList); //Autosave after every task add
    }

    public Task removeTask(int i) {
        Task removedTask = taskList.remove(i);
        storage.saveTasks(taskList); //Autosave after every task add
        return removedTask;
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public void save() {
        storage.saveTasks(taskList);
    }
}