package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class ByeCommand extends Command {

    public static final String COMMAND_WORD = "bye";

    public ByeCommand() {
    }

    @Override
    public void execute (TaskList tasks, Ui ui, Storage storage) {
            ui.printExitMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
