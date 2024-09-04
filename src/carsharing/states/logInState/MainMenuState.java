package carsharing.states.logInState;

import carsharing.commands.exceptions.InvalidCommandException;
import carsharing.commands.utils.CommandUtils;
import carsharing.context.Context;
import carsharing.exceptions.ProgramExitException;
import carsharing.states.State;
import carsharing.states.createCustomerState.CreateCustomerState;
import carsharing.states.customerLoginMenuState.CustomerLoginMenuState;
import carsharing.states.managerState.ManagerState;

public class MainMenuState implements State {

    public MainMenuState() {
        displayAvailableOptions();
    }

    @Override
    public void handleCommand(String command, Context context) throws InvalidCommandException {
        CommandUtils<LogInStateCommands> commandUtils = new CommandUtils<LogInStateCommands>();
        LogInStateCommands parsedCommand = commandUtils.parseCommand(LogInStateCommands.class, command);

        switch (parsedCommand) {
            case LOGIN_AS_MANAGER:
                context.setState(ManagerState.class);
                break;
            case LOGIN_AS_CUSTOMER:
                context.setState(CustomerLoginMenuState.class);
                break;
            case CREATE_CUSTOMER:
                context.setState(CreateCustomerState.class);
                break;
            case EXIT:
                throw new ProgramExitException();
            default:
                throw new InvalidCommandException("Invalid command");
        }
    }

    @Override
    public void displayAvailableOptions() {
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit""");
    }
}
