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
    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new RuntimeException("Book is null.");
        }

        if (book.getTitle() == null) {
            throw new RuntimeException("Book Title is null");
        }

        if (book.getTitle().isEmpty()) {
            throw new RuntimeException("Book Title is empty");
        }

        if (book.getPrice() == null) {
            throw new RuntimeException("Book Price is null");
        }

        if (book.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Book Price can not be negative");
        }

        String sql = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row, "
                        + "but actually got 0 rows were inserted.", null);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not add book to database: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = getFoundedBook(resultSet);
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT id, title, price FROM books";

        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = getFoundedBook(resultSet);
                books.add(book);
            }

            return books;

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataProcessingException(
                        "Book with id " + book.getId() + " not found", null);
            }

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by ID: " + id, e);
        }
    }

    private Book getFoundedBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
