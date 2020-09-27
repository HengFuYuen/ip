package duke;

import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.task.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskList {

    private final ArrayList<Task> tasks;
    private int numberOfTasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.numberOfTasks = tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public String getRangeOfValidTaskNumber() {
        return numberOfTasks == 0 ? "none" : "1 to " + numberOfTasks;
    }

    public void add(Task newTask) {
        tasks.add(newTask);
        numberOfTasks++;
    }

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

    private boolean isTaskIndexValid(int indexOfTaskToBeDeleted) {
        return indexOfTaskToBeDeleted >= 0 && indexOfTaskToBeDeleted < numberOfTasks;
    }

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

    public boolean isEmpty() {
        return numberOfTasks == 0;
    }

    public void clear() {
        tasks.clear();
        numberOfTasks = 0;
    }

    /**
     * Returns an arraylist of tasks in the task list containing the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return An arraylist of tasks in the task list containing the given keyword.
     * @throws EmptyTaskListException If the task list is empty.
     */
    public ArrayList<Task> find(String keyword) throws EmptyTaskListException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        return (ArrayList<Task>) tasks.stream()
                .filter((task) -> task.getAllInformation().contains(keyword))
                .collect(Collectors.toList());
    }
}
