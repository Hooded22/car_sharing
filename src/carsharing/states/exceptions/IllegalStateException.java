package carsharing.states.exceptions;

public class IllegalStateException extends RuntimeException {
    public IllegalStateException() {
        super("Wrong state provided");
    }
}
