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

    public Task addNewTask(String command) throws InvalidCommandException {
        Task newTask;
        if (command.startsWith(CommandType.TODO.getCommandWord())) {
            newTask = createTodo(command);
        } else if (command.startsWith(CommandType.DEADLINE.getCommandWord())) {
            newTask = createDeadline(command);
        } else if (command.startsWith(CommandType.EVENT.getCommandWord())) {
            newTask = createEvent(command);
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

    private boolean hasTaskDescription(String command, CommandType commandType) {
        return command.length() > commandType.getCommandWordLength();
    }

    private Event createEvent(String command) throws EventDescriptionNotFoundException,
            EventTimeNotFoundException {
        if (!hasTaskDescription(command, CommandType.EVENT)) {
            throw new EventDescriptionNotFoundException();
        }

        String eventInformation = command.substring(CommandType.EVENT.getCommandWordLength()).trim();
        String[] eventDescriptionAndTime = splitDescriptionAndTime(eventInformation,
                EVENT_TIME_INDICATOR);

        if (!hasTaskTime(eventDescriptionAndTime)) {
            throw new EventTimeNotFoundException();
        }

        String eventDescription = eventDescriptionAndTime[0].trim();
        String eventTime = eventDescriptionAndTime[1].trim();
        return new Event(eventDescription, eventTime);
    }

    private Deadline createDeadline(String command) throws DeadlineDescriptionNotFoundException,
            DeadlineTimeNotFoundException {
        if (!hasTaskDescription(command, CommandType.DEADLINE)) {
            throw new DeadlineDescriptionNotFoundException();
        }

        String deadlineInformation = command.substring(CommandType.DEADLINE.getCommandWordLength()).trim();
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

    private Todo createTodo(String command) throws TodoDescriptionNotFoundException {
        if (!hasTaskDescription(command, CommandType.TODO)) {
            throw new TodoDescriptionNotFoundException();
        }
        String todoDescription = command.substring(CommandType.TODO.getCommandWordLength()).trim();
        return new Todo(todoDescription);
    }

    public Task markTaskAsDone(String command) throws TaskIndexNotFoundException,
            InvalidTaskIndexException, EmptyTaskListException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (!isTaskIndexGiven(command, CommandType.DONE)) {
            throw new TaskIndexNotFoundException();
        }
        int indexOfTaskToBeMarkAsDone =
                Integer.parseInt(command.substring(CommandType.DONE.getCommandWordLength()).trim()) - 1;
        if (!isTaskIndexValid(indexOfTaskToBeMarkAsDone)) {
            throw new InvalidTaskIndexException();
        }
        Task taskMarkedAsDone = tasks.get(indexOfTaskToBeMarkAsDone);
        taskMarkedAsDone.markAsDone();
        return taskMarkedAsDone;
    }

    public Task deleteTask(String command) throws TaskIndexNotFoundException,
            InvalidTaskIndexException, EmptyTaskListException {
        if (isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (!isTaskIndexGiven(command, CommandType.DELETE)) {
            throw new TaskIndexNotFoundException();
        }
        int indexOfTaskToBeDeleted =
                Integer.parseInt(command.substring(CommandType.DELETE.getCommandWordLength()).trim()) - 1 ;
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

    private boolean isTaskIndexGiven(String command, CommandType commandType) {
        return command.length() > commandType.getCommandWordLength();
    }

    public boolean isEmpty() {
        return numberOfTasks == 0;
    }

    public void clear() {
        tasks.clear();
        numberOfTasks = 0;
    }

    public void printTasks() {
        int taskNumber = 1;
        for (Task task : tasks) {
            System.out.println("  " + (taskNumber) + "." + task);
            taskNumber++;
        }
    }
}
