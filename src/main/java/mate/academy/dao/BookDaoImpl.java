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
import mate.academy.util.DatabaseConnector;

@Dao
public class BookDaoImpl implements BookDao {

    private static final String INSERT_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_BOOKS = "SELECT * FROM books";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error inserting book into database", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseBook(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Error retrieving book with id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(FIND_ALL_BOOKS);
             ResultSet resultSet = statement.executeQuery()) {
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Error retrieving all books", e);
        }
    }

    @Override
    public void update(Book book) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(UPDATE_BOOK)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() > 0) {
                return;
            }
            throw new DataProcessingException("Book with id " + book.getId() + " not found", null);
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book with id " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(DELETE_BOOK_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book with id " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}