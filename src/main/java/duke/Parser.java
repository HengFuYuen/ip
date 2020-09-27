package duke;

import duke.command.ByeCommand;
import duke.command.ClearCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.EventCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.TodoCommand;
import duke.exception.DeadlineDescriptionNotFoundException;
import duke.exception.DeadlineTimeNotFoundException;
import duke.exception.DeleteNumberFormatException;
import duke.exception.DeleteTaskIndexNotFoundException;
import duke.exception.EventDescriptionNotFoundException;
import duke.exception.EventTimeNotFoundException;
import duke.exception.InvalidCommandException;
import duke.exception.KeywordNotFoundException;
import duke.exception.MarkAsDoneNumberFormatException;
import duke.exception.MarkAsDoneTaskIndexNotFoundException;
import duke.exception.TodoDescriptionNotFoundException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

public class Parser {

    public Parser() {
    }

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
        } else if (fullCommand.startsWith(FindCommand.COMMAND_WORD)) {
            command = createFindCommand(fullCommand);
        } else {
            throw new InvalidCommandException();
        }
        return command;
    }

    private ListCommand createListCommand() {
        return new ListCommand();
    }

    private DoneCommand createDoneCommand(String fullCommand) throws MarkAsDoneTaskIndexNotFoundException ,
            MarkAsDoneNumberFormatException {
        String taskIndexString = fullCommand.substring(DoneCommand.COMMAND_WORD_LENGTH).trim();
        if (!isTaskIndexGiven(taskIndexString)) {
            throw new MarkAsDoneTaskIndexNotFoundException();
        }

        try {
            int taskIndex = Integer.parseInt(taskIndexString) - 1;
            return new DoneCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new MarkAsDoneNumberFormatException();
        }
    }

    private DeleteCommand createDeleteCommand(String fullCommand) throws DeleteTaskIndexNotFoundException,
            DeleteNumberFormatException {
        String taskIndexString = fullCommand.substring(DeleteCommand.COMMAND_WORD_LENGTH).trim();
        if (!isTaskIndexGiven(taskIndexString)) {
            throw new DeleteTaskIndexNotFoundException();
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

    private ClearCommand createClearCommand() {
        return new ClearCommand();
    }

    private TodoCommand createTodoCommand(String fullCommand) throws TodoDescriptionNotFoundException {
        String todoDescription = fullCommand.substring(TodoCommand.COMMAND_WORD_LENGTH).trim();
        if (!hasTaskDescription(todoDescription)) {
            throw new TodoDescriptionNotFoundException();
        }
        Todo newTodo = new Todo(todoDescription);
        return new TodoCommand(newTodo);
    }

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

    private Command createByeCommand() {
        return new ByeCommand();
    }

    /**
     * Returns a <code>Duke</code> understandable find command that represents a corresponding user input.
     *
     * @param fullCommand The user input.
     * @return A find command.
     * @throws KeywordNotFoundException If the keyword to find is not given.
     */
    private FindCommand createFindCommand(String fullCommand) throws KeywordNotFoundException {
        String keyword = fullCommand.substring(FindCommand.COMMAND_WORD_LENGTH).trim();
        if (!isKeywordGiven(keyword)) {
            throw new KeywordNotFoundException();
        }
        return new FindCommand(keyword);
    }

    private boolean isKeywordGiven(String keyword) {
        return keyword.length() > 0;
    }
}
