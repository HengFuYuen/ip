package duke;

import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * A <code>TaskList</code> object stores all the tasks and provides methods related to adding, deleting,
 * etc, of tasks to the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class TaskList {

    private final ArrayList<Task> tasks;
    private int numberOfTasks;

    /**
     * Constructs a <code>TaskList</code> object that stores tasks and is initialized with previously
     * saved tasks if any.
     * The task list also keeps track of the number of tasks.
     *
     * @param tasks An arraylist containing previously saved tasks or an empty arraylist if there are
     *              no previously saved tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.numberOfTasks = tasks.size();
    }

    /**
     * Returns an arraylist containing all the tasks in the list.
     *
     * @return An arraylist containing all the tasks in the list.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks in the list.
     */
    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    /**
     * Returns a string representation of the range of valid task indexes.
     * If the task list is empty, a <code>"none"</code> string is returned.
     *
     * @return A string representation of the range of valid task indexes.
     */
    public String getRangeOfValidTaskIndex() {
        return numberOfTasks == 0 ? "none" : "1 to " + numberOfTasks;
    }

    /**
     * Adds a new task to the list.
     *
     * @param newTask The new task to be added.
     */
    public void add(Task newTask) {
        tasks.add(newTask);
        numberOfTasks++;
    }

    /**
     * Returns the task that was marked as done.
     * The task with the given task index is marked as done.
     *
     * @param taskIndex The task index of the task to be marked as done.
     * @return The task that was marked as done.
     * @throws EmptyTaskListException If the task list is empty.
     * @throws InvalidTaskIndexException If the the task index given is <= 0 or > number of tasks in the list.
     */
    public Task markTaskAsDone(int taskIndex) throws EmptyTaskListException, InvalidTaskIndexException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (!isTaskIndexValid(taskIndex)) {
            throw new InvalidTaskIndexException();
        }
        Task taskMarkedAsDone = tasks.get(taskIndex);
        taskMarkedAsDone.markAsDone();
        return taskMarkedAsDone;
    }

    private boolean isTaskIndexValid(int taskIndex) {
        return taskIndex >= 0 && taskIndex < numberOfTasks;
    }

    /**
     * Returns the task that was deleted from the list.
     * The task with the given task index is removed from the list.
     *
     * @param taskIndex The task index of the task to be deleted.
     * @return The task that was deleted from the list.
     * @throws EmptyTaskListException If the task list is empty.
     * @throws InvalidTaskIndexException If the the task index given is <= 0 or > number of tasks in the list.
     */
    public Task delete(int taskIndex) throws EmptyTaskListException, InvalidTaskIndexException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (!isTaskIndexValid(taskIndex)) {
            throw new InvalidTaskIndexException();
        }
        Task taskDeleted = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        numberOfTasks--;
        return taskDeleted;
    }

    private boolean isEmpty() {
        return numberOfTasks == 0;
    }

    /**
     * Removes all the tasks from the task list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        tasks.clear();
        numberOfTasks = 0;
    }
}
