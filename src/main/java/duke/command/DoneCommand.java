package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.EmptyTaskListException;
import duke.exception.InvalidTaskIndexException;
import duke.task.Task;

import java.io.IOException;

public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    private final int taskIndex;

    public DoneCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task taskMarkedAsDone = tasks.markTaskAsDone(taskIndex);
            ui.printTaskMarkedAsDone(taskMarkedAsDone);
            storage.update(tasks.getTasks());
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to mark the task in the hard disk as done...");
        } catch (InvalidTaskIndexException e) {
            ui.printInvalidTaskToMarkAsDoneIndexErrorMessage(tasks.getRangeOfValidTaskNumber());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }
}
