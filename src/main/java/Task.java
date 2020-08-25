public class Task {

    protected boolean isDone;
    protected String command;

    public Task (String command) {
        this.command = command;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getCommand() {
        return this.command;
    }

    public String getStatusIcon() {
        return (this.isDone ? "\u2713" : "\u2718");
    }
}
