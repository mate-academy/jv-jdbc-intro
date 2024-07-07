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

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "insert into books (title, price) values (?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row but nothing",
                        new RuntimeException());
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book " + book + " ", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            Book book = getBook(resultSet, id);
            return Optional.of(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id=" + id + " ", e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (
                Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                Book book = getBook(resultSet, id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET price = ? WHERE id = ? ";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(2, book.getId());
            statement.setBigDecimal(1, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException(
                        "Expected to insert at least 1 row, but 0 was inserted",
                        new RuntimeException());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("This book " + book + " was not founded", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book by id = " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet, Long id) throws SQLException {
        String title = resultSet.getObject("title", String.class);
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        return new Book(id, price, title);
    }
}
