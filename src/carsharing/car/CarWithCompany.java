package carsharing.car;

import carsharing.company.Company;

public class CarWithCompany {
    Car car;
    Company company;

    public CarWithCompany(Car car, Company company) {
        this.car = car;
        this.company = company;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
