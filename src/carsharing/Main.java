package carsharing;

import carsharing.context.Context;
import carsharing.dbConnection.DbConnectionManager;
import carsharing.dbConnection.DbSetup;
import carsharing.states.logInState.MainMenuState;

import java.util.Scanner;

public class Main {
    private static final DbConnectionManager dbConnectionManager = new DbConnectionManager();


    public static void main(String[] args) {
        DbSetup.setupDatabase(dbConnectionManager);
        Context context = new Context();
        Scanner scanner = new Scanner(System.in);
        context.setState(MainMenuState.class);


        while (true) {
            String command = scanner.nextLine();
            context.handleCommand(command);
        }

    }
}