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
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;
import mate.academy.service.DataProcessingException;

@mate.academy.lib.Dao
public class BookDao implements Dao {
    private static final String FIND_ALL_REQUEST = "SELECT * FROM books";
    private static final String FIND_BY_ID_REQUEST = "SELECT * FROM books WHERE id = ?";
    private static final String CREATE_REQUEST = "INSERT INTO books (title,price) VALUES (?, ?)";
    private static final String UPDATE_REQUEST
            = "UPDATE books SET title = ?,price = ? WHERE id = ?";
    private static final String DELETE_REQUEST = "DELETE FROM books WHERE id = ?;";

    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(CREATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert one row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add Book: " + book, e);
        }
        return book;
    }

    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return book;
            }
            throw new DataProcessingException("Expected to update one row, but updated 0 rows.");
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update book: " + book,e);
        }
    }

    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(FIND_BY_ID_REQUEST)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = getBookFromRequest(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(FIND_ALL_REQUEST)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = getBookFromRequest(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books ", e);
        }
        return books;
    }

    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST)) {
            statement.setLong(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by Id: " + id,e);
        }
    }

    private Book getBookFromRequest(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
