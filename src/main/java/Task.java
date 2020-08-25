public class Task {

    protected boolean isDone;
    protected String description;

    public Task(String command) {
        this.description = command;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return (this.isDone ? "\u2713" : "\u2718");
    }
}
