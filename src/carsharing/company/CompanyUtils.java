package carsharing.company;

import carsharing.exceptions.BackCommandException;
import carsharing.states.managerState.ManagerStateMessages;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CompanyUtils {

    public static Company chooseCompany(List<Company> companiesList) throws BackCommandException {
        Scanner scanner = new Scanner(System.in);

        if (companiesList.isEmpty()) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            throw new BackCommandException();
        }

        displayAllCompanies(companiesList);
        int companyIndex = scanner.nextInt();

        if (companyIndex == 0) {
            throw new BackCommandException();
        }

        try {
            return companiesList.get(companyIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            throw new BackCommandException();
        }
    }

    public static void displayAllCompanies(List<Company> companiesList) {
        System.out.println(ManagerStateMessages.COMPANY_LIST.getMessage());
        IntStream.range(0, companiesList.size()).forEach(i -> {
            Company item = companiesList.get(i);
            System.out.println(i + 1 + ". " + item.getName());
        });
        System.out.println("0. Back");
    }
}
