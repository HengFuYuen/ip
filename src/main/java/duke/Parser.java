package duke;

import duke.command.ByeCommand;
import duke.command.ClearCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.EventCommand;
import duke.command.ListCommand;
import duke.command.TodoCommand;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.DeleteNumberFormatException;
import duke.exception.TaskIndexToDeleteNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.InvalidCommandException;
import duke.exception.MarkAsDoneNumberFormatException;
import duke.exception.TaskIndexToMarkAsDoneNotFoundException;
import duke.exception.TodoDescriptionNotFoundException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

/**
 * Represents a user input decipherer.
 * A <code>Parser</code> object deciphers the user input meaning and validity and repackages it into a command
 * that the <code>Duke</code> application can understand.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class Parser {

    /**
     * Constructs a <code>Parser</code> object.
     */
    public Parser() {
    }

    /**
     * Returns a <code>Duke</code> understandable command that represents a corresponding user input after
     * deciphering it.
     *
     * @param fullCommand The user input.
     * @return A command, representing the given input, that the <code>Duke</code> application can understand.
     * @throws InvalidCommandException If the user input is invalid or cannot be deciphered.
     */
    public Command parse(String fullCommand) throws InvalidCommandException {
        fullCommand = fullCommand.trim();
        Command command;
        if (fullCommand.equals(ByeCommand.COMMAND_WORD)) {
            command = createByeCommand();
        } else if (fullCommand.equals(ListCommand.COMMAND_WORD)) {
            command = createListCommand();
        } else if (fullCommand.startsWith(DoneCommand.COMMAND_WORD)) {
            command = createDoneCommand(fullCommand);
        } else if (fullCommand.startsWith(DeleteCommand.COMMAND_WORD)) {
            command = createDeleteCommand(fullCommand);
        } else if (fullCommand.equals(ClearCommand.COMMAND_WORD)) {
            command = createClearCommand();
        } else if (fullCommand.startsWith(DeadlineCommand.COMMAND_WORD)) {
            command = createDeadlineCommand(fullCommand);
        } else if (fullCommand.startsWith(EventCommand.COMMAND_WORD)) {
            command = createEventCommand(fullCommand);
        } else if (fullCommand.startsWith(TodoCommand.COMMAND_WORD)) {
            command = createTodoCommand(fullCommand);
        } else {
            throw new InvalidCommandException();
        }
        return command;
    }

    /**
     * Returns a <code>Duke</code> understandable list command that represents a corresponding user input.
     *
     * @return A list command.
     */
    private ListCommand createListCommand() {
        return new ListCommand();
    }

    /**
     * Returns a <code>Duke</code> understandable done command that represents a corresponding user input.
     *
     * @param fullCommand The user input.
     * @return A done command.
     * @throws TaskIndexToMarkAsDoneNotFoundException If task index of the task to mark as done is not given.
     * @throws MarkAsDoneNumberFormatException If task index of the task to mark as done does not have
     *                                         an appropriate format and cannot be converted to an integer.
     */
    private DoneCommand createDoneCommand(String fullCommand) throws TaskIndexToMarkAsDoneNotFoundException,
            MarkAsDoneNumberFormatException {
        String taskIndexString = fullCommand.substring(DoneCommand.COMMAND_WORD_LENGTH).trim();
        if (!isTaskIndexGiven(taskIndexString)) {
            throw new TaskIndexToMarkAsDoneNotFoundException();
        }

        try {
            int taskIndex = Integer.parseInt(taskIndexString) - 1;
            return new DoneCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new MarkAsDoneNumberFormatException();
        }
    }

    /**
     * Returns a <code>Duke</code> understandable delete command that represents a corresponding user input.
     *
     * @param fullCommand The user input.
     * @return A delete command.
     * @throws TaskIndexToDeleteNotFoundException If task index of the task to delete is not given.
     * @throws DeleteNumberFormatException If task index of the task to delete does not have an appropriate
     *                                     format and cannot be converted to an integer.
     */
    private DeleteCommand createDeleteCommand(String fullCommand) throws TaskIndexToDeleteNotFoundException,
            DeleteNumberFormatException {
        String taskIndexString = fullCommand.substring(DeleteCommand.COMMAND_WORD_LENGTH).trim();
        if (!isTaskIndexGiven(taskIndexString)) {
            throw new TaskIndexToDeleteNotFoundException();
        }

        try {
            int taskIndex = Integer.parseInt(taskIndexString) - 1;
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new DeleteNumberFormatException();
        }
    }

    private boolean isTaskIndexGiven(String taskIndex) {
        return taskIndex.length() > 0;
    }

    /**
     * Returns a <code>Duke</code> understandable clear command that represents a corresponding user input.
     *
     * @return A clear command.
     */
    private ClearCommand createClearCommand() {
        return new ClearCommand();
    }

    /**
     * Returns a <code>Duke</code> understandable todo command that represents a corresponding user input.
     *
     * @param fullCommand The user input.
     * @return A todo command.
     * @throws TodoDescriptionNotFoundException If the todo task description is not given.
     */
    private TodoCommand createTodoCommand(String fullCommand) throws TodoDescriptionNotFoundException {
        String todoDescription = fullCommand.substring(TodoCommand.COMMAND_WORD_LENGTH).trim();
        if (!hasTaskDescription(todoDescription)) {
            throw new TodoDescriptionNotFoundException();
        }
        Todo newTodo = new Todo(todoDescription);
        return new TodoCommand(newTodo);
    }

    /**
     * Returns a <code>Duke</code> understandable event command that represents a corresponding user input.
     *
     * @param fullCommand The user input.
     * @return A event command.
     * @throws EventDescriptionNotFoundException If the event task description is not given.
     * @throws EventTimeNotFoundException If the event task timing is not given.
     */
    private EventCommand createEventCommand(String fullCommand) throws EventDescriptionNotFoundException
            , EventTimeNotFoundException {
        String eventInformation = fullCommand.substring(EventCommand.COMMAND_WORD_LENGTH).trim();
        if (!hasTaskDescription(eventInformation)) {
            throw new EventDescriptionNotFoundException();
        }

        String[] eventDescriptionAndTime = splitDescriptionAndTime(eventInformation,
                EventCommand.EVENT_TIME_INDICATOR);

        if (!hasOneTaskTime(eventDescriptionAndTime)) {
            throw new EventTimeNotFoundException();
        }

        String eventDescription = eventDescriptionAndTime[0].trim();
        String eventTime = eventDescriptionAndTime[1].trim();
        Event newEvent = new Event(eventDescription, eventTime);
        return new EventCommand(newEvent);
    }

    /**
     * Returns a <code>Duke</code> understandable deadline command that represents a corresponding user input.
     *
     * @param fullCommand The user input.
     * @return A deadline command.
     * @throws DeadlineDescriptionNotFoundException If the deadline task description is not given.
     * @throws DeadlineTimeNotFoundException If the time the deadline task is due is not given.
     */
    private DeadlineCommand createDeadlineCommand(String fullCommand) throws DeadlineDescriptionNotFoundException
            , DeadlineTimeNotFoundException {
        String deadlineInformation = fullCommand.substring(DeadlineCommand.COMMAND_WORD_LENGTH).trim();
        if (!hasTaskDescription(deadlineInformation)) {
            throw new DeadlineDescriptionNotFoundException();
        }

        String[] deadlineDescriptionAndTime = splitDescriptionAndTime(deadlineInformation
                , DeadlineCommand.DEADLINE_TIME_INDICATOR);

        if (!hasOneTaskTime(deadlineDescriptionAndTime)) {
            throw new DeadlineTimeNotFoundException();
        }

        String deadlineDescription = deadlineDescriptionAndTime[0].trim();
        String deadlineTime = deadlineDescriptionAndTime[1].trim();
        Deadline newDeadline = new Deadline(deadlineDescription, deadlineTime);
        return new DeadlineCommand(newDeadline);
    }

    private boolean hasTaskDescription(String taskInformation) {
        return taskInformation.length() > 0;
    }

    private boolean hasOneTaskTime(String[] taskDescriptionAndTime) {
        int taskTimeIndex = 2;
        return taskDescriptionAndTime.length == taskTimeIndex;
    }

    private String[] splitDescriptionAndTime(String taskInformation, String splitLocation) {
        return taskInformation.split(splitLocation);
    }

    /**
     * Returns a <code>Duke</code> understandable bye command that represents a corresponding user input.
     *
     * @return A bye command.
     */
    private Command createByeCommand() {
        return new ByeCommand();
    }
}
