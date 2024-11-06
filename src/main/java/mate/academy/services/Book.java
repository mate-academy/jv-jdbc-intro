package mate.academy.services;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String model;
    private BigDecimal price;

    public Book(Long id, String model, int price) {
        this.id = id;
        this.model = model;
        this.price = BigDecimal.valueOf(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = BigDecimal.valueOf(price);
    }
}
