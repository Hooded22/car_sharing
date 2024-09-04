package carsharing.customer;

public class Customer {
    private int id;
    private String name;
    private Integer rentedCarId;

    public Customer(Integer id, Integer rentedCarId, String name) {
        this.name = name;
        this.id = id;
        this.rentedCarId = rentedCarId;
    }

    public Customer(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(Integer rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
