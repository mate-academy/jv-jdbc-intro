package mate.academy.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    public Book() {

    }

    public Book(Long id, String title, BigDecimal price) {
        this.id = id;
        this.price = price;
        this.title = title;
    }

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
    public int hashCode() {
        return Objects.hash(id, title, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(price, book.price);
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", price='" + price + '\''
                + '}';
    }
}
