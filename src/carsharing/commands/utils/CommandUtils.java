package carsharing.commands.utils;

import carsharing.commands.Command;
import carsharing.commands.exceptions.InvalidCommandException;
import carsharing.commands.messages.CommandErrorMessageEnum;

public class CommandUtils<C extends Enum<C> & Command> {
    public C parseCommand(Class<C> commandEnum, String command) throws InvalidCommandException {
        try {
            validateCommand(command);
            return Command.fromString(commandEnum, command);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException(CommandErrorMessageEnum.UNKNOWN_COMMAND.getMessage());
        }
    }

    public void validateCommand(String command) throws InvalidCommandException {
        if (command.trim().isEmpty()) {
            throw new InvalidCommandException(CommandErrorMessageEnum.NO_INPUT.getMessage());
        }
    }
}
