package carsharing.commands;

public enum CommonCommands implements Command {
    BACK("0");

    private final String message;

    CommonCommands(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
