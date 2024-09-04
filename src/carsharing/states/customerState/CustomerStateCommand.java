package carsharing.states.customerState;

import carsharing.commands.Command;

public enum CustomerStateCommand implements Command {
    BACK("0"),
    RENT_CAR("1"),
    RETURN_CAR("2"),
    MY_RENTED_CAR("3");

    private final String message;

    CustomerStateCommand(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
