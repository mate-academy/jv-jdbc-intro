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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM books";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";
    private ResultSet resultSet;
    private String query;

    @Override
    public Book create(Book book) {
        query = CREATE_BOOK;
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, was "
                        + affectedRows);
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book = new Book(resultSet.getLong(1), book.getTitle(), book.getPrice());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book. " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        query = FIND_BY_ID;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(COLUMN_TITLE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_PRICE);
                return Optional.of(new Book(id, title, price));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        query = FIND_ALL;
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(COLUMN_ID);
                String title = resultSet.getString(COLUMN_TITLE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_PRICE);
                books.add(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        query = UPDATE_BOOK;
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement =
                        connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to change at least 1 row, was "
                        + affectedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        query = DELETE_BOOK_BY_ID;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
    }
}
