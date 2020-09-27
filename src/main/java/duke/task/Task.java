package duke.task;

/**
 * Represents a task that the user wants to track.
 * A <code>Task</code> object that only has a description but cannot be instantiated as it is abstract.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public abstract class Task {

    /* Indicates whether the task is done */
    protected boolean isDone;
    protected String description;
    public static final String TASK_DONE_ICON = "Y";
    public static final String TASK_NOT_DONE_ICON = "N";


    /**
     * Constructs a <code>Task</code> object with the given description while initializing its done status
     * to <code>false</code>.
     *
     * @param description A description of the task.
     */
    protected Task(String description) {
        this.description = description;
        isDone = false;
    }

    /**
     * Constructs a <code>Task</code> object with the given description and a boolean indicating if the
     * task is done.
     *
     * @param description A description of the task.
     * @param isDone If <code>true</code>, then the task is marked as done.
     */
    protected Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns the string icon indicating whether the task is done.
     *
     * @return The string icon indicating whether the task is done.
     */
    public String getStatusIcon() {
        return (isDone ? TASK_DONE_ICON : TASK_NOT_DONE_ICON);
    }

    /**
     * Returns a string representation of the task information to be saved in a file.
     *
     * @return A string representation of the task information to be saved in a file.
     */
    public String messageToStoreInDukeFile() {
        return getStatusIcon() + " : " + description;
    }

    /**
     * Returns a string representation of all the information related to the task.
     *
     * @return A string representation of all the information related to the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
