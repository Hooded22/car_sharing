package carsharing.states;

import carsharing.context.Context;
import carsharing.states.logInState.MainMenuState;

public interface State {
    void handleCommand(String command, Context context);

    default void backToMainState(Context context) {
        context.setState(MainMenuState.class);
    }

    void displayAvailableOptions();
}
