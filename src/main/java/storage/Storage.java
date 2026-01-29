/**
 * Storage class handles all read write storage operations
 */
package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;

import tasks.Task;
import tasks.Todo;
import tasks.Deadline;
import tasks.Event;

import exceptions.StorageException;



public class Storage {
    private static final String STORAGE_PATH = "./data/tasks.txt";
    private final String filePath;

    public Storage() {
        this.filePath = STORAGE_PATH;
        //If running for the first time, no file yet
        //Create a file immediately
        File file = new File(filePath);
        if (!file.exists()) {
            createFile(file);
        }
    }

    public ArrayList<Task> loadTasks() {
        File file = new File(filePath);
        ArrayList<Task> taskList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                taskList.add(parseTask(line));
            }
        } catch (IOException err) {
            throw new StorageException("Failed to load storage file");
        }
        return taskList;
    }

    public void saveTasks(ArrayList<Task> taskList) {
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : taskList) {
                writer.write(task.toStorageString());
                writer.newLine();
            }
        } catch (IOException err) {
            throw new StorageException("Failed to save storage file");
        }
    }

    private Task parseTask(String ptask) {
        String[] tokens = ptask.split("\\|");

        //Invalid task input
        if (tokens.length < 3) {
            throw new StorageException("Corrupted storage line: " + ptask);
        }

        String type = tokens[0];
        int status;
        try {
            status = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException err) {
            throw new StorageException("Corrupted storage line: " + ptask);
        }
        String title = tokens[2];

        Task task;

        switch (type) {
        case "T":
            task = new Todo(title);
            break;
        case "D":
            if (tokens.length < 4) {
                throw new StorageException("Corrupted deadline line: " + ptask);
            }
            try {
                LocalDateTime dueDate = LocalDateTime.parse(tokens[3]);
                task = new Deadline(title, dueDate);
            } catch (DateTimeParseException err) {
                throw new StorageException("Corrupted deadline date/time: " + ptask);
            }
            break;
        case "E":
            if (tokens.length < 5) {
                throw new StorageException("Corrupted event line: " + ptask);
            }
            try {
                LocalDateTime startDate = LocalDateTime.parse(tokens[3]);
                LocalDateTime endDate = LocalDateTime.parse(tokens[4]);
                task = new Event(title, startDate, endDate);
            } catch (DateTimeParseException err) {
                throw new StorageException("Corrupted deadline date/time: " + ptask);
            }
            break;
        default:
            throw new StorageException("Unknown task type: " + ptask);
        }

        if (status == 1) {
            task.setComplete();
        }
        return task;
    }

    private boolean isFileFound(File file) {
        return file.exists();
    }

    private void createFile(File file) {
        try {
            File parentDirectory = file.getParentFile();

            //If parent directory is not found, force create    
            if (parentDirectory != null && !parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            //Force create the file
            file.createNewFile();
        } catch (IOException err) {
            throw new StorageException("Failed to create storage file");
        }
    }
}