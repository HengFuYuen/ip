package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Deadline;

import java.io.IOException;

/**
 * Represents a user instruction to add a deadline task.
 * A <code>DeadlineCommand</code> will signify to <code>Duke</code> to add a deadline task to the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class DeadlineCommand extends Command {

    /* A string that signifies the input is a deadline command */
    public static final String COMMAND_WORD = "deadline";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    /* A string that indicates the following string is the timing at which the deadline is due */
    public static final String DEADLINE_TIME_INDICATOR = "/by";

    /* The deadline to be added to the task list */
    private final Deadline newDeadline;

    /**
     * Constructs a <code>DeadlineCommand</code> object given the deadline to be added.
     */
    public DeadlineCommand(Deadline newDeadline) {
        this.newDeadline = newDeadline;
    }

    /**
     * Adds a deadline to the task list and storage file and informs the user that the deadline has been
     * added.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(newDeadline);
        ui.printNewTask(newDeadline, tasks.getNumberOfTasks());
        try {
            storage.saveTask(newDeadline);
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to save the task to the hard drive...");
        }
    }
}
