package duke.task;

public abstract class Task {

    protected boolean isDone;
    protected String description;
    public static final String TASK_DONE_ICON = "Y";
    public static final String TASK_NOT_DONE_ICON = "N";


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
        return (isDone ? TASK_DONE_ICON : TASK_NOT_DONE_ICON);
    }

    public String messageToStoreInDukeFile() {
        return getStatusIcon() + " : " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
