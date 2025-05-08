package mate.academy.model;

import java.math.BigDecimal;
import mate.academy.lib.Dao;

public class Book {
    @Dao
    private String title;
    @Dao
    private BigDecimal price;
    @Dao
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "Book{"
               + "title='" + title + '\''
               + ", price=" + price
               + ", id=" + id
               + '}';
    }
}

