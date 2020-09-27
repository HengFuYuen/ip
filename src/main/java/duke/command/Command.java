package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a user instruction.
 * A <code>Command</code> cannot be instantiated as it is abstract.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public abstract class Command {

    /**
     * Constructs a <code>Command</code> object.
     */
    public Command() {
    }

    /**
     * Returns <code>true</code> if exiting the application else <code>false</code>.
     *
     * @return <code>true</code> if exiting the application else <code>false</code>.
     */
    public boolean isExit () {
        return false;
    }

    /**
     * Abstract method that executes the command.
     * The implementation is left to subclasses.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
}
