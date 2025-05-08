package mate.academy.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    public Book(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Book(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }
}