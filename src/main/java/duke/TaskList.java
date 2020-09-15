package duke;

import duke.exception.TaskDescriptionNotFoundException;
import duke.exception.TodoDescriptionNotFoundException;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.NoTaskInTaskListException;
import duke.exception.TaskToMarkAsDoneInvalidException;
import duke.exception.TaskToMarkAsDoneNotFoundException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.ArrayList;

public class TaskList {

    private static final ArrayList<Task> tasks = new ArrayList<>();
    protected static int numberOfTasks = 0;

    public TaskList() {
    }

    protected Task getTask(int indexOfTask) {
        return tasks.get(indexOfTask);
    }

    protected int getNumberOfTasks() {
        return numberOfTasks;
    }

    protected String getRangeOfValidTaskNumber() {
        return numberOfTasks == 0 ? "none" : "1 to " + numberOfTasks;
    }

    protected Task addNewTask(String command) throws TaskDescriptionNotFoundException {
        Task newTask;
        if (command.startsWith("todo")) {
            newTask = createTodo(command);
        } else if (command.startsWith("deadline")) {
            newTask = createDeadline(command);
        } else if (command.startsWith("event")) {
            newTask = createEvent(command);
        } else {
            throw new TaskDescriptionNotFoundException();
        }
        tasks.add(newTask);
        numberOfTasks++;
        return newTask;
    }

    private String[] splitDescriptionAndTime(String taskInformation, String splitLocation) {
        return taskInformation.split(splitLocation);
    }

    private Task createEvent(String command) throws EventDescriptionNotFoundException,
            EventTimeNotFoundException {
        if (command.length() <= 6) {
            throw new EventDescriptionNotFoundException();
        }

        String eventInformation = command.substring(5).trim();
        String[] eventDescriptionAndTime = splitDescriptionAndTime(eventInformation, "/at");

        if (eventDescriptionAndTime.length <= 1) {
            throw new EventTimeNotFoundException();
        }

        String eventDescription = eventDescriptionAndTime[0].trim();
        String eventTime = eventDescriptionAndTime[1].trim();
        return new Event(eventDescription, eventTime);
    }

    private Task createDeadline(String command) throws DeadlineDescriptionNotFoundException,
            DeadlineTimeNotFoundException {
        if (command.length() <= 9) {
            throw new DeadlineDescriptionNotFoundException();
        }

        String deadlineInformation = command.substring(8).trim();
        String[] deadlineDescriptionAndTime = splitDescriptionAndTime(deadlineInformation, "/by");

        if (deadlineDescriptionAndTime.length <= 1) {
            throw new DeadlineTimeNotFoundException();
        }

        String deadlineDescription = deadlineDescriptionAndTime[0].trim();
        String deadlineTime = deadlineDescriptionAndTime[1].trim();
        return new Deadline(deadlineDescription, deadlineTime);
    }

    private Task createTodo(String command) throws TodoDescriptionNotFoundException {
        if (command.length() <= 5) {
            throw new TodoDescriptionNotFoundException();
        }
        String todoDescription = command.substring(4).trim();
        return new Todo(todoDescription);
    }

    protected Task markTaskAsDone(String command) throws TaskToMarkAsDoneNotFoundException,
            TaskToMarkAsDoneInvalidException, NoTaskInTaskListException {
        if (numberOfTasks == 0) {
            throw new NoTaskInTaskListException();
        }
        if (command.length() <= 4) {
            throw new TaskToMarkAsDoneNotFoundException();
        }
        int indexOfTaskToBeMarkAsDone = Integer.parseInt(command.substring(4).trim()) - 1;

        if (indexOfTaskToBeMarkAsDone < 0 || indexOfTaskToBeMarkAsDone >= numberOfTasks) {
            throw new TaskToMarkAsDoneInvalidException();
        }

        Task taskMarkedAsDone = tasks.get(indexOfTaskToBeMarkAsDone);
        taskMarkedAsDone.markAsDone();
        return taskMarkedAsDone;
    }

}
