package mate.academy.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private Long id;
    private String brand;
    private String model;
    private String number;
    private Integer mileage;
    private Boolean inAccident;
    private BigDecimal price;

    public Car(Long id, String brand, String model, String number, Integer mileage, Boolean inAccident, BigDecimal price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.number = number;
        this.mileage = mileage;
        this.inAccident = inAccident;
        this.price = price;
    }


    public Car(String brand, String model, String number, Integer mileage, Boolean inAccident, BigDecimal price) {
        this(null, brand, model, number, mileage, inAccident, price);
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getNumber() {
        return number;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Boolean isInAccident() {
        return inAccident;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setModel(String model) {
        this.model = model;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(number, car.number) &&
                Objects.equals(mileage, car.mileage) &&
                Objects.equals(inAccident, car.inAccident) &&
                Objects.equals(price, car.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, number, mileage, inAccident, price);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", number='" + number + '\'' +
                ", mileage=" + mileage +
                ", inAccident=" + inAccident +
                ", price=" + price +
                '}';
    }

}
