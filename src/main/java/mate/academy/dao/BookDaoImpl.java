package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    // Here you should paste your own properties;
    private static final ConnectionUtil CONNECTION_UTIL =
            new ConnectionUtil("username",
                    "localhost", "password",
                    "3306", "test");

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = CONNECTION_UTIL.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert 1 >= rows, but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot insert new book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findBy(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = CONNECTION_UTIL.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<Book> optionalBook = Optional.empty();
            if (resultSet.next()) {
                optionalBook = Optional.of(new Book(id,
                        resultSet.getObject("title", String.class),
                        resultSet.getBigDecimal("price")));
            }
            return optionalBook;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        try (Connection connection = CONNECTION_UTIL.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getLong("id"),
                        resultSet.getObject("title", String.class),
                        resultSet.getBigDecimal("price")));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = CONNECTION_UTIL.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert 1 >= rows, but inserted 0 rows.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = CONNECTION_UTIL.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert 1 >= rows, but inserted 0 rows.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book by id: " + id, e);
        }
    }
}
