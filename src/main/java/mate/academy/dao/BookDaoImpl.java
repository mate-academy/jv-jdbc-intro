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
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                            CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least 1 row, but 0 was inserted");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book, " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> result = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(handleResultsAfterResultSet(resultSet, id));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by id: " + id, e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject(COLUMN_ID, Long.class);
                result.add(handleResultsAfterResultSet(resultSet, id));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books in the table ", e);
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to update at least 1 row, but 0 was updated.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book by the id: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book by id: " + id, e);
        }
    }

    private Book handleResultsAfterResultSet(ResultSet resultSet, Long id) {
        try {
            String title = resultSet.getString(COLUMN_TITLE);
            BigDecimal price = resultSet.getBigDecimal(COLUMN_PRICE);
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the results from ResultSet", e);
        }
    }
}
