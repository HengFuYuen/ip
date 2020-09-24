package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.task.Task;

import java.io.IOException;

public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task taskDeleted = tasks.delete(taskIndex);
            ui.printDeletedTask(taskDeleted, tasks.getNumberOfTasks());
            storage.update(tasks.getTasks());
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to delete the task in the hard disk...");
        } catch (InvalidTaskIndexException e) {
            ui.printInvalidTaskToDeleteIndexErrorMessage(tasks.getRangeOfValidTaskNumber());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }
}
