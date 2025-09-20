package mate.academy.dao.daoimpl;

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
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.models.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int rows = statement.executeUpdate();
            if (rows < 1) {
                throw new DataProcessingException("Expected to insert at leas one row"
                        + ", but there is: "
                        + rows);
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create book in DB: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Optional<Book> optionalBook;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            optionalBook = Optional.of(bookUtil(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find book by ID: " + id, e);
        }

        return optionalBook;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(bookUtil(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find any books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3,book.getId());
            int i = statement.executeUpdate();
            if (i == 0) {
                throw new DataProcessingException("There is no book in database with ID: "
                        + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book with ID: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int i = statement.executeUpdate();
            return (i > 0);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book with ID: " + id, e);
        }
    }

    private Book bookUtil(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(1, Long.class));
        book.setTitle(resultSet.getObject(2, String.class));
        book.setPrice(resultSet.getObject(3, BigDecimal.class));
        return book;
    }
}
