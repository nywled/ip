package momo.tasks;

import java.util.ArrayList;
import java.util.stream.Collectors;

import momo.storage.Storage;
import momo.storage.StorageService;

/**
 * Manages an in-memory list of tasks and coordinates persistence through a
 * {@link StorageService}. Provides task management operations such as add, remove, and retrieval.
 * <p>
 * Tasks are loaded from storage upon construction and automatically saved
 * after changes are made.
 * </p>
 */
public class TaskManager {
    private final StorageService storage;
    private final ArrayList<Task> taskList;

    /**
     * Constructs a task manager using the default storage implementation.
     * <p>
     * Existing tasks are loaded from storage into memory.
     * </p>
     */
    public TaskManager() {
        this.storage = new Storage();
        this.taskList = storage.loadTasks(); // load from file into memory
    }

    /**
     * Constructs a task manager with a provided storage service.
     * <p>
     * Intended primarily for testing with stub or mock storage implementations.
     * </p>
     *
     * @param storage The storage service to use.
     */
    public TaskManager(StorageService storage) {
        this.storage = storage;
        this.taskList = storage.loadTasks();
    }

    /**
     * Adds a task to the task list and saves the updated list to storage.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        assert task != null : "Task added should not be null";
        taskList.add(task);
        storage.saveTasks(taskList); //Autosave after every task add
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param i Index of the task to remove.
     * @return The removed task.
     */
    public Task removeTask(int i) {
        assert i >= 0 && i < taskList.size() : "Index out of bounds";
        Task removedTask = taskList.remove(i);
        storage.saveTasks(taskList); //Autosave after every task add
        return removedTask;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param i Index of the task to retrieve.
     * @return The task at the given index.
     */
    public Task getTask(int i) {
        assert i >= 0 && i < taskList.size() : "Index out of bounds";
        return taskList.get(i);
    }

    /**
     * Returns an array of the tasks whose title contains the searched keyword
     *
     * @return A new arraylist of tasks.
     */
    public ArrayList<Task> findTasksByTitle(String keyword) {
        ArrayList<Task> searchResults = taskList.stream()
                .filter(task -> task.containsKeyword(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
        return searchResults;
    }

    /**
     * Returns an array of the tasks whose tag contains the searched keyword
     *
     * @return A new arraylist of tasks.
     */
    public ArrayList<Task> findTasksByTag(String keyword) {
        ArrayList<Task> searchResults = taskList.stream()
                .filter(task -> task.hasTag(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
        return searchResults;
    }

    /**
     * Returns the number of tasks currently managed.
     *
     * @return Size of the task list.
     */
    public int getTaskListSize() {
        return taskList.size();
    }

    /**
     * Saves the current task list to storage.
     */
    public void save() {
        storage.saveTasks(taskList);
    }
}
