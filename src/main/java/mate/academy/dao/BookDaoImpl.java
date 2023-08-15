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
        String query = "INSERT INTO books (title, price) "
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create book. " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = parseBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query)) {
            List<Book> books = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of books "
                    + "from books table.", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ?"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update book "
                    + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE books SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete book by id " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
