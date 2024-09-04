package carsharing.states.companyMenuState;

import carsharing.commands.Command;

public enum CompanyMenuStateCommands implements Command {
    BACK("0"),
    CAR_LIST("1"),
    ADD_CAR("2");

    private final String message;

    CompanyMenuStateCommands(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
