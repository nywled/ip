package momo.storage;

import java.util.ArrayList;

import momo.tasks.Task;

public interface StorageService {
    ArrayList<Task> loadTasks();
    void saveTasks(ArrayList<Task> tasks);
}
