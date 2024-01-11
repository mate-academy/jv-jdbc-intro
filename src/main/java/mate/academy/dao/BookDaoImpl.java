package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String URL =
            "jdbc:mysql://localhost:3306/book_db?user=root&password=12345";
    private final Connection connection;

    public BookDaoImpl() {
        try {
            this.connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not connect to the database", e);
        }
    }

    @Override
    public Book create(Book book) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create a book", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not retrieve a book by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not retrieve all books", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update a book", e);
        }

        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete a book by id: " + id, e);
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error closing the database connection", e);
        }
    }
}
