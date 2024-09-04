package carsharing.company;

import carsharing.dbConnection.DbConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    private static final String INSERT_COMPANY_SQL = "INSERT INTO company (name) VALUES (?)";
    private static final String SELECT_ALL_COMPANIES_SQL = "SELECT id, name FROM company ORDER BY id ASC";

    public CompanyDaoImpl() {
    }

    @Override
    public void createCompany(Company company) {
        try (Connection connection = DbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANY_SQL)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DbConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_COMPANIES_SQL)) {
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }
}
