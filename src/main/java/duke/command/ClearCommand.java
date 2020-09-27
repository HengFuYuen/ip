package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

import java.io.IOException;

public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public ClearCommand() {
    }

    @Override
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
