package carsharing.states.managerState;

public enum ManagerStateMessages {
    ENTER_COMPANY_NAME("Enter the company name: "),
    COMPANY_CREATED("The company was created!"),
    NO_COMPANY_FOUND("The company list is empty"),
    COMPANY_LIST("Chose the company:");

    public final String message;

    ManagerStateMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
