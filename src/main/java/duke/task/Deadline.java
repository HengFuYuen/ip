package duke.task;

public class Deadline extends Task {

    private final String by;
    private final String allInformation;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        allInformation = description + "" + by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
        allInformation = description + "" + by;
    }

    @Override
    public String getAllInformation() {
        return allInformation;
    }

    public String messageToStoreInDukeFile() {
        return "D : " + super.messageToStoreInDukeFile() + " : " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
