package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.task.Task;

import java.io.IOException;

/**
 * Represents a user instruction to delete a specified task.
 * A <code>DeleteCommand</code> will signify to <code>Duke</code> to delete the specified task from the
 * task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class DeleteCommand extends Command {

    /* A string that signifies the input is a delete command */
    public static final String COMMAND_WORD = "delete";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    /* The task index of the task to be deleted */
    private final int taskIndex;

    /**
     * Constructs a <code>DeleteCommand</code> object given the task index of the task to be deleted.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Removes the task specified from the task list and the storage file and informs the user that the task
     * has been deleted.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task taskDeleted = tasks.delete(taskIndex);
            ui.printDeletedTask(taskDeleted, tasks.getNumberOfTasks());
            storage.update(tasks.getTasks());
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to delete the task in the hard disk...");
        } catch (InvalidTaskIndexException e) {
            ui.printInvalidTaskIndexToDeleteErrorMessage(tasks.getRangeOfValidTaskIndex());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }
}
