package carsharing.states.createCustomerState;

import carsharing.context.Context;
import carsharing.customer.Customer;
import carsharing.customer.CustomerService;
import carsharing.exceptions.BackCommandException;
import carsharing.states.State;

import java.util.Scanner;

public class CreateCustomerState implements State {
    CustomerService customerService;
    Scanner scanner = new Scanner(System.in);

    public CreateCustomerState(CustomerService customerService) {
        this.customerService = customerService;
        displayAvailableOptions();
    }

    @Override
    public void handleCommand(String customerName, Context context) throws BackCommandException {
        String newCustomerName = customerName;
        boolean customerNameValid = isCustomerNameValid(newCustomerName);

        while (!customerNameValid) {
            displayAvailableOptions();
            newCustomerName = scanner.nextLine();
            customerNameValid = isCustomerNameValid(newCustomerName);
        }

        createCustomer(customerName);
        System.out.println("The customer was added!");

        throw new BackCommandException();
    }

    @Override
    public void displayAvailableOptions() {
        System.out.println("Enter the customer name:");
    }

    private boolean isCustomerNameValid(String customerName) {
        if (customerName == null || customerName.isEmpty()) {
            return false;
        }
        return true;
    }

    private void createCustomer(String customerName) {
        Customer customer = new Customer(customerName);
        customerService.addCustomer(customer);
    }
}
