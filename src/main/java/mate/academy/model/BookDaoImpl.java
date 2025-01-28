package mate.academy.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;

public class BookDaoImpl implements BookDao {
    private static final String URL = "jdbc:mysql://localhost:3306/book";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Root1234";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't insert book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long bookId = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return Optional.of(new Book(bookId, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                books.add(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() > 0) {
                return book;
            } else {
                throw new DataProcessingException(
                        "Can't update book with id " + book.getId(), null);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't delete book with id " + id, e);
        }
    }
}
