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
import duke.exception.InvalidTaskTypeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.ArrayList;

public class TaskList {

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static int numberOfTasks = 0;
    private static int numberOfSavedTasks = 0;

    public TaskList() {
    }

    protected Task getTask(int indexOfTask) {
        return tasks.get(indexOfTask);
    }

    protected int getNumberOfTasks() {
        return numberOfTasks;
    }

    protected int getNumberOfSavedTasks() {
        return numberOfSavedTasks;
    }

    protected String getRangeOfValidTaskNumber() {
        return numberOfTasks == 0 ? "none" : "1 to " + numberOfTasks;
    }

    protected Task addNewTask(String command) throws InvalidCommandException {
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

    private String[] splitDescriptionAndTime(String taskInformation, CommandType splitLocation) {
        return taskInformation.split(splitLocation.getCommandWord());
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
                CommandType.EVENT_TIME_INDICATOR);

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
                , CommandType.DEADLINE_TIME_INDICATOR);

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

    protected Task markTaskAsDone(String command) throws TaskIndexNotFoundException,
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

    protected Task deleteTask(String command) throws TaskIndexNotFoundException,
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

    private boolean isEmpty() {
        return numberOfTasks == 0;
    }

    public void clear() {
        tasks.clear();
        numberOfTasks = 0;
    }

    public void addSavedTask(String savedTask) throws InvalidTaskTypeException {
        Task savedTaskToBeAdded;
        if (savedTask.startsWith("T")) {
            savedTaskToBeAdded = createSavedTodo(savedTask);
        } else if (savedTask.startsWith("D")) {
            savedTaskToBeAdded = createSavedDeadline(savedTask);
        } else if (savedTask.startsWith("E")) {
            savedTaskToBeAdded = createSavedEvent(savedTask);
        } else {
            throw new InvalidTaskTypeException();
        }
        tasks.add(savedTaskToBeAdded);
        numberOfSavedTasks++;
        numberOfTasks++;
    }

    private String[] splitTaskInformation(String taskInformation) {
        return taskInformation.split(":");
    }

    private Event createSavedEvent(String savedTask) {
        String[] savedEventInformation = splitTaskInformation(savedTask);
        boolean isSavedEventDone = savedEventInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedEventDescription = savedEventInformation[2].trim();
        String savedEventTime = savedEventInformation[3].trim();
        return new Event(savedEventDescription, savedEventTime, isSavedEventDone);
    }

    private Deadline createSavedDeadline(String savedTask) {
        String[] savedDeadlineInformation = splitTaskInformation(savedTask);
        boolean isSavedDeadlineDone = savedDeadlineInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedDeadlineDescription = savedDeadlineInformation[2].trim();
        String savedDeadlineTime = savedDeadlineInformation[3].trim();
        return new Deadline(savedDeadlineDescription, savedDeadlineTime, isSavedDeadlineDone);
    }

    private Todo createSavedTodo(String savedTask) {
        String[] savedTodoInformation = splitTaskInformation(savedTask);
        boolean isSavedTodoDone = savedTodoInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedTodoDescription = savedTodoInformation[2].trim();
        return new Todo(savedTodoDescription,isSavedTodoDone);
    }
}
