package duke;

public enum CommandType {
    DELETE ("delete", "delete".length()),
    BYE ("bye", "bye".length()),
    LIST ("list", "list".length()),
    DONE ("done", "done".length()),
    CLEAR ("clear", "clear".length()),
    TODO ("todo", "todo".length()),
    DEADLINE ("deadline", "deadline".length()),
    EVENT ("event", "event".length());

    private final String commandWord;
    private final int commandWordLength;

    CommandType(String commandWord, int commandWordLength) {
        this.commandWord = commandWord;
        this.commandWordLength = commandWordLength;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public int getCommandWordLength() {
        return commandWordLength;
    }
}

