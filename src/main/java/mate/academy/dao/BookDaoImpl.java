package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_QUERY = "INSERT INTO books(title, price) VALUES(?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Creating book failed, no rows affected.");
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
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection
                    .prepareStatement(FIND_BY_ID_QUERY)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(mapToBook(resultSet));
                    }
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Book> books = new ArrayList<>();
                    while (resultSet.next()) {
                        books.add(mapToBook(resultSet));
                    }
                    return books;
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Retrieving all books from DB was failed", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                statement.setString(1, book.getTitle());
                statement.setBigDecimal(2, book.getPrice());
                statement.setLong(3, book.getId());
                if (statement.executeUpdate() < 1) {
                    throw new DataProcessingException(
                            String.format("Book with id=%d wasn't updated, no rows affected.",
                                    book.getId()));
                }
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    String.format("Updating book with id=%d was failed", book.getId()), e
            );
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
                statement.setLong(1, id);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Deletion book by id=%d was failed", id), e
            );
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price")
        );
    }
}
