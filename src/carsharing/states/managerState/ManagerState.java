package carsharing.states.managerState;

import carsharing.commands.exceptions.InvalidCommandException;
import carsharing.commands.utils.CommandUtils;
import carsharing.company.Company;
import carsharing.company.CompanyDao;
import carsharing.context.Context;
import carsharing.exceptions.BackCommandException;
import carsharing.states.State;
import carsharing.states.companyMenuState.CompanyMenuState;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ManagerState implements State {
    CompanyDao companyDao;
    Scanner scanner = new Scanner(System.in);


    public ManagerState(CompanyDao companyDao) {
        displayAvailableOptions();
        this.companyDao = companyDao;
    }

    @Override
    public void handleCommand(String command, Context context) throws InvalidCommandException, BackCommandException {
        CommandUtils<ManagerStateCommands> commandUtils = new CommandUtils<ManagerStateCommands>();
        ManagerStateCommands parsedCommand = commandUtils.parseCommand(ManagerStateCommands.class, command);

        switch (parsedCommand) {
            case COMPANY_LIST:
                Company chosenCompany = choseCompany();

                if (chosenCompany != null) {
                    context.setState(CompanyMenuState.class, chosenCompany);
                } else {
                    displayAvailableOptions();
                }

                break;
            case CREATE_COMPANY:
                addCompany();
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
                1. Company list
                2. Create a company
                0. Back""");
    }

    private void displayAllCompanies(List<Company> companiesList) {
        System.out.println(ManagerStateMessages.COMPANY_LIST.getMessage());
        IntStream.range(0, companiesList.size()).forEach(i -> {
            Company item = companiesList.get(i);
            System.out.println(i + 1 + ". " + item.getName());
        });
        System.out.println("0. Back");
    }

    private Company choseCompany() {
        List<Company> companiesList = companyDao.getCompanies();

        if (companiesList.isEmpty()) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            return null;
        }

        displayAllCompanies(companiesList);
        int companyIndex = scanner.nextInt();

        if (companyIndex == 0) {
            return null;
        }

        try {
            return companiesList.get(companyIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ManagerStateMessages.NO_COMPANY_FOUND.getMessage());
            return null;
        }


    }

    private void addCompany() {
        System.out.println(ManagerStateMessages.ENTER_COMPANY_NAME.getMessage());
        String companyName = scanner.nextLine();


        while (companyName.isEmpty()) {
            System.out.println(ManagerStateMessages.ENTER_COMPANY_NAME.getMessage());
            companyName = scanner.nextLine();
        }

        Company company = new Company(companyName);
        companyDao.createCompany(company);
        System.out.println(ManagerStateMessages.COMPANY_CREATED.getMessage());
    }
}
