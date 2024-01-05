package mate.academy.model.impl;

import java.math.BigDecimal;
import mate.academy.model.Book;

public class BookImpl implements Book {
    private Long id;
    private String title;
    private BigDecimal price;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book with "
                + "id=" + id
                + ", title='" + title
                + '\''
                + ", price=" + price;
    }
}
