package carsharing.customer;

import carsharing.car.CarWithCompany;

import java.util.List;

public interface CustomerDao {

    List<Customer> getCustomers();

    Customer getCustomer(int id);

    void updateCustomer(Customer customer);

    void addCustomer(Customer customer);

    CarWithCompany getCustomerCar(int customerId);
}
