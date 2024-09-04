package carsharing.commands.exceptions;

import carsharing.commands.messages.CommandErrorMessageEnum;

public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException() {
        super(CommandErrorMessageEnum.UNKNOWN_COMMAND.getMessage());
    }
}
