package carsharing.dbConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {
    static final String DB_NAME = "carsharing";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/" + DB_NAME;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);

            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}