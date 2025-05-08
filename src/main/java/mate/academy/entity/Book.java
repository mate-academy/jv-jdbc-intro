package mate.academy.entity;

import java.math.BigDecimal;

public class Book {
    private long id;
    private String title;
    private BigDecimal price;

    public Book(long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Book(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format("Book: {id: %d title - %s, price - %s}", id, title, price);
    }
}
