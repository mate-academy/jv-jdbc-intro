package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;

import java.sql.*;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO info (book_name, author_name, price, quantity, publish_year) \n" +
                "VALUES ('?', '?', ?, ?, ?);\n";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setObject(3, book.getYear());
            statement.setInt(4, book.getQuantity());
            statement.setInt(5, book.getYear());

            int affectedRows = statement.executeUpdate();

            if(affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can not create a connection to the DB", e);
        }
        return book;
    }

    @Override
    public Book get(Long id) {
        String sql = "SELECT * FROM info WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String authorName = resultSet.getString("author_name");
                String bookName = resultSet.getString("book_name");
                Integer year = resultSet.getObject("published_year", Integer.class);
                Integer price = resultSet.getObject("price", Integer.class);
                Integer quantity = resultSet.getObject("quantity", Integer.class);

                Book book = new Book();
                book.setAuthor(authorName);
                book.setId(id);
                book.setName(bookName);
                book.setPrice(price);
                book.setQuantity(quantity);
                book.setYear(year);
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not create a connection to the DB", e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean delete(Book book) {
        return false;
    }

}
