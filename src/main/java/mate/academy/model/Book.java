package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    @mate.academy.lib.ID
    private long id;

    private String title;

    private BigDecimal price;

    public Book(long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Book() {

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String toString() {
        return String.format("ID: %d, Title: %s, Price: %s", id, title, price);
    }
}
