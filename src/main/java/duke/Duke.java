package duke;

/*
  Solution below adapted from
  https://github.com/nus-cs2113-AY2021S1/contacts/blob/master/src/main/java/Contacts1.java
 */

import duke.exception.InvalidCommandException;
import duke.exception.InvalidTaskTypeException;
import duke.exception.TodoDescriptionNotFoundException;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.exception.TaskIndexNotFoundException;
import duke.task.Task;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Duke {

    private static final String FILE_PATH = "data/duke.txt";
    private static final String DIR_PATH = "data";
    private static TaskList taskList;
    private static Storage storage;
    private static Ui ui;
    private static boolean isExitingDuke;

    public static void main(String[] args) {
        initDuke();
        while (!isExitingDuke) {
            String fullCommand = ui.readCommand();
            processUserCommand(fullCommand);
        }

    }

    private static void initDuke() {
        try {
            ui = new Ui();
            ui.printWelcomeMessage();
            storage = new Storage(DIR_PATH, FILE_PATH);
            taskList = new TaskList(storage.load());
            isExitingDuke = false;
        } catch (FileNotFoundException e) {
            ui.printFileNotFoundErrorMessage();
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to load previously saved data...");
        } catch (InvalidTaskTypeException e) {
            ui.printInvalidTaskTypeErrorMessage();
        } catch (IndexOutOfBoundsException e) {
            ui.printIndexOutOfBoundsErrorMessage();
        }
    }

    private static void processUserCommand(String fullCommand) {
        fullCommand = fullCommand.trim();
        if (fullCommand.equals(CommandType.BYE.getCommandWord())) {
            processExit();
        } else if (fullCommand.equals(CommandType.LIST.getCommandWord())) {
            ui.printTaskList(taskList.getTasks());
        } else if (fullCommand.startsWith(CommandType.DONE.getCommandWord())) {
            processTaskAsDone(fullCommand);
        } else if (fullCommand.startsWith(CommandType.DELETE.getCommandWord())) {
            processTaskDeletion(fullCommand);
        } else if (fullCommand.equals(CommandType.CLEAR.getCommandWord())) {
            processTaskListClearance();
        } else {
            processTaskAddition(fullCommand);
        }
    }

    private static void processExit() {
        try {
            storage.update(taskList.getTasks());
        } catch (IOException e){
            ui.printIOExceptionErrorMessage("Duke is unable to save current data...");
        } finally {
            isExitingDuke = true;
            ui.printExitMessage();
        }
    }

    private static void processTaskAsDone(String fullCommand) {
        try {
            Task taskMarkedAsDone = taskList.markTaskAsDone(fullCommand);
            ui.printTaskMarkedAsDone(taskMarkedAsDone);
        } catch (TaskIndexNotFoundException e) {
            ui.printTaskToMarkAsDoneIndexNotFoundErrorMessage(taskList.getRangeOfValidTaskNumber());
        } catch (InvalidTaskIndexException | NumberFormatException e) {
            ui.printInvalidTaskToMarkAsDoneIndexErrorMessage(taskList.getRangeOfValidTaskNumber());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }

    private static void processTaskDeletion(String fullCommand) {
        try {
            Task deletedTask = taskList.deleteTask(fullCommand);
            ui.printDeletedTask(deletedTask, taskList.getNumberOfTasks());
        } catch (TaskIndexNotFoundException e) {
            ui.printTaskToDeleteIndexNotFoundErrorMessage(taskList.getRangeOfValidTaskNumber());
        } catch (InvalidTaskIndexException | NumberFormatException e) {
            ui.printInvalidTaskToDeleteIndexErrorMessage(taskList.getRangeOfValidTaskNumber());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }

    private static void processTaskListClearance() {
        clearTaskList();
        ui.printClearTaskListMessage();
    }

    private static void clearTaskList() {
        taskList.clear();
    }

    private static void processTaskAddition(String fullCommand) {
        try {
            Task newTask = taskList.addNewTask(fullCommand);
            ui.printNewTask(newTask, taskList.getNumberOfTasks());
        } catch (TodoDescriptionNotFoundException e) {
            ui.printTodoDescriptionNotFoundErrorMessage();
        } catch (DeadlineDescriptionNotFoundException e) {
            ui.printDeadlineDescriptionNotFoundErrorMessage();
        } catch (DeadlineTimeNotFoundException e) {
            ui.printDeadlineTimeNotFoundErrorMessage();
        } catch (EventDescriptionNotFoundException e) {
            ui.printEventDescriptionNotFoundErrorMessage();
        } catch (EventTimeNotFoundException e) {
            ui.printEventTimeNotFoundErrorMessage();
        } catch (InvalidCommandException e) {
            ui.printInvalidCommandErrorMessage(taskList.getRangeOfValidTaskNumber());
        }
    }
}
