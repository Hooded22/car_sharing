package carsharing.states.logInState;

import carsharing.commands.Command;

public enum LogInStateCommands implements Command {
    EXIT("0"),
    LOGIN_AS_MANAGER("1"),
    LOGIN_AS_CUSTOMER("2"),
    CREATE_CUSTOMER("3");

    private final String message;

    LogInStateCommands(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
