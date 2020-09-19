package duke.task;

public abstract class Task {

    protected boolean isDone;
    protected String description;

    public Task(String command) {
        description = command;
        isDone = false;
    }

    public Task(String command, boolean isDone) {
        description = command;
        this.isDone = isDone;
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getStatusIcon() {
        return (isDone ? "Y" : "N");
    }

    public String getDoneStatusForDukeFile() {
        return (isDone ? "1 : " : "0 : ");
    }

    public String messageToStoreInDukeFile() {
        return getDoneStatusForDukeFile() + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
