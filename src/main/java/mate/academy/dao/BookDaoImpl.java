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
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_SQL_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_SQL_QUERY =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_SQL_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String NO_CHANGES_EXCEPTION_MESSAGE =
            "Expected to change at least 1 row, but changed 0 rows!";
    private static final String CREATE_EXCEPTION_MESSAGE = "Cannot add new book!";
    private static final String FIND_BY_ID_EXCEPTION_MESSAGE = "Cannot find the book!";
    private static final String FIND_ALL_EXCEPTION_MESSAGE = "Can not find books!";
    private static final String UPDATE_EXCEPTION_MESSAGE = "Cannot update book data!";
    private static final String DELETE_EXCEPTION_MESSAGE = "Cannot delete book!";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(CREATE_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(NO_CHANGES_EXCEPTION_MESSAGE);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CREATE_EXCEPTION_MESSAGE, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(FIND_BY_ID_EXCEPTION_MESSAGE, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> allBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allBooks.add(createBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(FIND_ALL_EXCEPTION_MESSAGE, e);
        }
        return allBooks;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(UPDATE_SQL_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(NO_CHANGES_EXCEPTION_MESSAGE);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(UPDATE_EXCEPTION_MESSAGE, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL_QUERY)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(NO_CHANGES_EXCEPTION_MESSAGE);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(DELETE_EXCEPTION_MESSAGE, e);
        }
    }

    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");;
        return new Book(id,title,price);
    }
}
