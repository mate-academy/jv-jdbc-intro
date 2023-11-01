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
import mate.academy.DataProcessingException;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to return one row, but inserted 0 row",
                        new RuntimeException());
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book " + book + " to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getNString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book in DB with id: " + id, e);
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> allBooks = new ArrayList<>();
        String sql = "SELECT * FROM books ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getNString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                allBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books in DB", e);
        }
        return allBooks;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updateRows = statement.executeUpdate();
            if (updateRows > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB: " + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int deleteRows = statement.executeUpdate();
            if (deleteRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete object from DB with id: " + id, e);
        }
        return false;
    }
}
