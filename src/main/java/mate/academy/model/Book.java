package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private BigDecimal price;
    private String title;

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
                + "id=" + id
                + ", price=" + price
                + ", title='" + title + '\''
                + '}';
    }
}
