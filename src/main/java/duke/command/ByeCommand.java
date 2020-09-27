package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a user instruction to exit the system.
 * A <code>ByeCommand</code> will signify to <code>Duke</code> to exit the application.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class ByeCommand extends Command {

    /* A string that signifies the input is a bye command */
    public static final String COMMAND_WORD = "bye";

    /**
     * Constructs a <code>ByeCommand</code> object.
     */
    public ByeCommand() {
    }

    /**
     * Prints the exiting message.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    @Override
    public void execute (TaskList tasks, Ui ui, Storage storage) {
            ui.printExitMessage();
    }


    /**
     * Returns <code>true</code> if user is exiting the application.
     *
     * @return <code>true</code> if user is exiting the application.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
