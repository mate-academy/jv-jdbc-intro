package mate.academy.entity;

import java.math.BigDecimal;

public class Book {
    private int id;
    private String title;
    private BigDecimal price;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
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
