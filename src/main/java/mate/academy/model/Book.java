package mate.academy.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import mate.academy.exception.DataProcessingException;

public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    public static Book getBookFromResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            throw new IllegalArgumentException("Result set cannot be null");
        }

        try {
            Book book = new Book();
            book.setId(resultSet.getObject("id", Long.class));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getObject("price", BigDecimal.class));

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Exception while getting book from result set", e);
        }
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
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", price=" + price
                + '}';
    }
}
