package mate.academy.dao;

import mate.academy.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.lib.Dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            setBookParameters(statement, book);
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Failed to create book: no rows were updated.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating book " + book + " failed, no ID obtained.");
                }
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseBook(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find book by ID " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setBookParameters(statement, book);
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Failed to create book: no rows were updated.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update book" + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {

            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book by ID " + id, e);
        }
    }

    private Connection getConnection() throws SQLException {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "Vika07012000");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/books", dbProperties);
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    private void setBookParameters(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setBigDecimal(2, book.getPrice());
    }
}