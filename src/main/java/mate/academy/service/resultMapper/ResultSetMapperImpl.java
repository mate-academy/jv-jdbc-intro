package mate.academy.service.resultMapper;

import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapperImpl implements ResultSetMapper{
    @Override
    public Book mapFromResultToBook(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble("price"));
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
