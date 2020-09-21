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
import java.util.Scanner;

public class Duke {

    private static final String FILE_PATH = "data/duke.txt";
    private static final String DIR_PATH = "data";
    private static TaskList taskList;
    private static Storage storage;
    private static boolean isExitingDuke;

    private static final String INPUT_INSTRUCTION_MESSAGE = "Please input in the following format:";
    private static final String RANGE_OF_VALID_TASK_NUMBER_MESSAGE = "Range of valid task number: ";
    private static final String START_OF_ERROR_MESSAGE = ":( OOPS!!! ";
    private static final String PRINT_TASK_LIST_INPUT_FORMAT = "  To list all tasks: list";
    private static final String MARK_TASK_AS_DONE_INPUT_FORMAT = "  To mark a task as done: done <task number>";
    private static final String DELETE_TASK_INPUT_FORMAT = "  To delete a task: delete <task number>";
    private static final String TODO_INPUT_FORMAT = "  To add a todo: todo <description of todo>";
    private static final String DEADLINE_INPUT_FORMAT = "  To add a deadline: deadline <description of "
            + "deadline> /by <time it is due>";
    private static final String EVENT_INPUT_FORMAT = "  To add an event: event <description of event> /at "
            + "<the event time>";
    private static final String EXIT_INPUT_FORMAT = "  To exit Duke: bye";
    private static final String CLEAR_TASK_LIST_INPUT_FORMAT = "  To clear task list: clear";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DIVIDER =
            "_____________________________________________________________________________________________";

    public static void main(String[] args) {
        printWelcomeMessage();
        initDuke();
        while (!isExitingDuke) {
            String command = getUserCommand();
            processUserCommand(command);
        }

    }

    private static void initDuke() {
        try {
            storage = new Storage(DIR_PATH, FILE_PATH);
            taskList = new TaskList(storage.load());
            isExitingDuke = false;
        } catch (FileNotFoundException e) {
            printFileNotFoundErrorMessage();
        } catch (IOException e) {
            printIOExceptionErrorMessage("Duke is unable to load previously saved data...");
        } catch (InvalidTaskTypeException e) {
            printInvalidTaskTypeErrorMessage();
        } catch (IndexOutOfBoundsException e) {
            printIndexOutOfBoundsErrorMessage();
        }
    }

    private static String getUserCommand() {
        return SCANNER.nextLine();
    }

    private static void processUserCommand(String command) {
        command = command.trim();
        if (command.equals(CommandType.BYE.getCommandWord())) {
            processExit();
        } else if (command.equals(CommandType.LIST.getCommandWord())) {
            printTaskList();
        } else if (command.startsWith(CommandType.DONE.getCommandWord())) {
            processTaskAsDone(command);
        } else if (command.startsWith(CommandType.DELETE.getCommandWord())) {
            processTaskDeletion(command);
        } else if (command.equals(CommandType.CLEAR.getCommandWord())) {
            processTaskListClearance();
        } else {
            processTaskAddition(command);
        }
    }

    private static void processExit() {
        try {
            storage.update(taskList.getTasks());
        } catch (IOException e){
            printIOExceptionErrorMessage("Duke is unable to save current data...");
        } finally {
            isExitingDuke = true;
            printExitMessage();
        }
    }

    private static void processTaskAsDone(String command) {
        try {
            Task taskMarkedAsDone = taskList.markTaskAsDone(command);
            printTaskMarkedAsDone(taskMarkedAsDone);
        } catch (TaskIndexNotFoundException e) {
            printTaskToMarkAsDoneIndexNotFoundErrorMessage();
        } catch (InvalidTaskIndexException | NumberFormatException e) {
            printInvalidTaskToMarkAsDoneIndexErrorMessage();
        } catch (EmptyTaskListException e) {
            printEmptyTaskListErrorMessage();
        }
    }

    private static void processTaskDeletion(String command) {
        try {
            Task deletedTask = taskList.deleteTask(command);
            printDeletedTask(deletedTask);
        } catch (TaskIndexNotFoundException e) {
            printTaskToDeleteIndexNotFoundErrorMessage();
        } catch (InvalidTaskIndexException | NumberFormatException e) {
            printInvalidTaskToDeleteIndexErrorMessage();
        } catch (EmptyTaskListException e) {
            printEmptyTaskListErrorMessage();
        }
    }

