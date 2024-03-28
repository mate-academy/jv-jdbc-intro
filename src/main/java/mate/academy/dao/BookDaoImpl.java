package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
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
    private static final String ID = "ID";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row but inserted: "
                        + affectedRows);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getBigDecimal(PRICE);

                return Optional.of(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            books = parseResultSetToBooks(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Error while finding all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ? , price = ? WHERE ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException(
                        "Expected to update at least one row but updated: " + updatedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book with ID: " + id, e);
        }
    }

    private static List<Book> parseResultSetToBooks(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();

        while (resultSet.next()) {
            long id = resultSet.getLong(ID);
            String title = resultSet.getString(TITLE);
            BigDecimal price = resultSet.getBigDecimal(PRICE);
            books.add(new Book(id, title, price));
        }
        return books;
    }
}
