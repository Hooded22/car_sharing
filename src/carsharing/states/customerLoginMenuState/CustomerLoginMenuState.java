package carsharing.states.customerLoginMenuState;

import carsharing.context.Context;
import carsharing.customer.Customer;
import carsharing.customer.CustomerService;
import carsharing.exceptions.BackCommandException;
import carsharing.states.State;
import carsharing.states.customerState.CustomerState;
import carsharing.states.managerState.ManagerStateMessages;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CustomerLoginMenuState implements State {
    CustomerService customerService;
    Scanner scanner = new Scanner(System.in);
    List<Customer> customerList;


    public CustomerLoginMenuState(CustomerService customerService) {
        this.customerService = customerService;
        this.customerList = getCustomersList();
        displayAllCustomers(customerList);
    }

    @Override
    public void handleCommand(String customerId, Context context) throws BackCommandException {
        int customerIdInt = Integer.parseInt(customerId);
        Customer customer = chooseCustomer(customerIdInt);

        while (customer == null) {
            customer = chooseCustomer();
        }

        context.setState(CustomerState.class, customer);

    }

    @Override
    public void displayAvailableOptions() {

    }

    private List<Customer> getCustomersList() throws BackCommandException {
        List<Customer> customerList = customerService.getCustomers();

        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            throw new BackCommandException();
        }

        return customerList;
    }

    private Customer chooseCustomer() throws BackCommandException {
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            throw new BackCommandException();
        }

        displayAllCustomers(customerList);
        int companyIndex = scanner.nextInt();

        if (companyIndex == 0) {
            throw new BackCommandException();
        }

        try {
            return customerList.get(companyIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            return null;
        }
    }

    private Customer chooseCustomer(int companyIndex) throws BackCommandException {
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            throw new BackCommandException();
        }

        displayAllCustomers(customerList);
        if (companyIndex == 0) {
            throw new BackCommandException();
        }

        try {
            return customerList.get(companyIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            return null;
        }
    }

    private void displayAllCustomers(List<Customer> customerList) {
        System.out.println("Customer list:");
        IntStream.range(0, customerList.size()).forEach(i -> {
            Customer item = customerList.get(i);
            System.out.println(i + 1 + ". " + item.getName());
        });
        System.out.println("0. Back");
    }

}
