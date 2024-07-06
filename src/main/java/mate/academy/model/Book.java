package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    public Book() {
    }

    public Book(String title, BigDecimal price) {
        this.price = price;
        this.title = title;
    }

    public Book(Long id, BigDecimal price, String title) {
        this.id = id;
        this.price = price;
        this.title = title;
    }

    public Book(long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id="
                + id
                + ", title='"
                + title
                + '\''
                + ", price="
                + price
                + '}';
    }
}
