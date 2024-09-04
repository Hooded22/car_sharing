package carsharing.states.managerState;

import carsharing.commands.Command;

public enum ManagerStateCommands implements Command {
    BACK("0"),
    COMPANY_LIST("1"),
    CREATE_COMPANY("2");

    private final String message;

    ManagerStateCommands(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
