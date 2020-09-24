package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Todo;

import java.io.IOException;

public class TodoCommand extends Command {

    public static final String COMMAND_WORD = "todo";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    private final Todo newTodo;

    public TodoCommand(Todo newTodo) {
        this.newTodo = newTodo;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(newTodo);
        ui.printNewTask(newTodo, tasks.getNumberOfTasks());
        try {
            storage.saveTask(newTodo);
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to save the task to the hard disk...");
        }
    }
}
