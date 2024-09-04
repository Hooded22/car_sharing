package carsharing.states.companyMenuState;

public enum CompanyMenuStateMessages {
    COMPANY_CARS("'%s' cars:"),
    COMPANY_STATE_TITLE("'%s' company:"),
    CAR_LIST("Car list"),
    CREATE_CAR("Create a car"),
    BACK("Back"),
    ENTER_CAR_NAME("Enter the car name:"),
    CAR_ADDED("The car was added!"),
    NO_CARS("The car list is empty!");

    public final String message;

    CompanyMenuStateMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
