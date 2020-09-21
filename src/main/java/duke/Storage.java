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

public class Storage {

    private static final String SAVED_TODO_INDICATOR = "T";
    private static final String SAVED_DEADLINE_INDICATOR = "D";
    private static final String SAVED_EVENT_INDICATOR = "E";
    private static final String TASK_INFORMATION_SPLIT_LOCATION = ":";
    private final File dukeFile;

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
        return taskInformation.split(TASK_INFORMATION_SPLIT_LOCATION);
    }

    private Event createSavedEvent(String savedTask) {
        String[] savedEventInformation = splitSavedTaskInformation(savedTask);
        boolean isSavedEventDone = savedEventInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedEventDescription = savedEventInformation[2].trim();
        String savedEventTime = savedEventInformation[3].trim();
        return new Event(savedEventDescription, savedEventTime, isSavedEventDone);
    }

    private Deadline createSavedDeadline(String savedTask) {
        String[] savedDeadlineInformation = splitSavedTaskInformation(savedTask);
        boolean isSavedDeadlineDone = savedDeadlineInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedDeadlineDescription = savedDeadlineInformation[2].trim();
        String savedDeadlineTime = savedDeadlineInformation[3].trim();
        return new Deadline(savedDeadlineDescription, savedDeadlineTime, isSavedDeadlineDone);
    }

    private Todo createSavedTodo(String savedTask) {
        String[] savedTodoInformation = splitSavedTaskInformation(savedTask);
        boolean isSavedTodoDone = savedTodoInformation[1].trim().equals(Task.TASK_DONE_ICON);
        String savedTodoDescription = savedTodoInformation[2].trim();
        return new Todo(savedTodoDescription, isSavedTodoDone);
    }

    private void addTaskToDukeFile(Task taskToSave, boolean firstTask) throws IOException {
        FileWriter fw = new FileWriter(dukeFile, true);
        if (!firstTask) {
            fw.write(System.lineSeparator());
        }
        fw.write(taskToSave.messageToStoreInDukeFile());
        fw.close();
    }

    public void update(ArrayList<Task> tasks) throws IOException {
        dukeFile.delete();
        dukeFile.createNewFile();
        boolean firstTask = true;
        for (Task task : tasks) {
            addTaskToDukeFile(task, firstTask);
            firstTask = false;
        }
    }
}
