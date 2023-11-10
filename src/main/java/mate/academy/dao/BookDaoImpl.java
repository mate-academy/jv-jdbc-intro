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
    private static final String SQL_CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SQL_UPDATE_QUERY
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String SQL_FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String SQL_DELETE_BY_ID_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE_QUERY,
                           Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            checkAffectedRows(statement.executeUpdate());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot insert book " + book.getTitle()
                    + " to the DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the book by index " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(SQL_UPDATE_QUERY)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            checkAffectedRows(preparedStatement.executeUpdate());
            Optional<Book> oldBook = findById(book.getId());
            return oldBook.orElse(null);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book: " + book.getTitle(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete the book with id "
                    + id, e);
        }
    }

    private static void checkAffectedRows(int affectedRows) {
        if (affectedRows < 1) {
            throw new RuntimeException("Expected to affect at least 1 row: "
                    + affectedRows + "affected rows");
        }
    }

    private Book parseToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID_COLUMN, Long.class));
        book.setTitle(resultSet.getString(TITLE_COLUMN));
        book.setPrice(resultSet.getObject(PRICE_COLUMN, BigDecimal.class));
        return book;
    }
}
