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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        final String createQuery =
                "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(createQuery,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            validateAffectedRows(statement);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        final String getQuery =
                "SELECT * FROM books WHERE id = (?)";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(getQuery)) {
            statement.setLong(1, id);
            ResultSet generatedKeys = statement.executeQuery();
            if (generatedKeys.next()) {
                return Optional.of(parse(generatedKeys));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        final String getAllQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(getAllQuery)) {
            ResultSet generatedKeys = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (generatedKeys.next()) {
                books.add(parse(generatedKeys));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all books!", e);
        }
    }

    @Override
    public Book update(Book book) {
        final String updateQuery =
                "UPDATE books SET title = (?), price = (?) WHERE id = (?)";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(updateQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            validateAffectedRows(statement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        final String deleteQuery = "DELETE FROM books WHERE id = (?)";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id: " + id, e);
        }
    }

    private Book parse(ResultSet set) throws SQLException {
        return new Book(set.getObject(1, Long.class),
                set.getString(2),
                set.getBigDecimal(3));
    }

    private void validateAffectedRows(PreparedStatement statement) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows < 1) {
            throw new DataProcessingException("Expected to affect at least one row,"
                    + " but 0 rows affected");
        }
    }
}
