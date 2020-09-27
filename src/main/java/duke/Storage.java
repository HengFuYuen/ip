package duke;

import duke.exception.InvalidTaskTypeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a data storage manager.
 * A <code>Storage</code> object helps to save tasks in the task list into the hard disk and loads any
 * previously saved task when Duke is first booted up.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class Storage {

    /* String in saved task information that indicates that the task is a todo */
    private static final String SAVED_TODO_INDICATOR = "T";
    /* String in saved task information that indicates that the task is a deadline */
    private static final String SAVED_DEADLINE_INDICATOR = "D";
    /* String in saved task information that indicates that the task is a event */
    private static final String SAVED_EVENT_INDICATOR = "E";
    /* Divider that separates task type, done status, description and time (if any) of a task in the file */
    private static final String TASK_INFORMATION_DIVIDER = ":";

    /* The file that the tasks are saved in */
    private final File dukeFile;

    /**
     * Constructs a <code>Storage</code> object with a directory containing the file that saves tasks and
     * where previously saved tasks can be loaded from.
     *
     * @param dirPath Directory path that indicates the directory where the file storing tasks is saved.
     * @param filePath File path where the file storing the tasks is found.
     * @throws IOException If an I/O error occurred while creating the file that stores tasks.
     */
    public Storage(String dirPath, String filePath) throws IOException {
        File dukeDir = new File(dirPath);
        dukeFile = new File(filePath);
        if (!dukeDir.exists()) {
            dukeDir.mkdir();
        }
        if (!dukeFile.exists()) {
            dukeFile.createNewFile();
        }
    }

    /**
     * Returns an arraylist of all previously saved tasks or an empty arraylist if there are no previously
     * saved tasks.
     * Reads in all previously saved task information, deciphers it and adds them into an arraylist.
     *
     * @return An arraylist of all previously saved tasks or an empty arraylist if there are no saved tasks.
     * @throws FileNotFoundException If the file that should contain all previously saved tasks is not found.
     * @throws InvalidTaskTypeException If the the task type of a previously saved task is not recognised.
     */
    public ArrayList<Task> load() throws FileNotFoundException, InvalidTaskTypeException {
        Scanner fileScanner = new Scanner(dukeFile);
        ArrayList<Task> tasks = new ArrayList<>();
        while (fileScanner.hasNext()) {
            String savedTask = fileScanner.nextLine();
            tasks.add(createSavedTask(savedTask));
        }
        fileScanner.close();
        return tasks;
    }

    /**
     * Returns a task that represents the task information previously saved in the file.
     *
     * @param savedTask The previously saved task information including its description, time (if any) and
     *                  done status.
     * @return An task that was previously saved in the file.
     * @throws InvalidTaskTypeException If the the task type of a previously saved task is not recognised.
     */
    private Task createSavedTask(String savedTask) throws InvalidTaskTypeException {
        Task savedTaskToBeAdded;
        if (savedTask.startsWith(SAVED_TODO_INDICATOR)) {
            savedTaskToBeAdded = createSavedTodo(savedTask);
        } else if (savedTask.startsWith(SAVED_DEADLINE_INDICATOR)) {
            savedTaskToBeAdded = createSavedDeadline(savedTask);
        } else if (savedTask.startsWith(SAVED_EVENT_INDICATOR)) {
            savedTaskToBeAdded = createSavedEvent(savedTask);
        } else {
            throw new InvalidTaskTypeException();
        }
        return savedTaskToBeAdded;
    }

    private String[] splitSavedTaskInformation(String taskInformation) {
        return taskInformation.split(TASK_INFORMATION_DIVIDER);
    }

    /**
     * Returns an event that represents the event information previously saved in the file.
     *
     * @param savedTask The previously saved event information including its description, time and done
     *                  status.
     * @return An event that was previously saved in the file.
     */
    private Event createSavedEvent(String savedTask) {
        String[] savedEventInformation = splitSavedTaskInformation(savedTask);
        boolean isSavedEventDone = savedEventInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedEventDescription = savedEventInformation[2].trim();
        String savedEventTime = savedEventInformation[3].trim();
        return new Event(savedEventDescription, savedEventTime, isSavedEventDone);
    }

    /**
     * Returns a deadline that represents the deadline information previously saved in the file.
     *
     * @param savedTask The previously saved deadline information including its description, due time and
     *                  done status.
     * @return A deadline that was previously saved in the file.
     */
    private Deadline createSavedDeadline(String savedTask) {
        String[] savedDeadlineInformation = splitSavedTaskInformation(savedTask);
        boolean isSavedDeadlineDone = savedDeadlineInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedDeadlineDescription = savedDeadlineInformation[2].trim();
        String savedDeadlineTime = savedDeadlineInformation[3].trim();
        return new Deadline(savedDeadlineDescription, savedDeadlineTime, isSavedDeadlineDone);
    }

    /**
     * Returns a todo that represents the todo information previously saved in the file.
     *
     * @param savedTask The previously saved todo information including its description and done status.
     * @return A todo that was previously saved in the file.
     */
    private Todo createSavedTodo(String savedTask) {
        String[] savedTodoInformation = splitSavedTaskInformation(savedTask);
        boolean isSavedTodoDone = savedTodoInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedTodoDescription = savedTodoInformation[2].trim();
        return new Todo(savedTodoDescription, isSavedTodoDone);
    }

    /**
     * Saves a task to the file.
     *
     * @param taskToSave The task to be saved in the file.
     * @throws IOException If an I/O error occurred while saving the task to the file.
     */
    public void saveTask(Task taskToSave) throws IOException {
        FileWriter fw = new FileWriter(dukeFile, true);
        if (dukeFile.length() != 0) {
            fw.write(System.lineSeparator());
        }
        fw.write(taskToSave.messageToStoreInDukeFile());
        fw.close();
    }

    /**
     * Updates the tasks in the file.
     *
     * @param tasks The arraylist containing all the tasks in the task list.
     * @throws IOException If an I/O error occurred while updating the file.
     */
    public void update(ArrayList<Task> tasks) throws IOException {
        dukeFile.delete();
        dukeFile.createNewFile();
        for (Task task : tasks) {
            saveTask(task);
        }
    }

    /**
     * Removes all the tasks from the file.
     * The file will be empty after this call returns.
     *
     * @throws IOException If an I/O error occurred while clearing the file.
     */
    public void clear() throws IOException {
        dukeFile.delete();
        dukeFile.createNewFile();
    }
}
