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

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                         .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least "
                        + "one row but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            book = getGeneratedIDandSetToBook(generatedKeys, book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        ResultSet resultSet;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = parseBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listOfBooks.add(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find all books:", e);
        }
        return listOfBooks;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update at least "
                        + "one row, but inserted 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book=" + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        int affectedRows = 0;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id=" + id, e);
        }
        return affectedRows > 0;
    }

    private Book parseBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        Long id = rs.getObject("id", Long.class);
        String title = rs.getString("title");
        BigDecimal price = rs.getObject("price", BigDecimal.class);
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    private Book getGeneratedIDandSetToBook(ResultSet generatedKeys, Book book)
                throws SQLException {
        Long id = null;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong(1);
        }
        book.setId(id);
        return book;
    }
}
