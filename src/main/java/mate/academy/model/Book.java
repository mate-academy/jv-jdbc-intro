package mate.academy.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    private Book(BuilderBook builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.price = builder.price;
    }

    public static class BuilderBook {
        private Long id;
        private String title;
        private BigDecimal price;

        public BuilderBook() {
        }

        public BuilderBook setId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderBook setTitle(String title) {
            this.title = title;
            return this;
        }

        public BuilderBook setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }
        public Book build() {
            return new Book(this);
        }
    }
}
