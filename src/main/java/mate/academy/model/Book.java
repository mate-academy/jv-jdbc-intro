package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private BigDecimal id;
    private String title;
    private int price;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id
                + ", title='" + title + '\''
                + ", price=" + price + '}';
    }
}
