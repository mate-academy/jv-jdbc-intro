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
import mate.academy.utils.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String SQL_CREATE = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM books;";
    private static final String SQL_UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREATE,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            verifyAffectedRows(statement.executeUpdate());

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add book " + book.getTitle()
                    + " to the DB", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            Book book = null;

            if (resultSet.next()) {
                book = parseBook(resultSet);
            }

            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet allBooks = statement.executeQuery();

            return getBooks(allBooks, books);
        } catch (SQLException e) {
            throw new DataProcessingException("DB is empty", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            statement.executeUpdate();

            Optional<Book> oldBook = findById(book.getId());

            return oldBook.orElse(null);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book.getTitle(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book with id: " + id, e);
        }
    }

    private static Book parseBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }

    private static List<Book> getBooks(ResultSet resultSet, List<Book> result) throws SQLException {
        while (resultSet.next()) {
            Book book = parseBook(resultSet);
            result.add(book);
        }

        return result;
    }

    private static void verifyAffectedRows(int affectedRows) {
        if (affectedRows < 1) {
            throw new RuntimeException("Expected to insert at least 1 row, actual: "
                    + affectedRows);
        }
    }
}
