package duke;

import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a text user interface.
 * A <code>Ui</code> object deals with user interactions by reading the user command and showing users the
 * appropriate messages after a valid command is executed or when an error occurs.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
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
    static final String FIND_INPUT_FORMAT = "  To find task(s): find <keyword>";
    static final String CLEAR_TASK_LIST_INPUT_FORMAT = "  To clear task list: clear";
    static final String DIVIDER =
            "_____________________________________________________________________________________________";

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructs a <code>Ui</code> object.
     */
    public Ui() {
    }

    /**
     * Reads in and returns the command given.
     * @return The command given.
     */
    public String readCommand() {
        return SCANNER.nextLine();
    }

    /**
     * Prints the welcome message from Duke when it is first opened.
     */
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

    /**
     * Prints an error message when an IOException is thrown.
     *
     * @param message The place where the error occurred resulting in an IOException to be thrown.
     */
    public void printIOExceptionErrorMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "Something went wrong...");
        System.out.println(message);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when the file used to store previously saved tasks cannot be found.
     */
    public void printFileNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "There is no previously saved data.");
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when the previously saved tasks has missing data and cannot be loaded.
     */
    public void printDukeFileCannotBeLoadedErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "Saved data has errors...");
        System.out.println("Duke is unable to load previously saved data...");
        System.out.println(DIVIDER);
    }

    /**
     * Prints a message to show that the task list has been successfully cleared and is now empty.
     */
    public void printClearTaskListMessage() {
        System.out.println(DIVIDER);
        System.out.println("All previous data has been deleted...");
        System.out.println("The task list is now empty.");
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a invalid task index to delete is given and provide input suggestions.
     * A range of valid task indexes and correct input format for deleting a task will be shown.
     *
     * @param rangeOfValidTaskNumbers The range of valid task indexes of tasks that can be deleted.
     */
    public void printInvalidTaskIndexToDeleteErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to delete is invalid."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a task index to delete is not given and provide input suggestions.
     * A range of valid task indexes and correct input format for deleting a task will be shown.
     *
     * @param rangeOfValidTaskNumbers The range of valid task indexes of tasks that can be deleted.
     */
    public void printTaskIndexToDeleteNotFoundErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to delete is not found."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(DELETE_TASK_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints a message to show that the task specified has been deleted and how many tasks are still left
     * in the task list.
     *
     * @param deletedTask The task that was deleted from the task list.
     * @param getNumberOfTasks The number of tasks left in the task list.
     */
    public void printDeletedTask(Task deletedTask, int getNumberOfTasks) {
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + deletedTask);
        System.out.println("Now you have " + getNumberOfTasks + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a task index to mark as done is not given and provide input suggestions.
     * A range of valid task indexes and correct input format to mark a task as done will be shown.
     *
     * @param rangeOfValidTaskNumbers The range of valid task indexes of tasks that can be marked as done.
     */
    public void printTaskIndexToMarkAsDoneNotFoundErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to mark as done is not found."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a invalid task index to mark as done is given and provide input
     * suggestions.
     * A range of valid task indexes and correct input format to mark a task as done will be shown.
     *
     * @param rangeOfValidTaskNumbers The range of valid task indexes of tasks that can be marked as done.
     */
    public void printInvalidTaskIndexToMarkAsDoneErrorMessage(String rangeOfValidTaskNumbers) {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task to mark as done is invalid."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(RANGE_OF_VALID_TASK_NUMBER_MESSAGE + rangeOfValidTaskNumbers);
        System.out.println(MARK_TASK_AS_DONE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a command to delete or mark a task as done is given in the correct
     * format but the task list is empty.
     */
    public void printEmptyTaskListErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task list is empty." + System.lineSeparator());
        System.out.println("Please add a task to the task list.");
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when the task type of a previously saved task cannot be deciphered.
     */
    public void printInvalidTaskTypeErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The task is neither a todo nor a deadline nor an event.");
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when an invalid command is given and provide input suggestions.
     * A range of valid task indexes and correct input formats for the various commands will be shown.
     *
     * @param rangeOfValidTaskNumbers The range of valid task indexes of tasks that can be deleted or marked
     *                               as done.
     */
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
        System.out.println(FIND_INPUT_FORMAT);
        System.out.println(EXIT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a command to add a todo task without a description is given.
     */
    public void printTodoDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of a todo cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(TODO_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a command to add a deadline task without a description is given.
     */
    public void printDeadlineDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of a deadline cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a command to add a deadline task without a timing at which it is due is
     * given.
     */
    public void printDeadlineTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The time the deadline is due is not found as none or " +
                "too many timings were given." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(DEADLINE_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a command to add an event task without a description is given.
     */
    public void printEventDescriptionNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The description of an event cannot be empty."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a command to add an event task without a timing is given.
     */
    public void printEventTimeNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The event timing is not found as none or too " +
                "many timings were given." + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(EVENT_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }

    /**
     * Prints a message to show that the task specified has been added and how many tasks are in the task
     * list.
     *
     * @param newTask The new task that was added to the task list.
     * @param getNumberOfTasks The number of tasks in the task list.
     */
    public void printNewTask(Task newTask, int getNumberOfTasks) {
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("Now you have " + getNumberOfTasks + " task(s) in the list.");
        System.out.println(DIVIDER);
    }

    /**
     * Prints a message to show that the task specified has been marked as done.
     *
     * @param taskMarkedAsDone The task that was marked as done.
     */
    public void printTaskMarkedAsDone(Task taskMarkedAsDone) {
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + taskMarkedAsDone);
        System.out.println(DIVIDER);
    }

    /**
     * Prints out all the tasks in the task list in the order that they were added or a message stating
     * that the task list is empty if there are no tasks.
     *
     * @param tasks The arraylist containing all the tasks in the task list.
     */
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

    /**
     * Prints an exit message when Duke is closed.
     */
    public void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    /**
     * Prints out all the tasks in the task list containing the keyword given in the order that they were
     * added.
     * The tasks containing the keyword is numbered after its original task index in the full task list.
     *
     * @param tasksWithKeyword The arraylist containing all the tasks with the given keyword.
     * @param tasks The arraylist containing all the tasks in the task list.
     */
    public void printTasksWithKeyword(ArrayList<Task> tasksWithKeyword, ArrayList<Task> tasks) {
        System.out.println(DIVIDER);
        if (tasksWithKeyword.isEmpty()) {
            System.out.println("There are no matching task(s) in your list.");
        } else {
            int numberOfTasksWithKeyword = tasksWithKeyword.size();
            System.out.println("Here is(are) the " + numberOfTasksWithKeyword + " matching task(s) in your " +
                    "list:");
            for (Task task : tasksWithKeyword) {
                int taskIndexInUnfilteredTaskList = tasks.indexOf(task) + 1;
                System.out.println("  " + (taskIndexInUnfilteredTaskList) + "." + task);
            }
        }
        System.out.println(DIVIDER);
    }

    /**
     * Prints an error message when a keyword to search for in the task list is not given and provide the
     * correct input format for finding tasks using a keyword.
     */
    public void printKeywordNotFoundErrorMessage() {
        System.out.println(DIVIDER);
        System.out.println(START_OF_ERROR_MESSAGE + "The keyword to search for is not found."
                + System.lineSeparator());
        System.out.println(INPUT_INSTRUCTION_MESSAGE);
        System.out.println(FIND_INPUT_FORMAT);
        System.out.println(DIVIDER);
    }
}
