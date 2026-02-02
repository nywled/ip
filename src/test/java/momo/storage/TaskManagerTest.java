package momo.storage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import momo.tasks.Todo;
import momo.tasks.Task;

public class TaskManagerTest {

    //Private class to emulate data used by Storage.java
    private static class StubStorage implements StorageService{
        private ArrayList<Task> backing = new ArrayList<>();
        public int saveCalls = 0;

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
    public void addTask_updatesList_andCallsSave() {
        StubStorage storage = new StubStorage();
        TaskManager tm = new TaskManager(storage);

        tm.addTask(new Todo("read book"));

        assertEquals(1, tm.getTaskListSize(), "Size should increase after add");
        assertEquals(1, storage.saveCalls, "addTask should autosave exactly once");
    }

    @Test
    public void removeTask_returnsRemovedTask_andCallsSave() {
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
}
