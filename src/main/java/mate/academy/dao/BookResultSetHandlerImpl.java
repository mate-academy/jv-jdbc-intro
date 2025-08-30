package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import mate.academy.model.Book;

public class BookResultSetHandlerImpl implements BookResultSetHandler {
    public Long handleGeneratedId(ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        }
        return null;
    }

    public Book handleBookResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        while (resultSet.next()) {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
        }
        return book;
    }
}
