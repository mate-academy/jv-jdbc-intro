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
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_BOOK_QUERY =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BOOK_BY_ID_QUERY =
            "SELECT id, title, price FROM books WHERE id = ?";
    private static final String SELECT_ALL_BOOKS_QUERY =
            "SELECT id, title, price FROM books";
    private static final String UPDATE_BOOK_QUERY =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_BY_ID_QUERY =
            "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(SELECT_BOOK_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by ID: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() > 0) {
                return book;
            }
            throw new RuntimeException("Error updating book: " + book);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(DELETE_BOOK_BY_ID_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book by ID: " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}
