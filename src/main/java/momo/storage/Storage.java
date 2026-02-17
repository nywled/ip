package momo.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import momo.exceptions.StorageException;
import momo.tasks.Deadline;
import momo.tasks.Event;
import momo.tasks.Task;
import momo.tasks.Todo;

/**
 * Provides file-based persistence for task data and handles reading tasks from
 * storage and writing tasks back to storage.
 * <p>
 * This class loads tasks from a storage file at startup and saves tasks back to
 * the same file when requested. Tasks are stored in a line-based format and
 * reconstructed based on their type markers (e.g., {@code T}, {@code D},
 * {@code E}).
 * </p>
 */
public class Storage implements StorageService {
    private static final String STORAGE_PATH = "./data/tasks.txt";
    private final String filePath;

    /**
     * Constructs a storage handler using the default storage path.
     * <p>
     * If the storage file (or its parent directory) does not exist, it will be
     * automatically created.
     * </p>
     *
     * @throws StorageException If the storage file cannot be created.
     */
    public Storage() {
        this.filePath = STORAGE_PATH;
        // If running for the first time, no file yet
        // Create a file immediately
        File file = new File(filePath);
        if (!file.exists()) {
            createFile(file);
        }
    }

    /**
     * Loads tasks from the storage file and returns them as a list.
     * <p>
     * Blank lines are ignored. Each non-blank line is parsed into a
     * {@link Task}.
     * </p>
     *
     * @return {@code ArrayList} of tasks loaded from storage.
     * @throws StorageException If the storage file cannot be read or contains
     * corrupted data.
     */
    @Override
    public ArrayList<Task> loadTasks() {
        File file = new File(filePath);
        ArrayList<Task> taskList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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

    /**
     * Saves the given list of tasks to the storage file.
     * <p>
     * Each task is written on its own line using
     * {@link Task#toStorageString()}.
     * </p>
     *
     * @param taskList {@code ArrayList} of tasks to persist to storage.
     * @throws StorageException If the file cannot be written.
     */
    @Override
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

    /**
     * Parses a single storage line into a concrete {@link Task}.
     * <p>
     * Expected format examples:
     * <ul>
     * <li>{@code T|<status>|<title>}</li>
     * <li>{@code D|<status>|<title>|<dueDate>}</li>
     * <li>{@code E|<status>|<title>|<startDate>|<endDate>}</li>
     * </ul>
     * Date/time fields are parsed using {@link LocalDateTime}.
     * </p>
     *
     * @param ptask Raw line read from storage.
     * @return The reconstructed task.
     * @throws StorageException If the line format is invalid or cannot be
     * parsed.
     */
    private Task parseTask(String ptask) {
        String[] tokens = ptask.split("\\|", -1); //Keep empty "" tag between ||

        // Invalid task input
        if (tokens.length < 4) {
            throw new StorageException("Corrupted storage line: " + ptask);
        }

        // Get task type
        String type = tokens[0];
        String title = tokens[2];

        // Get task status
        int status;
        try {
            status = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException err) {
            throw new StorageException("Corrupted storage line: " + ptask);
        }

        //create new instance of task
        Task task = createTask(type, tokens, title, ptask);

        // Set task to complete if storage tag shows 1
        if (status == 1) {
            task.setComplete();
        }

        addTagsToTask(task, tokens[3]);
        return task;
    }

    /**
     * Creates a concrete {@link Task} instance based on the task type.
     *
     * <p>The task type is determined by the first token in the storage line:
     * <ul>
     *   <li>{@code "T"} → {@link Todo}</li>
     *   <li>{@code "D"} → {@link Deadline}</li>
     *   <li>{@code "E"} → {@link Event}</li>
     * </ul>
     * </p>
     *
     * @param type The task type identifier.
     * @param tokens The tokenized storage line.
     * @param title The task title extracted from storage.
     * @param originalLine The original storage line for error reporting.
     * @return A concrete {@link Task} corresponding to the given type.
     * @throws StorageException If the task type is unknown or required fields are missing.
     */
    private Task createTask(String type, String[] tokens, String title, String originalLine) {
        switch (type) {
        case "T":
            return createTodo(title);
        case "D":
            return createDeadline(tokens, title, originalLine);
        case "E":
            return createEvent(tokens, title, originalLine);
        default:
            throw new StorageException("Unknown task type: " + originalLine);
        }
    }

    /**
     * Creates a {@link Todo} task with the specified title.
     *
     * @param title The task title.
     * @return A new {@link Todo} instance.
     */
    private Todo createTodo(String title) {
        return new Todo(title);
    }

    /**
     * Creates a {@link Deadline} task from the given storage tokens.
     *
     * @param tokens The tokenized storage line.
     * @param title The task title.
     * @param originalLine The original storage line for error reporting.
     * @return A new {@link Deadline} instance.
     * @throws StorageException If the deadline format is invalid or the date/time cannot be parsed.
     */
    private Task createDeadline(String[] tokens, String title, String originalLine) {
        if (tokens.length < 5) {
            throw new StorageException("Corrupted deadline line: " + originalLine);
        }

        try {
            LocalDateTime dueDate = LocalDateTime.parse(tokens[4]);
            return new Deadline(title, dueDate);
        } catch (DateTimeParseException err) {
            throw new StorageException("Corrupted deadline date/time: " + originalLine);
        }
    }

    /**
     * Creates an {@link Event} task from the given storage tokens.
     *
     * @param tokens The tokenized storage line.
     * @param title The task title.
     * @param originalLine The original storage line for error reporting.
     * @return A new {@link Event} instance.
     * @throws StorageException If the event format is invalid or date/time parsing fails.
     */
    private Task createEvent(String[] tokens, String title, String originalLine) {
        if (tokens.length < 6) {
            throw new StorageException("Corrupted event line: " + originalLine);
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(tokens[4]);
            LocalDateTime endDate = LocalDateTime.parse(tokens[5]);
            return new Event(title, startDate, endDate);
        } catch (DateTimeParseException err) {
            throw new StorageException("Corrupted event date/time: " + originalLine);
        }
    }

    private void addTagsToTask(Task task, String tagField) {
        if (tagField == null || tagField.isBlank()) {
            return;
        }

        String[] tags = tagField.split(", ");
        for (String i : tags) {
            if (!i.isBlank()) {
                task.addTag(i.trim());
            }
        }
    }

    /**
     * Creates the storage file and its parent directory (if needed).
     *
     * @param file The file to create.
     * @throws StorageException If the directory/file cannot be created.
     */
    private void createFile(File file) {
        try {
            File parentDirectory = file.getParentFile();

            // If parent directory is not found, force create
            if (parentDirectory != null && !parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            // Force create the file
            file.createNewFile();
        } catch (IOException err) {
            throw new StorageException("Failed to create storage file");
        }
    }
}
