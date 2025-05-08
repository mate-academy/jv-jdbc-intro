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
    private static final String SQL_CREATE = "INSERT INTO book (title, price) VALUES (?, ?);";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM book WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE book SET title = ?, price = ? WHERE id = ?;";
    private static final String SQL_FIND_ALL = "SELECT * FROM book;";
    private static final String SQL_DELETE = "DELETE FROM book WHERE id = ?;";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
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
            throw new DataProcessingException("Can't create book in DB" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = parseBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books from bd", e);
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(SQL_UPDATE)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Can't change data book in db " + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB " + book, e);
        }

        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1,id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from db by id " + id, e);
        }
    }

    private static Book parseBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString("title"));
        book.setId(resultSet.getObject("id", Long.class));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
