package duke;

import duke.exception.InvalidCommandException;
import duke.exception.TodoDescriptionNotFoundException;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.exception.TaskIndexNotFoundException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;
    private int numberOfTasks;

    private static final String DEADLINE_TIME_INDICATOR = "/by";
    private static final String EVENT_TIME_INDICATOR = "/at";

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

    public Task addNewTask(String fullCommand) throws InvalidCommandException {
        Task newTask;
        if (fullCommand.startsWith(CommandType.TODO.getCommandWord())) {
            newTask = createTodo(fullCommand);
        } else if (fullCommand.startsWith(CommandType.DEADLINE.getCommandWord())) {
            newTask = createDeadline(fullCommand);
        } else if (fullCommand.startsWith(CommandType.EVENT.getCommandWord())) {
            newTask = createEvent(fullCommand);
        } else {
            throw new InvalidCommandException();
        }
        tasks.add(newTask);
        numberOfTasks++;
        return newTask;
    }

    private String[] splitDescriptionAndTime(String taskInformation, String splitLocation) {
        return taskInformation.split(splitLocation);
    }

    private boolean hasTaskDescription(String fullCommand, CommandType commandType) {
        return fullCommand.length() > commandType.getCommandWordLength();
    }

    private Event createEvent(String fullCommand) throws EventDescriptionNotFoundException,
            EventTimeNotFoundException {
        if (!hasTaskDescription(fullCommand, CommandType.EVENT)) {
            throw new EventDescriptionNotFoundException();
        }

        String eventInformation = fullCommand.substring(CommandType.EVENT.getCommandWordLength()).trim();
        String[] eventDescriptionAndTime = splitDescriptionAndTime(eventInformation,
                EVENT_TIME_INDICATOR);

        if (!hasTaskTime(eventDescriptionAndTime)) {
            throw new EventTimeNotFoundException();
        }

        String eventDescription = eventDescriptionAndTime[0].trim();
        String eventTime = eventDescriptionAndTime[1].trim();
        return new Event(eventDescription, eventTime);
    }

    private Deadline createDeadline(String fullCommand) throws DeadlineDescriptionNotFoundException,
            DeadlineTimeNotFoundException {
        if (!hasTaskDescription(fullCommand, CommandType.DEADLINE)) {
            throw new DeadlineDescriptionNotFoundException();
        }

        String deadlineInformation = fullCommand.substring(CommandType.DEADLINE.getCommandWordLength()).trim();
        String[] deadlineDescriptionAndTime = splitDescriptionAndTime(deadlineInformation
                , DEADLINE_TIME_INDICATOR);

        if (!hasTaskTime(deadlineDescriptionAndTime)) {
            throw new DeadlineTimeNotFoundException();
        }

        String deadlineDescription = deadlineDescriptionAndTime[0].trim();
        String deadlineTime = deadlineDescriptionAndTime[1].trim();
        return new Deadline(deadlineDescription, deadlineTime);
    }

    private boolean hasTaskTime(String[] taskDescriptionAndTime) {
        int taskTimeIndex = 2;
        return taskDescriptionAndTime.length == taskTimeIndex;
    }

    private Todo createTodo(String fullCommand) throws TodoDescriptionNotFoundException {
        if (!hasTaskDescription(fullCommand, CommandType.TODO)) {
            throw new TodoDescriptionNotFoundException();
        }
        String todoDescription = fullCommand.substring(CommandType.TODO.getCommandWordLength()).trim();
        return new Todo(todoDescription);
    }

    public Task markTaskAsDone(String fullCommand) throws TaskIndexNotFoundException,
            InvalidTaskIndexException, EmptyTaskListException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (!isTaskIndexGiven(fullCommand, CommandType.DONE)) {
            throw new TaskIndexNotFoundException();
        }
        int indexOfTaskToBeMarkAsDone =
                Integer.parseInt(fullCommand.substring(CommandType.DONE.getCommandWordLength()).trim()) - 1;
        if (!isTaskIndexValid(indexOfTaskToBeMarkAsDone)) {
            throw new InvalidTaskIndexException();
        }
        Task taskMarkedAsDone = tasks.get(indexOfTaskToBeMarkAsDone);
        taskMarkedAsDone.markAsDone();
        return taskMarkedAsDone;
    }

    public Task deleteTask(String fullCommand) throws TaskIndexNotFoundException,
            InvalidTaskIndexException, EmptyTaskListException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (!isTaskIndexGiven(fullCommand, CommandType.DELETE)) {
            throw new TaskIndexNotFoundException();
        }
        int indexOfTaskToBeDeleted =
                Integer.parseInt(fullCommand.substring(CommandType.DELETE.getCommandWordLength()).trim()) - 1 ;
        if (!isTaskIndexValid(indexOfTaskToBeDeleted)) {
            throw new InvalidTaskIndexException();
        }
        Task removedTask = tasks.get(indexOfTaskToBeDeleted);
        tasks.remove(indexOfTaskToBeDeleted);
        numberOfTasks--;
        return removedTask;
    }

    private boolean isTaskIndexValid(int indexOfTaskToBeDeleted) {
        return indexOfTaskToBeDeleted >= 0 && indexOfTaskToBeDeleted < numberOfTasks;
    }

    private boolean isTaskIndexGiven(String fullCommand, CommandType commandType) {
        return fullCommand.length() > commandType.getCommandWordLength();
    }

    public boolean isEmpty() {
        return numberOfTasks == 0;
    }

    public void clear() {
        tasks.clear();
        numberOfTasks = 0;
    }
}
