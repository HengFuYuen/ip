/**
 * Solution below adapted from
 * https://github.com/nus-cs2113-AY2021S1/contacts/blob/master/src/main/java/Contacts1.java
 */

import java.util.Scanner;

public class Duke {

    private static final int MAX_NUMBER_OF_TASKS = 100;
    private static Task[] tasks = new Task[MAX_NUMBER_OF_TASKS];
    private static int taskNumber = 0;

    private static final String inputInstruction = "Please input in the following format:";
    private static final String printTaskListInputFormat = "  To list all tasks: list";
    private static String markTaskAsDoneInputFormat = "  To mark a task as done: done <task number>"
            + System.lineSeparator() + "      Range of valid task number: ";
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
        while (true) {
            String command = getUserCommand();
            processUserCommand(command);
        }

    }

    private static void processUserCommand(String command) {
        command = command.trim();
        if (command.equals("bye")) {
            processExit();
        } else if (command.equals("list")) {
            printTaskList();
        } else if (command.startsWith("done")) {
            processTaskAsDone(command);
        } else {
            processTaskAddition(command);
        }

    }

    private static void processTaskAsDone(String command) {
        try {
            Task finishedTask = markTaskAsDone(command);
            printFinishedTask(finishedTask);
        } catch (TaskToMarkAsDoneNotFoundException e) {
            printTaskToMarkAsDoneNotFoundErrorMessage();
        } catch (TaskToMarkAsDoneInvalidException e) {
            printTaskToMarkAsDoneInvalidErrorMessage();
        } catch (NoTaskInTaskListException e) {
            printNoTaskInTaskListErrorMessage();
        } catch (NumberFormatException e) {
            printTaskToMarkAsDoneInvalidErrorMessage();
        }
    }

    private static String getRangeOfValidTaskNumber() {
        return taskNumber == 0 ? "none" : "1 to " + taskNumber;
    }

    private static void printTaskToMarkAsDoneNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The task to mark as done is not found." + System.lineSeparator());
        System.out.println(inputInstruction);
        System.out.println(markTaskAsDoneInputFormat + getRangeOfValidTaskNumber());
        System.out.println(DIVIDER);
    }

    private static void printTaskToMarkAsDoneInvalidErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The task to mark as done is invalid." + System.lineSeparator());
        System.out.println(inputInstruction);
        System.out.println(markTaskAsDoneInputFormat + getRangeOfValidTaskNumber());
        System.out.println(DIVIDER);
    }

    private static void printNoTaskInTaskListErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The task list is empty." + System.lineSeparator()
                + "Please add a task before marking it as done.");
        System.out.println(todoInputFormat);
        System.out.println(deadlineInputFormat);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
    }

    private static void processTaskAddition(String command) {
        try {
            Task newTask = addNewTask(command);
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
        System.out.println(inputInstruction);
        System.out.println(todoInputFormat);
        System.out.println(deadlineInputFormat);
        System.out.println(eventInputFormat);
        System.out.println(printTaskListInputFormat);
        System.out.println(markTaskAsDoneInputFormat + getRangeOfValidTaskNumber());
        System.out.println(exitDukeInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printTodoDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The description of a todo cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstruction);
        System.out.println(todoInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The description of a deadline cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstruction);
        System.out.println(deadlineInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The time the deadline is due is not found."
                + System.lineSeparator());
        System.out.println(inputInstruction);
        System.out.println(deadlineInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEventDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The description of an event cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstruction);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEventTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println("\u2639 OOPS!!! The timing of the event is not found." + System.lineSeparator());
        System.out.println(inputInstruction);
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
        System.out.println("Now you have " + (taskNumber) + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    private static Task addNewTask(String command) throws TaskDescriptionNotFoundException {
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
        tasks[taskNumber] = newTask;
        taskNumber++;
        return newTask;
    }

    private static String[] splitDescriptionAndTime(String taskInformation, String splitLocation) {
        return taskInformation.split(splitLocation);
    }

    private static Task createEvent(String command) throws EventDescriptionNotFoundException,
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

    private static Task createDeadline(String command) throws DeadlineDescriptionNotFoundException,
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

    private static Task createTodo(String command) throws TodoDescriptionNotFoundException {
        if (command.length() <= 5) {
            throw new TodoDescriptionNotFoundException();
        }
        String todoDescription = command.substring(4).trim();
        return new Todo(todoDescription);
    }

    private static void printFinishedTask(Task finishedTask) {
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + finishedTask);
        System.out.println(DIVIDER);
    }

    private static Task markTaskAsDone(String command) throws TaskToMarkAsDoneNotFoundException,
            TaskToMarkAsDoneInvalidException, NoTaskInTaskListException {
        if (taskNumber == 0) {
            throw new NoTaskInTaskListException();
        }
        if (command.length() <= 4) {
            throw new TaskToMarkAsDoneNotFoundException();
        }
        int finishedTaskNumber = Integer.parseInt(command.substring(4).trim()) - 1;

        if (finishedTaskNumber < 0 || finishedTaskNumber >= taskNumber) {
            throw new TaskToMarkAsDoneInvalidException();
        }

        Task finishedTask = tasks[finishedTaskNumber];
        finishedTask.markAsDone();
        return finishedTask;
    }

    private static void printTaskList() {
        System.out.println(DIVIDER);
        if (taskNumber == 0) {
            System.out.println("The task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskNumber; i++) {
                Task currentTask = tasks[i];
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
        String logo = " ____        _        " + System.lineSeparator()
                + "|  _ \\ _   _| | _____ " + System.lineSeparator()
                + "| | | | | | | |/ / _ \\" + System.lineSeparator()
                + "| |_| | |_| |   <  __/" + System.lineSeparator()
                + "|____/ \\__,_|_|\\_\\___|" + System.lineSeparator();
        System.out.println("Hello from" + System.lineSeparator() + logo);
        System.out.println(DIVIDER);
        System.out.println("Hello! I'm Duke" + System.lineSeparator() + "What can I do for you?");
        System.out.println(DIVIDER);
    }
}
