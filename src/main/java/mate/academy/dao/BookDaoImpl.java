package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.util.ConnectionCreator;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CANNOT_CREATE_BOOK_MSG = "Cannot create the book: ";
    private static final String CANNOT_FIND_OBJECT_MSG = "Cannot find the needed object by id: ";
    private static final String CANNOT_UPDATE_ROW_MSG = "Cannot update the row by ID: ";
    private static final String FAILED_TO_DELETE_ROW = "Cannot delete the element. ID: ";
    private static final String FAILED_TO_FIND_ROWS_MSG = "Failed to find all rows";
    private static final String ID_COLUMN_NAME = "id";
    private static final String NO_ROWS_INSERTED_MSG = "No rows inserted";
    private static final String NO_NEEDED_ROW_MSG = "The required row is not present";
    private static final String PRICE_COLUMN_NAME = "price";
    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO books (title, price) "
            + "VALUES (?, ?)";
    private static final String SQL_DELETE_STATEMENT = "DELETE FROM books WHERE id = ?";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE books SET title = ?,"
            + " price = ? WHERE id = ?";
    private static final String SQL_SELECT_ALL_STATEMENT = "SELECT * FROM books";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionCreator.createConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         SQL_INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_ONE, book.getTitle());
            statement.setLong(INDEX_TWO, book.getPrice().longValue());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(NO_ROWS_INSERTED_MSG);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book = mapResultSetIntoBook(generatedKeys);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_CREATE_BOOK_MSG + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionCreator.createConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_STATEMENT)) {
            statement.setLong(INDEX_ONE, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = mapResultSetIntoBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_FIND_OBJECT_MSG + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = ConnectionCreator.createConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         SQL_SELECT_ALL_STATEMENT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetIntoBook(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new DataProcessingException(FAILED_TO_FIND_ROWS_MSG, e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionCreator.createConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATEMENT)) {
            statement.setString(INDEX_ONE, book.getTitle());
            statement.setObject(INDEX_TWO, book.getPrice());
            statement.setLong(INDEX_THREE, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(NO_NEEDED_ROW_MSG);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_UPDATE_ROW_MSG + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionCreator.createConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STATEMENT)) {
            statement.setLong(INDEX_ONE, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(FAILED_TO_DELETE_ROW + id, e);
        }
    }

    private Book mapResultSetIntoBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID_COLUMN_NAME, Long.class));
        book.setTitle(resultSet.getString(TITLE_COLUMN_NAME));
        book.setPrice(resultSet.getBigDecimal(PRICE_COLUMN_NAME));
        return book;
    }
}
