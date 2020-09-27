package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.EmptyTaskListException;
import duke.task.Task;

import java.util.ArrayList;

/**
 * Represents a user instruction to search for tasks containing the given keyword.
 * A <code>FindCommand</code> will signify to <code>Duke</code> to find and print out tasks in
 * the task list containing the given keyword.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class FindCommand extends Command {

    /* A string that signifies the input is a find command */
    public static final String COMMAND_WORD = "find";
    public static final int COMMAND_WORD_LENGTH = COMMAND_WORD.length();

    /* The keyword to find in the task list */
    private final String keyword;

    /**
     * Constructs a <code>FindCommand</code> object given the keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Finds and prints all the tasks in the task list containing the given keyword.
     *
     * @param tasks The task list that stores all the tasks.
     * @param ui The ui in charge of user interaction.
     * @param storage The storage that saves tasks into a file and loads tasks from the file.
     */
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
