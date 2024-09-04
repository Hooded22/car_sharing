package carsharing.car;

import java.util.List;

public interface CarDao {
    List<Car> getAllCarsForCompanyId(int companyId);

    List<Car> getAllNotRentedCarsForCompanyId(int companyId);

    void createCar(Car car);
}
