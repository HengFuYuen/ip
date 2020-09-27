package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.task.Task;

import java.io.IOException;

/**
 * Represents a user instruction to mark a specified task as done.
 * A <code>DoneCommand</code> will signify to <code>Duke</code> to mark the specified task from the task
 * list as done.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class DoneCommand extends Command {

    /* A string that signifies the input is a done command */
    public static final String COMMAND_WORD = "done";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    /* The task index of the task to be marked as done */
    private final int taskIndex;

    /**
     * Constructs a <code>DoneCommand</code> object given the task index of the task to be marked as done.
     */
    public DoneCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Marks the task specified as done, updates the task in the storage file and informs the user that
     * the task has been marked as done.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task taskMarkedAsDone = tasks.markTaskAsDone(taskIndex);
            ui.printTaskMarkedAsDone(taskMarkedAsDone);
            storage.update(tasks.getTasks());
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to mark the task in the hard disk as done...");
        } catch (InvalidTaskIndexException e) {
            ui.printInvalidTaskIndexToMarkAsDoneErrorMessage(tasks.getRangeOfValidTaskIndex());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }
}
