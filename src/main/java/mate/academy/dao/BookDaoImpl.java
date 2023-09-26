package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_QUERY = "INSERT INTO book(title, price) VALUES(?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book";
    private static final String UPDATE_QUERY = "UPDATE book SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM book WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Creating book failed, no rows affected.");
            }
            book.setId(getCreatedId(statement));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return Optional.of(mapToBook(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Getting book by id=%d was failed", id), e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Retrieving all books from DB was failed", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException(
                        String.format("Book with id=%d wasn't updated, no rows affected.",
                                book.getId())
                );
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Updating book with id=%d was failed", book.getId()), e
            );
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Deletion book by id=%d was failed", id), e);
        }
    }

    private Long getCreatedId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (!generatedKeys.next()) {
                throw new DataProcessingException("Creating book failed, no ID obtained.");
            }
            return generatedKeys.getLong(1);
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
