package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private int year;
    private BigDecimal price;
    private int quantity;
    private String name;
    private String author;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", year=" + year
                + ", price=" + price
                + ", quantity=" + quantity
                + ", name='" + name + '\''
                + ", author='" + author + '\''
                + '}';
    }
}
