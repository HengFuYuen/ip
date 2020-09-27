package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a user instruction to clear the task list.
 * A <code>ClearCommand</code> will signify to <code>Duke</code> to remove all tasks from the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
import java.io.IOException;

public class ClearCommand extends Command {

    /* A string that signifies the input is a clear command */
    public static final String COMMAND_WORD = "clear";

    /**
     * Constructs a <code>ClearCommand</code> object.
     */
    public ClearCommand() {
    }

    /**
     * Clears the task list and informs the user that the task list is cleared.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    public void execute (TaskList tasks, Ui ui, Storage storage) {
        tasks.clear();
        ui.printClearTaskListMessage();
        try {
            storage.clear();
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to clear data of tasks saved in the " +
                    "hard disk...");
        }
    }
}
