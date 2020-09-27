package duke;

/*
  Solution below adapted from
  https://github.com/nus-cs2113-AY2021S1/contacts/blob/master/src/main/java/Contacts1.java
  https://github.com/se-edu/addressbook-level2/tree/master/src/seedu/addressbook
 */

import duke.command.Command;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.DeleteNumberFormatException;
import duke.exception.TaskIndexToDeleteNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.InvalidCommandException;
import duke.exception.InvalidTaskTypeException;
import duke.exception.KeywordNotFoundException;
import duke.exception.MarkAsDoneNumberFormatException;
import duke.exception.TaskIndexToMarkAsDoneNotFoundException;
import duke.exception.TodoDescriptionNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Represents a task manager that tracks different types of tasks.
 * A <code>Duke</code> object corresponds to a task tracking application that has a
 * <code>Storage</code> which saves tasks into the hard disk and loads any previously saved task, a
 * <code>Ui</code> that is in charge of user interaction, a <code>Parser</code> that deciphers the user
 * command and a <code>TaskList</code> that contains all the tasks and carries out operations related to
 * adding, deleting, etc. of tasks to the task list.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class Duke {

    private TaskList tasks;
    private Storage storage;
    private final Ui ui;
    private final Parser parser;

    /**
     * The main method for <code>Duke</code>.
     * Creates a new <code>Duke</code> object and runs it.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        String dirPath = "data";
        String filePath = "data/duke.txt";
        new Duke(dirPath,filePath).run();
    }

    /**
     * Constructs a <code>Duke</code> object with a Storage that uses the directory and file path given,
     * a Ui, a Parser and a TaskList and initializes it.
     * Any previously saved tasks are also loaded.
     *
     * @param dirPath  Directory path that indicates the directory where the file storing tasks is saved.
     * @param filePath File path where the file storing the tasks is found.
     */
    private Duke(String dirPath, String filePath) {
        ui = new Ui();
        parser = new Parser();
        try {
            storage = new Storage(dirPath, filePath);
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.printFileNotFoundErrorMessage();
        } catch (IOException e) {
            ui.printIOExceptionErrorMessage("Duke is unable to create a directory or file to save tasks in"
                    + " the hard disk...");
        } catch (InvalidTaskTypeException e) {
            ui.printInvalidTaskTypeErrorMessage();
        } catch (IndexOutOfBoundsException e) {
            ui.printDukeFileCannotBeLoadedErrorMessage();
        }
    }

    /**
     * Runs the <code>Duke</code> application by continuously taking in the user command, deciphering it
     * and executing it until the <code>Duke</code> application is exited.
     */
    private void run() {
        ui.printWelcomeMessage();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (TaskIndexToMarkAsDoneNotFoundException e) {
                ui.printTaskIndexToMarkAsDoneNotFoundErrorMessage(tasks.getRangeOfValidTaskIndex());
            } catch (TaskIndexToDeleteNotFoundException e) {
                ui.printTaskIndexToDeleteNotFoundErrorMessage(tasks.getRangeOfValidTaskIndex());
            } catch (KeywordNotFoundException e) {
                ui.printKeywordNotFoundErrorMessage();
            } catch (MarkAsDoneNumberFormatException e) {
                ui.printInvalidTaskIndexToMarkAsDoneErrorMessage(tasks.getRangeOfValidTaskIndex());
            } catch (DeleteNumberFormatException e) {
                ui.printInvalidTaskIndexToDeleteErrorMessage(tasks.getRangeOfValidTaskIndex());
            } catch (TodoDescriptionNotFoundException e) {
                ui.printTodoDescriptionNotFoundErrorMessage();
            } catch (DeadlineDescriptionNotFoundException e) {
                ui.printDeadlineDescriptionNotFoundErrorMessage();
            } catch (DeadlineTimeNotFoundException e) {
                ui.printDeadlineTimeNotFoundErrorMessage();
            } catch (EventDescriptionNotFoundException e) {
                ui.printEventDescriptionNotFoundErrorMessage();
            } catch (EventTimeNotFoundException e) {
                ui.printEventTimeNotFoundErrorMessage();
            } catch (InvalidCommandException e) {
                ui.printInvalidCommandErrorMessage(tasks.getRangeOfValidTaskIndex());
            }
        }
    }
}
