package duke.task;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    public String messageToStoreInDukeFile() {
        return "T : " + super.messageToStoreInDukeFile();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
