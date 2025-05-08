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
    private static final int COLUMN_INDEX = 1;
    private static final int EDGE_INVALID_NUMBER_AFFECTED_ROWS = 0;
    private static final int EDGE_VALID_NUMBER_AFFECTED_ROWS = 1;
    private static final int PARAMETER_INDEX_ONE = 1;
    private static final int PARAMETER_INDEX_THREE = 3;
    private static final int PARAMETER_INDEX_TWO = 2;
    private static final String COLUMN_LABEL_ID = "id";
    private static final String COLUMN_LABEL_PRICE = "price";
    private static final String COLUMN_LABEL_TITLE = "title";
    private static final String EXCEPTION_CAN_NOT_ADD_BOOK = "Can't add new book: ";
    private static final String EXCEPTION_CAN_NOT_UPDATE_BOOKS = "Can not update books with id = ";
    private static final String EXCEPTION_NO_INSERT_BOOK
            = "Expected to insert at least one row, but inserted 0 rows.";
    private static final String EXCEPTION_NO_UPDATE_BOOKS
            = "Expected to update at least one row, but updated 0 rows.";
    private static final String EXCEPTION_NOT_FIND_BOOK_BY_ID = "Can not find book by id ";
    private static final String QUERY_CREATE_BOOK
            = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String QUERY_DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String QUERY_FIND_ALL_BOOKS = "SELECT * FROM books";
    private static final String QUERY_FIND_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String QUERY_UPDATE_BOOK
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(QUERY_CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(PARAMETER_INDEX_ONE, book.getTitle());
            statement.setBigDecimal(PARAMETER_INDEX_TWO, book.getPrice());
            if (statement.executeUpdate() < EDGE_VALID_NUMBER_AFFECTED_ROWS) {
                throw new RuntimeException(EXCEPTION_NO_INSERT_BOOK);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(COLUMN_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(EXCEPTION_CAN_NOT_ADD_BOOK + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BOOK_BY_ID)) {
            statement.setLong(PARAMETER_INDEX_ONE, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(EXCEPTION_NOT_FIND_BOOK_BY_ID + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_FIND_ALL_BOOKS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(mapToBook(resultSet));
            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_BOOK)) {
            statement.setString(PARAMETER_INDEX_ONE, book.getTitle());
            statement.setBigDecimal(PARAMETER_INDEX_TWO, book.getPrice());
            statement.setLong(PARAMETER_INDEX_THREE, book.getId());
            if (statement.executeUpdate() < EDGE_VALID_NUMBER_AFFECTED_ROWS) {
                throw new RuntimeException(EXCEPTION_NO_UPDATE_BOOKS);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(EXCEPTION_CAN_NOT_UPDATE_BOOKS + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(QUERY_DELETE_BOOK_BY_ID)) {
            statement.setLong(PARAMETER_INDEX_ONE, id);
            return statement.executeUpdate() > EDGE_INVALID_NUMBER_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException(EXCEPTION_CAN_NOT_UPDATE_BOOKS + id, e);
        }
    }

    private static Book mapToBook(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString(COLUMN_LABEL_TITLE);
        BigDecimal price = resultSet.getObject(COLUMN_LABEL_PRICE, BigDecimal.class);
        Long id = resultSet.getObject(COLUMN_LABEL_ID, Long.class);
        return new Book(id, title, price);
    }
}
