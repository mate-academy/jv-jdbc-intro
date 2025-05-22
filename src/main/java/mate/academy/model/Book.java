package mate.academy.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Book {
    private BigInteger id;
    private String title;
    private BigDecimal price;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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
        return "Book{" + "id=" + id
                + ", title='" + title + '\''
                + ", price=" + price
                + '}';
    }
}