    private static void processTaskListClearance() {
        clearTaskList();
        printClearTaskListMessage();
    }

    private static void clearTaskList() {
        taskList.clear();
    }

    private static void processTaskAddition(String command) {
        try {
            Task newTask = taskList.addNewTask(command);
            printNewTask(newTask);
        } catch (TodoDescriptionNotFoundException e) {
            printTodoDescriptionNotFoundErrorMessage();
        } catch (DeadlineDescriptionNotFoundException e) {
            printDeadlineDescriptionNotFoundErrorMessage();
        } catch (DeadlineTimeNotFoundException e) {
            printDeadlineTimeNotFoundErrorMessage();
        } catch (EventDescriptionNotFoundException e) {
            printEventDescriptionNotFoundErrorMessage();
        } catch (EventTimeNotFoundException e) {
            printEventTimeNotFoundErrorMessage();
        } catch (InvalidCommandException e) {
            printInvalidCommandErrorMessage();
        }
    }

    private static void printWelcomeMessage() {
        String logo = " ____        _" + System.lineSeparator()
                + "|  _ \\ _   _| | _____" + System.lineSeparator()
                + "| | | | | | | |/ / _ \\" + System.lineSeparator()
                + "| |_| | |_| |   <  __/" + System.lineSeparator()
                + "|____/ \\__,_|_|\\_\\___|" + System.lineSeparator();
        System.out.println("Hello from" + System.lineSeparator() + logo);
        System.out.println(DIVIDER);
        System.out.println("Hello! I'm Duke" + System.lineSeparator() + "What can I do for you?");
        System.out.println(DIVIDER);
    }

    private static void printIOExceptionErrorMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "Something went wrong...");
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    private static void printFileNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "There is no previously saved data.");
        System.out.println(DIVIDER);
    }

    private static void printIndexOutOfBoundsErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "Saved data has errors...");
        System.out.println("Duke is unable to load previously saved data...");
        System.out.println(DIVIDER);
    }

    private static void printClearTaskListMessage() {
        System.out.println(DIVIDER);
        System.out.println("All previous data has been deleted...");
        System.out.println("The task list is now empty.");
        System.out.println(DIVIDER);
    }

    private static void printInvalidTaskToDeleteIndexErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to delete is invalid." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + taskList.getRangeOfValidTaskNumber());
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printTaskToDeleteIndexNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to delete is not found." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + taskList.getRangeOfValidTaskNumber());
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printDeletedTask(Task deletedTask) {
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + taskList.getNumberOfTasks() + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    private static void printTaskToMarkAsDoneIndexNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to mark as done is not found."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + taskList.getRangeOfValidTaskNumber());
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printInvalidTaskToMarkAsDoneIndexErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to mark as done is invalid."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + taskList.getRangeOfValidTaskNumber());
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printEmptyTaskListErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task list is empty." + System.lineSeparator());
        System.out.println("Please add a task to the task list.");
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printInvalidTaskTypeErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task is neither a todo nor a deadline nor an event.");
        System.out.println(DIVIDER);
    }


    private static void printInvalidCommandErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "I'm sorry, but I don't know what that means :-("
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + taskList.getRangeOfValidTaskNumber());
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(PRINT_TASK_LIST_INPUT_FORMAT);
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(CLEAR_TASK_LIST_INPUT_FORMAT);
        System.out.println(EXIT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printTodoDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of a todo cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of a deadline cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The time the deadline is due is not found as none or " +
                "too many timings were given." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printEventDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of an event cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printEventTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The event timing is not found as none or too " +
                "many timings were given." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    private static void printNewTask(Task newTask) {
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("Now you have " + (taskList.getNumberOfTasks()) + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    private static void printTaskMarkedAsDone(Task taskMarkedAsDone) {
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + taskMarkedAsDone);
        System.out.println(DIVIDER);
    }

    private static void printTaskList() {
        System.out.println(DIVIDER);
        if (taskList.isEmpty()) {
            System.out.println("The task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            taskList.printTasks();
        }
        System.out.println(DIVIDER);
    }

    private static void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }
}
