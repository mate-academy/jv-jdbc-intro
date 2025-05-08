package mate.academy.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {

    private Long id;
    private String title;
    private BigDecimal price;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) && Objects
            .equals(getTitle(), book.getTitle()) && Objects
            .equals(getPrice(), book.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPrice());
    }

    @Override
    public String toString() {
        return "Book{"
            + "id=" + id
            + ", title='" + title
            + '\''
            + ", price=" + price
            + '}';
    }
}
