package carsharing.commands.messages;

public enum CommandErrorMessageEnum {
    UNKNOWN_COMMAND("Unknown command"),
    NO_INPUT("No input provided");

    public final String message;

    CommandErrorMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
