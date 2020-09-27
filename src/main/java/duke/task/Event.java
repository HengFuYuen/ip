package duke.task;

/**
 * Represents a task that starts at a specific time and ends at a specific time.
 * An <code>Event</code> object is a task that has a description and a start and end timing.
 *
 * @author Heng Fu Yuen
 * @version 2.0
 */
public class Event extends Task {

    /* The timing at which an event starts and ends */
    protected String at;

    /**
     * Constructs an <code>Event</code> object with the given description and time while initializing
     * its done status to <code>false</code>.
     *
     * @param description A description of the event.
     * @param at The start and end time of the event.
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    /**
     * Constructs an <code>Event</code> object with the given description,time and a boolean indicating
     * if the event is done.
     *
     * @param description A description of the event.
     * @param at The start and end time of the event.
     * @param isDone If <code>true</code>, then the event is marked as done.
     */
    public Event(String description, String at ,boolean isDone) {
        super(description, isDone);
        this.at = at;
    }

    /**
     * Returns a string representation of the event information to be saved in a file.
     *
     * @return A string representation of the event information to be saved in a file.
     */
    public String messageToStoreInDukeFile() {
        return "E : " + super.messageToStoreInDukeFile() + " : " + at;
    }

    /**
     * Returns a string representation of all the information related to the event.
     *
     * @return A string representation of all the information related to the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}
