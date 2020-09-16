package duke;

/**
 * Solution below adapted from
 * https://github.com/nus-cs2113-AY2021S1/contacts/blob/master/src/main/java/Contacts1.java
 */

import duke.exception.TaskDescriptionNotFoundException;
import duke.exception.TodoDescriptionNotFoundException;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.NoTaskInTaskListException;
import duke.exception.TaskIndexIsInvalidException;
import duke.exception.TaskIndexNotFoundException;
import duke.task.Task;

import java.util.Scanner;

public class Duke {

    private static TaskList taskList;

    private static final String inputInstructionMessage = "Please input in the following format:";
    private static String rangeOfValidTaskNumberMessage = "Range of valid task number: ";
    private static final String printTaskListInputFormat = "  To list all tasks: list";
    private static String markTaskAsDoneInputFormat = "  To mark a task as done: done <task number>";
    private static String deleteTaskInputFormat = "  To delete a task: delete <task number>";
    private static final String todoInputFormat = "  To add a todo: todo <description of todo>";
    private static final String deadlineInputFormat = "  To add a deadline: deadline <description of "
            + "deadline> /by <time it is due>";
    private static final String eventInputFormat = "  To add an event: event <description of event> /at "
            + "<the event time>";
    private static final String exitDukeInputFormat = "  To exit Duke: bye";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DIVIDER =
            "_______________________________________________________________________________";


    public static void main(String[] args) {
        printWelcomeMessage();
        initTaskList();
        while (true) {
            String command = getUserCommand();
            processUserCommand(command);
        }

    }

    private static void initTaskList() {
        taskList = new TaskList();
    }

    private static void processUserCommand(String command) {
        command = command.trim();
        if (command.equals("bye")) {
            processExit();
        } else if (command.equals("list")) {
            printTaskList();
        } else if (command.startsWith("done")) {
            processTaskAsDone(command);
        } else if (command.startsWith("delete")) {
            processTaskDeletion(command);
        } else {
            processTaskAddition(command);
        }

    }

    private static void processTaskDeletion(String command) {
        String commandType = "delete";
        try {
            Task deletedTask = taskList.deleteTask(command);
            printDeletedTask(deletedTask);
        } catch (TaskIndexNotFoundException e) {
            printTaskIndexNotFoundErrorMessage(commandType);
        } catch (TaskIndexIsInvalidException | NumberFormatException e) {
            printTaskIndexIsInvalidErrorMessage(commandType);
        } catch (NoTaskInTaskListException e) {
            printNoTaskInTaskListErrorMessage();
        }
    }

    private static void printDeletedTask(Task deletedTask) {
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + taskList.getNumberOfTasks() + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    private static void processTaskAsDone(String command) {
        String commandType = "done";
        try {
            Task taskMarkedAsDone = taskList.markTaskAsDone(command);
            printTaskMarkedAsDone(taskMarkedAsDone);
        } catch (TaskIndexNotFoundException e) {
            printTaskIndexNotFoundErrorMessage(commandType);
        } catch (TaskIndexIsInvalidException | NumberFormatException e) {
            printTaskIndexIsInvalidErrorMessage(commandType);
        } catch (NoTaskInTaskListException e) {
            printNoTaskInTaskListErrorMessage();
        }
    }

    private static void printTaskIndexNotFoundErrorMessage(String commandType) {
        System.out.println(DIVIDER);
        if (commandType.equals("done")) {
            System.out.println("\u2639 OOPS!!! The task to mark as done is not found.");
            System.out.println(inputInstructionMessage);
            System.out.println(rangeOfValidTaskNumberMessage+ taskList.getRangeOfValidTaskNumber());
            System.out.println(markTaskAsDoneInputFormat);
        }
        if (commandType.equals("delete")) {
            System.out.println("\u2639 OOPS!!! The task to delete is not found.");
            System.out.println(inputInstructionMessage);
            System.out.println(rangeOfValidTaskNumberMessage+ taskList.getRangeOfValidTaskNumber());
            System.out.println(deleteTaskInputFormat);
        }
        System.out.println(DIVIDER);
    }

    private static void printTaskIndexIsInvalidErrorMessage(String commandType) {
        System.out.println(DIVIDER);
        if (commandType.equals("done")) {
            System.out.println("\u2639 OOPS!!! The task to mark as done is invalid.");
            System.out.println(inputInstructionMessage);
            System.out.println(rangeOfValidTaskNumberMessage+ taskList.getRangeOfValidTaskNumber());
            System.out.println(markTaskAsDoneInputFormat);
        }
        if (commandType.equals("delete")) {
            System.out.println("\u2639 OOPS!!! The task to delete is invalid." );
            System.out.println(inputInstructionMessage);
            System.out.println(rangeOfValidTaskNumberMessage+ taskList.getRangeOfValidTaskNumber());
            System.out.println(deleteTaskInputFormat);
        }
        System.out.println(DIVIDER);
    }

    private static void printNoTaskInTaskListErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The task list is empty.");
        System.out.println("Please add a task to the task list.");
        System.out.println(todoInputFormat);
        System.out.println(deadlineInputFormat);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
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
        } catch (TaskDescriptionNotFoundException e) {
            printTaskDescriptionNotFoundErrorMessage();
        }
    }

    private static void printTaskDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-("
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(rangeOfValidTaskNumberMessage+ taskList.getRangeOfValidTaskNumber());
        System.out.println(todoInputFormat);
        System.out.println(deadlineInputFormat);
        System.out.println(eventInputFormat);
        System.out.println(printTaskListInputFormat);
        System.out.println(markTaskAsDoneInputFormat);
        System.out.println(deleteTaskInputFormat);
        System.out.println(exitDukeInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printTodoDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The description of a todo cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(todoInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The description of a deadline cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(deadlineInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The time the deadline is due is not found."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(deadlineInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEventDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The description of an event cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEventTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The timing of the event is not found." + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
    }

    private static void processExit() {
        printExitMessage();
        exitDuke();
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
        if (taskList.getNumberOfTasks() == 0) {
            System.out.println("The task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
                Task currentTask = taskList.getTask(i);
                System.out.println("  " + (i + 1) + "." + currentTask);
            }
        }
        System.out.println(DIVIDER);
    }

    private static void exitDuke() {
        System.exit(0);
    }

    private static void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    private static String getUserCommand() {
        return SCANNER.nextLine();
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
}