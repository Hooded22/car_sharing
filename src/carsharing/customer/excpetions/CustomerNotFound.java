package carsharing.customer.excpetions;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(int id) {
        super("Customer with id " + id + " not found");
    }
}
