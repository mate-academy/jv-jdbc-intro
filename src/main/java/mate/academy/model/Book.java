package mate.academy.model;

import java.math.BigDecimal;

public interface Book {
    void setTitle(String title);

    void setPrice(BigDecimal price);

    void setId(Long id);

    String getTitle();

    BigDecimal getPrice();

    Long getId();
}
