package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import mate.academy.Book;
import mate.academy.ConnectionUtill;

public class BookDaoImpl implements BookDao {

    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtill.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, "
                        + "but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Book get(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtill.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                int price = resultSet.getObject("price", Integer.class);
                Book book = new Book();
                book.setId(id);
                book.setPrice(price);
                book.setTitle(title);
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
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
