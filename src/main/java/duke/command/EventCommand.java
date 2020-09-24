package duke.command;


import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Event;

import java.io.IOException;

public class EventCommand extends Command {

    public static final String COMMAND_WORD = "event";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    public static final String EVENT_TIME_INDICATOR = "/at";

    private final Event newEvent;

    public EventCommand(Event newEvent) {
        this.newEvent = newEvent;
    }

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
