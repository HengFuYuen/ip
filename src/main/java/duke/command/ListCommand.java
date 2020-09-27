package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a user instruction to list out all the tasks.
 * A <code>ListCommand</code> will signify to <code>Duke</code> to print out all the tasks in the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class ListCommand extends Command {

    /* A string that signifies the input is a list command */
    public static final String COMMAND_WORD = "list";

    /**
     * Constructs a <code>ListCommand</code> object.
     */
    public ListCommand() {
    }

    /**
     * Prints all the tasks in task list.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printTaskList(tasks.getTasks());
    }
}
