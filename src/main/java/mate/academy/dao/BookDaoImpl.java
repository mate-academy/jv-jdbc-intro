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
    private static final String INSERT_BOOK_SQL = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BOOK_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_BOOKS_SQL = "SELECT * FROM books";
    private static final String UPDATE_BY_ID_SQL
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_BY_ID_SQL = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_BOOK_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least one row, but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(setBook(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> booksList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                booksList.add(setBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books from database", e);
        }
        return booksList;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() > 0) {
                return book;
            }
            throw new DataProcessingException("Can't update book with id: " + book.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_BY_ID_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book setBook(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set data from DB", e);
        }
    }
}
