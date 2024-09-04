package carsharing.car;

import carsharing.exceptions.BackCommandException;
import carsharing.states.managerState.ManagerStateMessages;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CarUtils {
    public static Car chooseCar(List<Car> carsList, String companyName) throws BackCommandException {
        Scanner scanner = new Scanner(System.in);

        if (carsList.isEmpty()) {
            System.out.println("No available cars in the '" + companyName + "' company");
            throw new BackCommandException();
        }

        displayAllCars(carsList);
        int companyIndex = scanner.nextInt();

        if (companyIndex == 0) {
            throw new BackCommandException();
        }

        try {
            return carsList.get(companyIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            throw new BackCommandException();
        }
    }

    public static void displayAllCars(List<Car> carsList) {
        System.out.println("Choose a car:");
        IntStream.range(0, carsList.size()).forEach(i -> {
            Car item = carsList.get(i);
            System.out.println(i + 1 + ". " + item.getName());
        });
        System.out.println("0. Back");
    }
}
