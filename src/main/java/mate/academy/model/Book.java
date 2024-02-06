package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private final String title;
    private final BigDecimal price;

    public Book(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public Book(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", title=" + title
                + ", price=" + price
                + "}";
    }
}
