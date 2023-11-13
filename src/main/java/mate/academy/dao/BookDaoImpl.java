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
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

public class BookDaoImpl implements BookDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String EXPECTED_MESSAGE =
            "Expected to insert at leas one row, but inserted 0 rows";
    private static final String DB_CONNECTION_ERROR_MESSAGE =
            "Can't create a connection to the DB";
    private static final String UPDATE_ERROR_MESSAGE = "Can't update book by id: ";
    private static final String CREATE_ERROR_MESSAGE = "Can't create book";
    private static final String DELETE_ERROR_MESSAGE = "Can't delete book by id: ";
    private static final String NULL_ID_ERROR_MESSAGE = "Id can't be null";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(CREATE_QUERY,
                                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(EXPECTED_MESSAGE);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            Book book = null;
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = parseResultSetToBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            List<Book> bookList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookList.add(parseResultSetToBook(resultSet));
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            if (book.getId() == null) {
                throw new RuntimeException(NULL_ID_ERROR_MESSAGE);
            }
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 0) {
                throw new RuntimeException(UPDATE_ERROR_MESSAGE + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_QUERY)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(DELETE_ERROR_MESSAGE + id, e);
        }
    }

    private Book parseResultSetToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID, Long.class);
        String title = resultSet.getString(TITLE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        return new Book(id, title, price);
    }
}
