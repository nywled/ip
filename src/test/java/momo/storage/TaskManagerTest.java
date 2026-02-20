package momo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import momo.tasks.Task;
import momo.tasks.TaskManager;
import momo.tasks.Todo;

public class TaskManagerTest {

    //Private class to emulate data used by Storage.java
    private static class StubStorage implements StorageService {
        private ArrayList<Task> backing = new ArrayList<>();
        private int saveCalls = 0;

        @Override
        public ArrayList<Task> loadTasks() {
            return new ArrayList<>(backing);
        }

        @Override
        public void saveTasks(ArrayList<Task> tasks) {
            saveCalls++;
            backing = new ArrayList<>(tasks);
        }
    }

    @Test
    public void addTask_updatesList_callsSave() {
        StubStorage storage = new StubStorage();
        TaskManager tm = new TaskManager(storage);

        tm.addTask(new Todo("read book"));

        assertEquals(1, tm.getTaskListSize(), "Size should increase after add");
        assertEquals(1, storage.saveCalls, "addTask should autosave exactly once");
    }

    @Test
    public void removeTask_returnsRemovedTask_callsSave() {
        StubStorage storage = new StubStorage();
        TaskManager tm = new TaskManager(storage);

        tm.addTask(new Todo("A"));
        tm.addTask(new Todo("B"));
        storage.saveCalls = 0;

        Task removed = tm.removeTask(0);

        assertEquals("[T][ ] A", removed.toString(), "Should return removed task");
        assertEquals(1, tm.getTaskListSize(), "Size should decrease after remove");
        assertEquals("[T][ ] B", tm.getTask(0).toString(), "Remaining task should shift");
        assertEquals(1, storage.saveCalls, "removeTask should autosave exactly once");
    }

    @Test
    public void constructor_loadsTasksFromStorage_tasksArrayInitialised() {
        StubStorage storage = new StubStorage();
        storage.backing.add(new Todo("A"));
        storage.backing.add(new Todo("B"));

        TaskManager tm = new TaskManager(storage);

        assertEquals(2, tm.getTaskListSize(), "Should load existing tasks into memory on construction");
        assertEquals("[T][ ] A", tm.getTask(0).toString(), "First loaded task should match");
        assertEquals("[T][ ] B", tm.getTask(1).toString(), "Second loaded task should match");
    }

    @Test
    public void save_callsStorageOnce_persistsCurrentList() {
        StubStorage storage = new StubStorage();
        TaskManager tm = new TaskManager(storage);

        tm.addTask(new Todo("A"));
        tm.addTask(new Todo("B"));

        storage.saveCalls = 0; // reset to isolate save() behavior

        tm.save();

        assertEquals(1, storage.saveCalls, "save() should call storage.saveTasks exactly once");
        assertEquals(2, storage.backing.size(), "save() should persist current list");
        assertEquals("[T][ ] A", storage.backing.get(0).toString(), "Persisted task 1 should match");
        assertEquals("[T][ ] B", storage.backing.get(1).toString(), "Persisted task 2 should match");
    }

    @Test
    public void getTask_validIndex_returnsCorrectTask() {
        StubStorage storage = new StubStorage();
        TaskManager tm = new TaskManager(storage);

        tm.addTask(new Todo("A"));
        tm.addTask(new Todo("B"));

        assertEquals("[T][ ] A", tm.getTask(0).toString(), "getTask(0) should return first task");
        assertEquals("[T][ ] B", tm.getTask(1).toString(), "getTask(1) should return second task");
    }
}
