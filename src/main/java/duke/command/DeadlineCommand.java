package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Deadline;

import java.io.IOException;

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    public static final String DEADLINE_TIME_INDICATOR = "/by";

    private final Deadline newDeadline;

    public DeadlineCommand(Deadline newDeadline) {
        this.newDeadline = newDeadline;
    }

    @Override
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
