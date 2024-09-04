package carsharing.car;

import carsharing.dbConnection.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private static final String INSERT_CAR_SQL = "INSERT INTO car (name, company_id) VALUES (?, ?)";
    private static final String SELECT_ALL_CARS_BY_COMPANY_ID_SQL = "SELECT * FROM car WHERE company_id = ?";
    private static final String SELECT_ALL_NOT_RENTED_CARS_SQL = "SELECT * FROM car WHERE company_id = ? AND id NOT IN (SELECT rented_car_id FROM customer WHERE rented_car_id IS NOT NULL);";

    public CarDaoImpl() {
    }

    @Override
    public List<Car> getAllNotRentedCarsForCompanyId(int companyId) {
        List<Car> cars = new ArrayList<>();
        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NOT_RENTED_CARS_SQL);
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setName(resultSet.getString("name"));
                car.setCompanyId(resultSet.getInt("company_id"));

                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public List<Car> getAllCarsForCompanyId(int companyId) {
        List<Car> cars = new ArrayList<>();
        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARS_BY_COMPANY_ID_SQL);
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setName(resultSet.getString("name"));
                car.setCompanyId(resultSet.getInt("company_id"));

                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public void createCar(Car car) {
        try (Connection connection = DbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAR_SQL)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompanyId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
