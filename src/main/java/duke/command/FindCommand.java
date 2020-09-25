package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.EmptyTaskListException;
import duke.task.Task;

import java.util.ArrayList;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            ArrayList<Task> tasksWithKeyword = tasks.find(keyword);
            ui.printTasksWithKeyword(tasksWithKeyword, tasks.getTasks());
        } catch (EmptyTaskListException e) {
            ui.printEmptyTaskListErrorMessage();
        }
    }
}
