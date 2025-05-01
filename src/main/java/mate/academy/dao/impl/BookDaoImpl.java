package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.ConnectionUtil;
import mate.academy.dao.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql, 1)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, but was inserted 0");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, long.class);
                book.setId(id);
                return book;
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Failed to insert book", ex);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Long newId = result.getObject("id", Long.class);
                String title = result.getString("title");
                BigDecimal price = result.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setPrice(price);
                book.setTitle(title);
                book.setId(newId);
                return Optional.of(book);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Failed to find book", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet executeQuery = statement.executeQuery();
            while (executeQuery.next()) {
                Long newId = executeQuery.getLong("id");
                String title = executeQuery.getString("title");
                BigDecimal price = executeQuery.getBigDecimal("price");
                Book book = new Book();
                book.setPrice(price);
                book.setTitle(title);
                book.setId(newId);
                result.add(book);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Failed to find all books", ex);
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET price = ?, title = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(2, book.getTitle());
            statement.setBigDecimal(1, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, but was inserted 0");
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Failed to update book", ex);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, 1)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return ! (affectedRows < 1);
        } catch (SQLException ex) {
            throw new DataProcessingException("Failed to delete book by id", ex);
        }
    }
}
