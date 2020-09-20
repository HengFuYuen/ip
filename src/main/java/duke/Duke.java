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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;

public class Duke {

    private static TaskList taskList;

    private static final String inputInstructionMessage = "Please input in the following format:";
    private static final String rangeOfValidTaskNumberMessage = "Range of valid task number: ";
    private static final String printTaskListInputFormat = "  To list all tasks: list";
    private static final String markTaskAsDoneInputFormat = "  To mark a task as done: done <task number>";
    private static final String deleteTaskInputFormat = "  To delete a task: delete <task number>";
    private static final String todoInputFormat = "  To add a todo: todo <description of todo>";
    private static final String deadlineInputFormat = "  To add a deadline: deadline <description of "
            + "deadline> /by <time it is due>";
    private static final String eventInputFormat = "  To add an event: event <description of event> /at "
            + "<the event time>";
    private static final String exitDukeInputFormat = "  To exit Duke: bye";
    private static final String clearTaskListInputFormat = "  To clear task list: clear";
    private static final String startOfErrorMessage = ":( OOPS!!! ";


    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DIVIDER =
            "_______________________________________________________________________________";

    private static final File dukeDir = new File("data");
    private static final File dukeFile = new File("data/duke.txt");


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
        try {
            if (hasDukeDir(dukeDir) && hasDukeFile(dukeFile)) {
                printLoadingDukeFileMessage();
                loadDukeFile(dukeFile);
                printLoadingOfDukeFileIsSuccessfulMessage();
            }
            if (!hasDukeDir(dukeDir)) {
                createDukeDir(dukeDir);
            }
            if (!hasDukeFile(dukeFile)) {
                createDukeFile(dukeFile);
            }
        } catch (FileNotFoundException e) {
            printFileNotFoundErrorMessage();
        } catch (IOException e) {
            printIOExceptionErrorMessage(e.getMessage());
        }
    }

    private static void printIOExceptionErrorMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "Something went wrong..." + message);
        System.out.println(DIVIDER);
    }

    private static void printFileNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "There is no previously saved data.");
        System.out.println(DIVIDER);
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

    private static void processTaskListClearance() {
        clearTaskList();
        printClearTaskListMessage();
    }

    private static void printClearTaskListMessage() {
        System.out.println(DIVIDER);
        System.out.println("All previous data has been deleted...");
        System.out.println("The task list is now empty.");
        System.out.println(DIVIDER);
    }

    private static void clearTaskList() {
        taskList.clear();
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

    private static void printInvalidTaskToDeleteIndexErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The task to delete is invalid." + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(rangeOfValidTaskNumberMessage + taskList.getRangeOfValidTaskNumber());
        System.out.println(deleteTaskInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printTaskToDeleteIndexNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The task to delete is not found." + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(rangeOfValidTaskNumberMessage + taskList.getRangeOfValidTaskNumber());
        System.out.println(deleteTaskInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeletedTask(Task deletedTask) {
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + taskList.getNumberOfTasks() + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    private static void processTaskAsDone(String command) {
        try {
            Task taskMarkedAsDone = taskList.markTaskAsDone(command);
            editTaskInDukeFile();
            printTaskMarkedAsDone(taskMarkedAsDone);
        } catch (TaskIndexNotFoundException e) {
            printTaskToMarkAsDoneIndexNotFoundErrorMessage();
        } catch (InvalidTaskIndexException | NumberFormatException e) {
            printInvalidTaskToMarkAsDoneIndexErrorMessage();
        } catch (EmptyTaskListException e) {
            printEmptyTaskListErrorMessage();
        } catch (IOException e) {
            printIOExceptionErrorMessage(e.getMessage());
        }
    }

    private static void printTaskToMarkAsDoneIndexNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The task to mark as done is not found."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(rangeOfValidTaskNumberMessage + taskList.getRangeOfValidTaskNumber());
        System.out.println(markTaskAsDoneInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printInvalidTaskToMarkAsDoneIndexErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The task to mark as done is invalid."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(rangeOfValidTaskNumberMessage + taskList.getRangeOfValidTaskNumber());
        System.out.println(markTaskAsDoneInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEmptyTaskListErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The task list is empty." + System.lineSeparator());
        System.out.println("Please add a task to the task list.");
        System.out.println(todoInputFormat);
        System.out.println(deadlineInputFormat);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
    }

    private static void processTaskAddition(String command) {
        try {
            Task newTask = taskList.addNewTask(command);
            boolean firstTask = taskList.getNumberOfTasks() == 0;
            if (taskList.getNumberOfTasks() > taskList.getNumberOfSavedTasks()) {
                addTaskToDukeFile(newTask, firstTask);
            }
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
        } catch (IOException e) {
            printIOExceptionErrorMessage(e.getMessage());
        }
    }

    private static void printInvalidTaskTypeErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The task is neither a todo nor a deadline nor an event.");
        System.out.println(DIVIDER);
    }


    private static void printInvalidCommandErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "I'm sorry, but I don't know what that means :-("
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(rangeOfValidTaskNumberMessage + taskList.getRangeOfValidTaskNumber());
        System.out.println(todoInputFormat);
        System.out.println(deadlineInputFormat);
        System.out.println(eventInputFormat);
        System.out.println(printTaskListInputFormat);
        System.out.println(markTaskAsDoneInputFormat);
        System.out.println(deleteTaskInputFormat);
        System.out.println(clearTaskListInputFormat);
        System.out.println(exitDukeInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printTodoDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The description of a todo cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(todoInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The description of a deadline cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(deadlineInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printDeadlineTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The time the deadline is due is not found."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(deadlineInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEventDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The description of an event cannot be empty."
                + System.lineSeparator());
        System.out.println(inputInstructionMessage);
        System.out.println(eventInputFormat);
        System.out.println(DIVIDER);
    }

    private static void printEventTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "The timing of the event is not found."
                + System.lineSeparator());
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

    private static void createDukeFile(File dukeFile) throws IOException {
        dukeFile.createNewFile();
    }

    private static boolean hasDukeFile(File dukeFile) {
        return dukeFile.exists();
    }

    private static boolean hasDukeDir(File dukeDir) {
        return dukeDir.exists();
    }

    private static void createDukeDir(File dukeDir) {
        dukeDir.mkdir();
    }


    private static void printLoadingDukeFileMessage() {
        System.out.println(DIVIDER);
        System.out.println("Loading previously saved data...");
        System.out.println(DIVIDER);
    }


    private static void loadDukeFile(File dukeFile) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(dukeFile);
        while (fileScanner.hasNext()) {
            String savedTask = fileScanner.nextLine();
            processSavedTaskAddition(savedTask);
        }
        fileScanner.close();
    }

    private static void printLoadingOfDukeFileIsSuccessfulMessage() {
        System.out.println(DIVIDER);
        System.out.println(":) Duke successfully loaded all previous data!");
        System.out.println(DIVIDER);
    }

    private static void processSavedTaskAddition(String savedTask) {
        try {
            taskList.addSavedTask(savedTask);
        } catch (InvalidTaskTypeException e) {
            printInvalidTaskTypeErrorMessage();
        } catch (IndexOutOfBoundsException e) {
            printIndexOutOfBoundsErrorMessage();
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

    private static void printIndexOutOfBoundsErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(startOfErrorMessage + "Saved data has errors...");
        System.out.println("Duke is unable to load previously saved data...");
        System.out.println(DIVIDER);
    }

    private static void editTaskInDukeFile() throws IOException {
        Files.delete(Paths.get("data/duke.txt"));
        createDukeFile(dukeFile);
        boolean firstTask = true;
        for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
            addTaskToDukeFile(taskList.getTask(i), firstTask);
            firstTask = false;
        }
    }

    private static void addTaskToDukeFile(Task newTask, boolean firstTask) throws IOException {
        FileWriter fw = new FileWriter(dukeFile, true);
        if (!firstTask) {
            fw.write(System.lineSeparator());
        }
        fw.write(newTask.messageToStoreInDukeFile());
        fw.close();
    }
}
