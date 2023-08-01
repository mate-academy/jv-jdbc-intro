package mate.academy.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Book {
  private Long id;
  private String title;
  private BigDecimal price;
}