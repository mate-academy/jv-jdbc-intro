package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    public Book() {
    }

    public Book(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;
        return this.id != null && this.id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", price=" + price + '}';
    }
}
