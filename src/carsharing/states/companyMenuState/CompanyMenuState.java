package carsharing.states.companyMenuState;

import carsharing.car.Car;
import carsharing.car.CarDao;
import carsharing.commands.exceptions.InvalidCommandException;
import carsharing.commands.utils.CommandUtils;
import carsharing.company.Company;
import carsharing.context.Context;
import carsharing.exceptions.BackCommandException;
import carsharing.states.State;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CompanyMenuState implements State {
    CarDao carDao;
    Scanner scanner = new Scanner(System.in);
    Company company;

    public CompanyMenuState(CarDao carDao, Company company) {
        displayAvailableOptions();
        this.carDao = carDao;
        this.company = company;
    }

    @Override
    public void handleCommand(String command, Context context) throws InvalidCommandException, BackCommandException {
        CommandUtils<CompanyMenuStateCommands> commandUtils = new CommandUtils<>();
        CompanyMenuStateCommands parsedCommand = commandUtils.parseCommand(CompanyMenuStateCommands.class, command);

        switch (parsedCommand) {
            case CAR_LIST:
                displayAllCars();
                displayAvailableOptions();
                break;
            case ADD_CAR:
                addCar();
                displayAvailableOptions();
                break;
            case BACK:
                throw new BackCommandException();
            default:
                throw new InvalidCommandException();
        }
    }

    @Override
    public void displayAvailableOptions() {
        System.out.println("""
                \n
                1. Car list
                2. Create a car
                0. Back""");
    }

    private void displayAllCars() {
        List<Car> cars = carDao.getAllCarsForCompanyId(company.getId());

        if (cars.isEmpty()) {
            System.out.println(CompanyMenuStateMessages.NO_CARS.getMessage());
            return;
        }

        System.out.println(CompanyMenuStateMessages.CAR_LIST.getMessage());
        IntStream.range(0, cars.size()).forEach(i -> {
            Car item = cars.get(i);
            System.out.println(i + 1 + ". " + item.getName());
        });
    }

    private void addCar() {
        System.out.println(CompanyMenuStateMessages.ENTER_CAR_NAME.getMessage());
        String carName = scanner.nextLine();

        if (carName.isEmpty()) {
            System.out.println(CompanyMenuStateMessages.ENTER_CAR_NAME.getMessage());
        }

        Car car = new Car(carName, company.getId());
        carDao.createCar(car);
        System.out.println(CompanyMenuStateMessages.CAR_ADDED.getMessage());
    }
}
