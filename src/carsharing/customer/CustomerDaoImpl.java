package carsharing.customer;

import carsharing.car.Car;
import carsharing.car.CarWithCompany;
import carsharing.company.Company;
import carsharing.customer.excpetions.CustomerNotFound;
import carsharing.customer.excpetions.NoRentedCarException;
import carsharing.dbConnection.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer (name, rented_car_id) VALUES (?, ?)";
    private static final String SELECT_ALL_CUSTOMERS_SQL = "SELECT * FROM customer";
    private static final String SELECT_CUSTOMER_BY_ID_SQL = "SELECT * FROM customer WHERE id = ?";
    private static final String SELECT_CUSTOMER_CAR_SQL = "SELECT customer.ID AS customer_id, customer.NAME AS customer_name, \n" +
            "       car.ID AS car_id, car.NAME AS car_name, \n" +
            "       company.ID AS company_id, company.NAME AS company_name\n" +
            "FROM customer\n" +
            "INNER JOIN car ON customer.RENTED_CAR_ID = car.ID\n" +
            "INNER JOIN company ON car.COMPANY_ID = company.ID;";
    private static final String UPDATE_CUSTOMER_RENTED_CAR_SQL = "UPDATE customer SET rented_car_id = ? WHERE id = ?";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer SET name = ?, rented_car_id = ? WHERE id = ?";


    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Integer customerId = resultSet.getInt("id");
                String customerName = resultSet.getString("name");
                int carId = resultSet.getInt("rented_car_id");
                Integer rentedCarId = resultSet.wasNull() ? null : carId;

                Customer customer = new Customer(customerId, rentedCarId, customerName);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


    @Override
    public Customer getCustomer(int id) throws CustomerNotFound {
        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID_SQL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer customerId = resultSet.getInt("id");
                String customerName = resultSet.getString("name");
                int carId = resultSet.getInt("rented_car_id");
                Integer rentedCarId = resultSet.wasNull() ? null : carId;

                Customer customer = new Customer(customerId, rentedCarId, customerName);
                return customer;
            } else {
                throw new CustomerNotFound(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFound {
        getCustomer(customer.getId());

        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER_SQL);
            preparedStatement.setString(1, customer.getName());
            Integer carId = customer.getRentedCarId();
            if (carId != null) {
                preparedStatement.setInt(2, carId);
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }
            preparedStatement.setInt(3, customer.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL);

            preparedStatement.setString(1, customer.getName());
            Integer carId = customer.getRentedCarId();
            if (carId != null) {
                preparedStatement.setInt(2, carId);
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CarWithCompany getCustomerCar(int customerId) throws NoRentedCarException {
        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_CAR_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int carId = resultSet.getInt("car_id");
                String carName = resultSet.getString("car_name");
                int companyId = resultSet.getInt("company_id");
                String companyName = resultSet.getString("company_name");

                Car car = new Car(carId, carName, companyId);
                Company company = new Company(companyId, companyName);

                return new CarWithCompany(car, company);
            } else {
                throw new NoRentedCarException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
