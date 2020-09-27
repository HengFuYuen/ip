package duke.task;

/**
 * Represents a task without any date/time attached to it.
 * A <code>Todo</code> object is a task that only has a description.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class Todo extends Task {

    /**
     * Constructs a <code>Todo</code> object with the given description while initializing its done status
     * to <code>false</code>.
     *
     * @param description A description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a <code>Todo</code> object with the given description and a boolean indicating if the
     * todo is done.
     *
     * @param description A description of the todo.
     * @param isDone If <code>true</code>, then the todo is marked as done.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns a string representation of the todo information to be saved in a file.
     *
     * @return A string representation of the todo information to be saved in a file.
     */
    public String messageToStoreInDukeFile() {
        return "T : " + super.messageToStoreInDukeFile();
    }

    /**
     * Returns a string representation of all the information related to the todo.
     *
     * @return A string representation of all the information related to the todo.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
