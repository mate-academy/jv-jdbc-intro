package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private String title;
    private BigDecimal price;
    private Long id;

    public Book(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public Book() {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{"
                + "title='" + title + '\''
                + ", price=" + price
                + ", id=" + id + '}';
    }
}
