package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public abstract class Command {

    public Command() {
    }

    public boolean isExit () {
        return false;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
}
