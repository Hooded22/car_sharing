package carsharing.company;

import java.util.List;

public interface CompanyDao {
    void createCompany(Company company);

    List<Company> getCompanies();
}
