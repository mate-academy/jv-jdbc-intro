package mate.academy.model;

import java.math.BigDecimal;

public record Book(Long id, String title, BigDecimal price) {
    public Book withId(Long id) {
        return new Book(id, this.title, this.price);
    }
}
