package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new RuntimeException("Book should not be null.");
        }
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("1 row in table should be affected, but was: "
                        + affectedRows);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create a book." + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Book id should be positive and not null.");
        }
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return Optional.of(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get a book from database by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        final List<Book> allBooks = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                allBooks.add(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find all books from database.", e);
        }
        return allBooks;
    }

    @Override
    public Book update(Book book) {
        if (book == null) {
            throw new RuntimeException("Book should not be null.");
        }
        if (book.getId() == null || book.getId() <= 0) {
            throw new RuntimeException("Book id should be positive and not null.");
        }
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("1 row in table should be affected, but was: "
                        + affectedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update a book in database", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Book id should be positive and not null.");
        }
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update a book in database", e);
        }
    }
}
