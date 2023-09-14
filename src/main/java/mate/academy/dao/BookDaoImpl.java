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
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Row with a new book was not inserted.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a new book: " + book, e);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET books.title = ?, books.price = ? WHERE books.id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Row with a book is not updated. Book: " + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE books.id = ?";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = toEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id: " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(toEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }
        return books;
    }

    @Override
    public boolean delete(Book book) {
        String query = "DELETE FROM books WHERE books.id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, book.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book: " + book, e);
        }
    }

    private Book toEntity(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
