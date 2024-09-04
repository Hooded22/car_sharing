package carsharing.context;

import carsharing.car.CarDao;
import carsharing.car.CarDaoImpl;
import carsharing.commands.exceptions.InvalidCommandException;
import carsharing.company.Company;
import carsharing.company.CompanyDao;
import carsharing.company.CompanyDaoImpl;
import carsharing.company.CompanyService;
import carsharing.customer.Customer;
import carsharing.customer.CustomerDao;
import carsharing.customer.CustomerDaoImpl;
import carsharing.customer.CustomerService;
import carsharing.exceptions.BackCommandException;
import carsharing.exceptions.ProgramExitException;
import carsharing.states.State;
import carsharing.states.companyMenuState.CompanyMenuState;
import carsharing.states.createCustomerState.CreateCustomerState;
import carsharing.states.customerLoginMenuState.CustomerLoginMenuState;
import carsharing.states.customerState.CustomerState;
import carsharing.states.exceptions.IllegalStateException;
import carsharing.states.logInState.MainMenuState;
import carsharing.states.managerState.ManagerState;

public class Context {
    private final CompanyDao companyDao = new CompanyDaoImpl();
    private final CarDao carDao = new CarDaoImpl();
    private final CustomerDao customerDao = new CustomerDaoImpl();
    private final CustomerService customerService = new CustomerService(customerDao, carDao);
    private final CompanyService companyService = new CompanyService(companyDao, carDao);
    private State currentState;

    public Context() {
    }

    public State getState() {
        return this.currentState;
    }

    public void setState(Class<? extends State> stateClass) {
        if (stateClass.equals(MainMenuState.class)) {
            this.currentState = new MainMenuState();
        } else if (stateClass.equals(ManagerState.class)) {
            this.currentState = new ManagerState(companyDao);
        } else if (stateClass.equals(CreateCustomerState.class)) {
            this.currentState = new CreateCustomerState(customerService);
        } else if (stateClass.equals(CustomerLoginMenuState.class)) {
            this.currentState = new CustomerLoginMenuState(customerService);
        } else {
            throw new IllegalStateException();
        }
    }

    public void setState(Class<? extends State> stateClass, Company company) {
        if (stateClass.equals(CompanyMenuState.class)) {
            this.currentState = new CompanyMenuState(carDao, company);
        } else {
            throw new IllegalStateException();
        }
    }

    public void setState(Class<? extends State> stateClass, Customer customer) {
        if (stateClass.equals(CustomerState.class)) {
            this.currentState = new CustomerState(customerService, companyService, customer);
        } else {
            throw new IllegalStateException();
        }
    }

    public void handleCommand(String command) throws InvalidCommandException {
        try {
            this.currentState.handleCommand(command, this);
        } catch (ProgramExitException ex) {
            handleProgramExitException(ex);
        } catch (BackCommandException ex) {
            handleBackCommandException(ex);
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        } catch (InvalidCommandException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleProgramExitException(ProgramExitException ex) {
        System.out.println("Bye!");
        System.exit(0);
    }

    private void handleBackCommandException(BackCommandException ex) {
        if (currentState instanceof ManagerState) {
            setState(MainMenuState.class);
        } else if (currentState instanceof CompanyMenuState) {
            setState(ManagerState.class);
        } else {
            setState(MainMenuState.class);
        }
    }
}
