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
import duke.exception.DeleteTaskIndexNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.InvalidCommandException;
import duke.exception.InvalidTaskTypeException;
import duke.exception.KeywordNotFoundException;
import duke.exception.MarkAsDoneNumberFormatException;
import duke.exception.MarkAsDoneTaskIndexNotFoundException;
import duke.exception.TodoDescriptionNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Duke {

    private TaskList tasks;
    private Storage storage;
    private final Ui ui;
    private final Parser parser;

    public Duke(String dirPath, String filePath) {
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
            ui.printIndexOutOfBoundsErrorMessage();
        }
    }

    public void run() {
        ui.printWelcomeMessage();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (KeywordNotFoundException e) {
                ui.printKeywordNotFoundErrorMessage();
            } catch (MarkAsDoneTaskIndexNotFoundException e) {
                ui.printMarkAsDoneTaskIndexNotFoundErrorMessage(tasks.getRangeOfValidTaskNumber());
            } catch (DeleteTaskIndexNotFoundException e) {
                ui.printDeleteTaskIndexNotFoundErrorMessage(tasks.getRangeOfValidTaskNumber());
            } catch (MarkAsDoneNumberFormatException e) {
                ui.printInvalidTaskToMarkAsDoneIndexErrorMessage(tasks.getRangeOfValidTaskNumber());
            } catch (DeleteNumberFormatException e) {
                ui.printInvalidTaskToDeleteIndexErrorMessage(tasks.getRangeOfValidTaskNumber());
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
                ui.printInvalidCommandErrorMessage(tasks.getRangeOfValidTaskNumber());
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data","data/duke.txt").run();
    }
}
