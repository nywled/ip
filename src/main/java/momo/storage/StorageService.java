package momo.storage;

import java.util.ArrayList;

import momo.tasks.Task;

/**
 * Storage service interface for loading and saving tasks.
 * Defines the contract for task persistence services.
 * <p>
 * Implementations are responsible for loading tasks from storage
 * and saving tasks back to storage.
 * </p>
 */
public interface StorageService {
    /**
     * Loads tasks from storage.
     *
     * @return {@code ArrayList} of tasks retrieved from storage.
     */
    ArrayList<Task> loadTasks();

    /**
     * Saves the given list of tasks to storage.
     *
     * @param tasks The tasks to be persisted.
     */
    void saveTasks(ArrayList<Task> tasks);
}
