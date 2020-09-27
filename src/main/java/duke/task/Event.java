package duke.task;

public class Event extends Task {

    private final String at;
    private final String allInformation;

    public Event(String description, String at) {
        super(description);
        this.at = at;
        allInformation = description + "" + at;
    }

    public Event(String description, String at ,boolean isDone) {
        super(description, isDone);
        this.at = at;
        allInformation = description + "" + at;
    }

    @Override
    public String getAllInformation() {
        return allInformation;
    }

    public String messageToStoreInDukeFile() {
        return "E : " + super.messageToStoreInDukeFile() + " : " + at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}
