package carsharing.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSetup {
    private static final String CREATE_COMPANY_TABLE_SQL = "CREATE TABLE IF NOT EXISTS company (" +
            "ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL" +
            ");";

    private static final String CREATE_CAR_TABLE_SQL = "CREATE TABLE IF NOT EXISTS car" +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL," +
            "COMPANY_ID INT NOT NULL," +
            "FOREIGN KEY (COMPANY_ID) REFERENCES company(id) ON DELETE CASCADE)";

    private static final String CREATE_CUSTOMER_TABLE_SQL = "CREATE TABLE IF NOT EXISTS customer" +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL," +
            "RENTED_CAR_ID INT DEFAULT NULL," +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES car(id) ON DELETE SET NULL)";

    public static void setupDatabase(DbConnectionManager dbConnectionManager) {
        try (Connection connection = dbConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Connected to database");
            System.out.println("Setting up database...");
            statement.execute(CREATE_COMPANY_TABLE_SQL);
            System.out.println("Company table created.");
            statement.execute(CREATE_CAR_TABLE_SQL);
            System.out.println("Car table created.");
            statement.execute(CREATE_CUSTOMER_TABLE_SQL);
            System.out.println("Customer table created.");
            System.out.println("Database set up done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}