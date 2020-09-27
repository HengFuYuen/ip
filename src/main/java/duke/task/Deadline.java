package duke.task;

/**
 * Represents a task that needs to be done before a specific time/date.
 * A <code>Deadline</code> object is a task that has a description and timing at which it is due.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class Deadline extends Task {

    /* The time a deadline task is due by */
    private final String by;
    private final String allInformation;

    /**
     * Constructs a <code>Deadline</code> object with the given description and timing at which it is due
     * while initializing its done status to <code>false</code>.
     *
     * @param description A description of the deadline.
     * @param by The time the deadline is due by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        allInformation = description + "" + by;
    }

    /**
     * Constructs a <code>Deadline</code> object with the given description, timing at which it is due and
     * a boolean indicating if the deadline is done.
     *
     * @param description A description of the deadline.
     * @param by The time the deadline is due by.
     * @param isDone If <code>true</code>, then the deadline is marked as done.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
        allInformation = description + "" + by;
    }

    @Override
    public String getAllInformation() {
        return allInformation;
    }

    /**
     * Returns a string representation of the deadline information to be saved in a file.
     *
     * @return A string representation of the deadline information to be saved in a file.
     */
    public String messageToStoreInDukeFile() {
        return "D : " + super.messageToStoreInDukeFile() + " : " + by;
    }

    /**
     * Returns a string representation of all the information related to the deadline.
     *
     * @return A string representation of all the information related to the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
