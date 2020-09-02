/**
 * Solution below adapted from
 * https://github.com/nus-cs2113-AY2021S1/contacts/blob/master/src/main/java/Contacts1.java
 */

import java.util.Scanner;

public class Duke {

    private static final int MAX_NUMBER_OF_TASKS = 100;
    private static Task[] tasks = new Task[MAX_NUMBER_OF_TASKS];
    private static int taskNumber = 0;

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        printWelcomeMessage();
        while (true) {
            String command = getUserCommand();
            processUserCommand(command);
        }

    }

    private static void processUserCommand(String command) {
        if (command.equals("bye")) {
            printExitMessage();
            exitDuke();
        } else if (command.equals("list")) {
            printTaskList();
        } else if (command.startsWith("done")) {
            Task finishedTask = markTaskAsDone(command);
            printFinishedTask(finishedTask);
        } else {
            Task newTask = addNewTask(command);
            printNewTask(newTask);
        }
    }

    private static void printNewTask(Task newTask) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:" + System.lineSeparator() + "  " + newTask
                + System.lineSeparator() + "Now you have " + (taskNumber) + " task(s) in the list.");
        System.out.println("____________________________________________________________");
    }

    private static Task addNewTask(String command) {
        Task newTask;
        if (command.startsWith("todo")) {
            newTask = createTodo(command);
        } else if (command.startsWith("deadline")) {
            newTask = createDeadline(command);
        } else if (command.startsWith("event")) {
            newTask = createEvent(command);
        } else {
            newTask = createTask(command);
        }
        tasks[taskNumber] = newTask;
        taskNumber++;
        return newTask;
    }

    private static Task createTask(String command) {
        return new Task(command);
    }

    private static String[] splitDescriptionAndTime(String taskInformation, String splitLocation) {
        return taskInformation.split(splitLocation);
    }

    private static Task createEvent(String command) {
        String eventInformation = command.substring(6);
        String[] eventDescriptionAndTime = splitDescriptionAndTime(eventInformation, " /at ");
        return new Event(eventDescriptionAndTime[0], eventDescriptionAndTime[1]);
    }

    private static Task createDeadline(String command) {
        String deadlineInformation = command.substring(9);
        String[] deadlineDescriptionAndTime = splitDescriptionAndTime(deadlineInformation, " /by ");
        return new Deadline(deadlineDescriptionAndTime[0], deadlineDescriptionAndTime[1]);
    }

    private static Task createTodo(String command) {
        String todoDescription = command.substring(5);
        return new Todo(todoDescription);
    }

    private static void printFinishedTask(Task finishedTask) {
        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:" + System.lineSeparator()
                + "  " + finishedTask);
        System.out.println("____________________________________________________________");
    }

    private static Task markTaskAsDone(String command) {
        int finishedTaskNumber = Integer.parseInt(command.substring(5)) - 1;
        Task finishedTask = tasks[finishedTaskNumber];
        finishedTask.markAsDone();
        return finishedTask;
    }

    private static void printTaskList() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskNumber; i++) {
            Task currentTask = tasks[i];
            System.out.println((i + 1) + "." + currentTask);
        }
        System.out.println("____________________________________________________________");
    }

    private static void exitDuke() {
        System.exit(0);
    }

    private static void printExitMessage() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
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
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Duke" + System.lineSeparator() + "What can I do for you?");
        System.out.println("____________________________________________________________");
    }
}
