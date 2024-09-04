package carsharing.states.customerState;

import carsharing.car.Car;
import carsharing.car.CarUtils;
import carsharing.car.CarWithCompany;
import carsharing.commands.utils.CommandUtils;
import carsharing.company.Company;
import carsharing.company.CompanyService;
import carsharing.company.CompanyUtils;
import carsharing.context.Context;
import carsharing.customer.Customer;
import carsharing.customer.CustomerService;
import carsharing.customer.excpetions.CustomerAlreadyRentCarException;
import carsharing.customer.excpetions.NoRentedCarException;
import carsharing.exceptions.BackCommandException;
import carsharing.states.State;

import java.util.List;

public class CustomerState implements State {
    Customer customer;
    CustomerService customerService;
    CompanyService companyService;

    public CustomerState(CustomerService customerService, CompanyService companyService, Customer customer) {
        this.customer = customer;
        this.customerService = customerService;
        this.companyService = companyService;
        displayAvailableOptions();
    }

    @Override
    public void handleCommand(String command, Context context) {
        CommandUtils<CustomerStateCommand> commandUtils = new CommandUtils<>();
        CustomerStateCommand parsedCommand = commandUtils.parseCommand(CustomerStateCommand.class, command);

        switch (parsedCommand) {
            case BACK:
                throw new BackCommandException();
            case RENT_CAR:
                rentCar();
                displayAvailableOptions();
                break;
            case RETURN_CAR:
                returnCar();
                displayAvailableOptions();
                break;
            case MY_RENTED_CAR:
                displayRentedCar();
                displayAvailableOptions();
                break;
        }
    }

    @Override
    public void displayAvailableOptions() {
        System.out.println("""
                \n
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back""");
    }

    private void rentCar() {
        if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!");
            return;
        }

        List<Company> companies = companyService.getAllCompany();
        try {
            Company choosenCompany = CompanyUtils.chooseCompany(companies);
            List<Car> cars = companyService.getNotRentedCompanyCars(choosenCompany.getId());

            if (cars.isEmpty()) {
                System.out.println("No available cars in the '" + choosenCompany.getName() + "' company!");
                return;
            }

            Car chosenCar = CarUtils.chooseCar(cars, choosenCompany.getName());

            customer = customerService.rentCar(chosenCar.getId(), customer.getId());
            System.out.println("You rented '" + chosenCar.getName() + "' car!");
        } catch (BackCommandException e) {
            displayAvailableOptions();
        } catch (CustomerAlreadyRentCarException e) {
            System.out.println("You've already rented a car!");
        }
    }

    private void returnCar() {
        try {
            if (customer.getRentedCarId() == null) {
                System.out.println("You didn't rent a car!");
                return;
            }

            customer = customerService.returnCar(customer.getId());
            System.out.println("You've returned a rented car!");
        } catch (NoRentedCarException e) {
            System.out.println("You didn't rent a car!");
        }
    }

    private void displayRentedCar() {
        try {
            CarWithCompany carWithCompany = customerService.getCustomerCar(customer.getId());

            System.out.println("Your rented car:\n" + carWithCompany.getCar().getName() + "\nCompany:\n" + carWithCompany.getCompany().getName());
        } catch (NoRentedCarException e) {
            System.out.println("You didn't rent a car!");
        }
    }


}
