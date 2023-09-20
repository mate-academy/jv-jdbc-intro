package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final Map<String, String> requests;

    static {
        requests = Map.of(
                "create", "INSERT INTO books(title, price) VALUES (?, ?)",
                "findById", "SELECT * from books WHERE id = ?",
                "findAll", "SELECT * FROM books",
                "update", "UPDATE books SET title = ?, price = ? WHERE id = ?",
                "deleteById", "DELETE FROM books WHERE id = ?"
        );
    }

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(requests.get("create"),
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (!isRowAffected(statement.executeUpdate())) {
                throw new DataProcessingException("Insert failed for book: " + book);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(connectionErrorMsg(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(requests.get("findById"))) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = parseResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException(connectionErrorMsg(), e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(requests.get("findAll"))) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(parseResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException(connectionErrorMsg(), e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(requests.get("update"))) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (!isRowAffected(statement.executeUpdate())) {
                throw new DataProcessingException("Update failed for book with id:" + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(connectionErrorMsg(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(requests.get("deleteById"))) {
            statement.setLong(1, id);
            return isRowAffected(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataProcessingException(connectionErrorMsg(), e);
        }
    }

    private Book parseResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId((Long) resultSet.getObject("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    private String connectionErrorMsg() {
        return "Connection to the DB failed";
    }

    private boolean isRowAffected(int affectedRow) {
        return affectedRow > 0;
    }
}
