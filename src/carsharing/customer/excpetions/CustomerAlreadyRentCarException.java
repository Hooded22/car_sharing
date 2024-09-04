package carsharing.customer.excpetions;

public class CustomerAlreadyRentCarException extends RuntimeException {
    public CustomerAlreadyRentCarException() {
        super("Customer already rent");
    }
}
