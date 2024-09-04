package carsharing.customer;

import carsharing.car.CarDao;
import carsharing.car.CarWithCompany;
import carsharing.customer.excpetions.CustomerAlreadyRentCarException;
import carsharing.customer.excpetions.NoRentedCarException;

import java.util.List;

public class CustomerService {
    CustomerDao customerDao;
    CarDao carDao;

    public CustomerService(CustomerDao customerDao, CarDao carDao) {
        this.customerDao = customerDao;
        this.carDao = carDao;
    }

    public List<Customer> getCustomers() {
        return customerDao.getCustomers();
    }

    public CarWithCompany getCustomerCar(int customerId) {
        return customerDao.getCustomerCar(customerId);
    }

    public void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }

    public Customer rentCar(int carId, int customerId) throws CustomerAlreadyRentCarException {
        Customer customerToUpdate = customerDao.getCustomer(customerId);

        if (customerToUpdate.getRentedCarId() != null) {
            throw new CustomerAlreadyRentCarException();
        }

        customerToUpdate.setRentedCarId(carId);
        customerDao.updateCustomer(customerToUpdate);

        return customerToUpdate;
    }


    public Customer returnCar(int customerId) throws NoRentedCarException {
        Customer customerToUpdate = customerDao.getCustomer(customerId);

        if (customerToUpdate.getRentedCarId() == null) {
            throw new NoRentedCarException();
        }

        customerToUpdate.setRentedCarId(null);
        customerDao.updateCustomer(customerToUpdate);

        return customerToUpdate;
    }


}
