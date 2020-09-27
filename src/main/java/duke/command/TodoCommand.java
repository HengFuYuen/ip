package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Todo;

import java.io.IOException;

/**
 * Represents a user instruction to add a todo task.
 * A <code>TodoCommand</code> will signify to <code>Duke</code> to add a todo task to the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class TodoCommand extends Command {

    /* A string that signifies the input is a todo command */
    public static final String COMMAND_WORD = "todo";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    /* The todo to be added to the task list */
    private final Todo newTodo;

    /**
     * Constructs a <code>TodoCommand</code> object given the todo to be added.
     */
    public TodoCommand(Todo newTodo) {
        this.newTodo = newTodo;
    }


    /**
     * Adds a todo to the task list and storage file and informs the user that the todo has been added.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    @Override
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
