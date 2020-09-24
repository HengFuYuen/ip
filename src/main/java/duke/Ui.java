package duke;

import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    static final String INPUT_INSTRUCTION_MESSAGE = "Please input in the following format:";
    static final String RANGE_OF_VALID_TASK_NUMBER_MESSAGE = "Range of valid task number: ";
    static final String START_OF_ERROR_MESSAGE = ":( OOPS!!! ";
    static final String PRINT_TASK_LIST_INPUT_FORMAT = "  To list all tasks: list";
    static final String MARK_TASK_AS_DONE_INPUT_FORMAT = "  To mark a task as done: done <task number>";
    static final String DELETE_TASK_INPUT_FORMAT = "  To delete a task: delete <task number>";
    static final String TODO_INPUT_FORMAT = "  To add a todo: todo <description of todo>";
    static final String DEADLINE_INPUT_FORMAT = "  To add a deadline: deadline <description of "
            + "deadline> /by <time it is due>";
    static final String EVENT_INPUT_FORMAT = "  To add an event: event <description of event> /at "
            + "<the event time>";
    static final String EXIT_INPUT_FORMAT = "  To exit Duke: bye";
    static final String CLEAR_TASK_LIST_INPUT_FORMAT = "  To clear task list: clear";
    static final String DIVIDER =
            "_____________________________________________________________________________________________";

    private static final Scanner SCANNER = new Scanner(System.in);

    public Ui() {
    }

    public String readCommand() {
        return SCANNER.nextLine();
    }

    public void printWelcomeMessage() {
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

    public void printIOExceptionErrorMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "Something went wrong...");
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    public void printFileNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "There is no previously saved data.");
        System.out.println(DIVIDER);
    }

    public void printIndexOutOfBoundsErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "Saved data has errors...");
        System.out.println("Duke is unable to load previously saved data...");
        System.out.println(DIVIDER);
    }

    public void printClearTaskListMessage() {
        System.out.println(DIVIDER);
        System.out.println("All previous data has been deleted...");
        System.out.println("The task list is now empty.");
        System.out.println(DIVIDER);
    }

    public void printInvalidTaskToDeleteIndexErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to delete is invalid."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printDeleteTaskIndexNotFoundErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to delete is not found." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printDeletedTask(Task deletedTask, int getNumberOfTasks) {
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + getNumberOfTasks + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    public void printMarkAsDoneTaskIndexNotFoundErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to mark as done is not found."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printInvalidTaskToMarkAsDoneIndexErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to mark as done is invalid."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printEmptyTaskListErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task list is empty." + System.lineSeparator());
        System.out.println("Please add a task to the task list.");
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printInvalidTaskTypeErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task is neither a todo nor a deadline nor an event.");
        System.out.println(DIVIDER);
    }

    public void printInvalidCommandErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "I'm sorry, but I don't know what that means :-("
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
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

    public void printTodoDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of a todo cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printDeadlineDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of a deadline cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printDeadlineTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The time the deadline is due is not found as none or " +
                "too many timings were given." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printEventDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of an event cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printEventTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The event timing is not found as none or too " +
                "many timings were given." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    public void printNewTask(Task newTask, int getNumberOfTasks) {
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("Now you have " + getNumberOfTasks + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    public void printTaskMarkedAsDone(Task taskMarkedAsDone) {
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + taskMarkedAsDone);
        System.out.println(DIVIDER);
    }

    public void printTaskList(ArrayList<Task> tasks) {
        System.out.println(DIVIDER);
        if (tasks.isEmpty()) {
            System.out.println("The task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            int taskNumber = 1;
            for (Task task : tasks) {
                System.out.println("  " + (taskNumber) + "." + task);
                taskNumber++;
            }
        }
        System.out.println(DIVIDER);
    }

    public void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }
}
