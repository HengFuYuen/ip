package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Event;

import java.io.IOException;

/**
 * Represents a user instruction to add an event task.
 * A <code>EventCommand</code> will signify to <code>Duke</code> to add an event task to the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class EventCommand extends Command {

    /* A string that signifies the input is an event command */
    public static final String COMMAND_WORD = "event";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    /* A string that indicates the following string is the timing of the event */
    public static final String EVENT_TIME_INDICATOR = "/at";

    /* The event to be added to the task list */
    private final Event newEvent;

    /**
     * Constructs an <code>EventCommand</code> object given the event to be added.
     */
    public EventCommand(Event newEvent) {
        this.newEvent = newEvent;
    }

    /**
     * Adds an event to the task list and storage file and informs the user that the event has been added.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(newEvent);
        ui.printNewTask(newEvent, tasks.getNumberOfTasks());
        try {
            storage.saveTask(newEvent);
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to save the task to the hard disk...");
        }
    }
}
