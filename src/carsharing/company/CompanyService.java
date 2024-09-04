package carsharing.company;

import carsharing.car.Car;
import carsharing.car.CarDao;

import java.util.List;

public class CompanyService {
    CompanyDao companyDao;
    CarDao carDao;

    public CompanyService(CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    public List<Company> getAllCompany() {
        return companyDao.getCompanies();
    }

    public List<Car> getAllCompanyCars(int companyId) {
        return carDao.getAllCarsForCompanyId(companyId);
    }

    public List<Car> getNotRentedCompanyCars(int companyId) {
        return carDao.getAllNotRentedCarsForCompanyId(companyId);
    }


}
