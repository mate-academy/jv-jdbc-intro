package mate.academy.dao;


import mate.academy.ConnectionUtil;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sqlCall = "INSERT INTO books(title, price) VALUES(?, ?)";
        String title = book.getTitle();
        BigDecimal price = book.getPrice();
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlCall, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            statement.setBigDecimal(2, price);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, "
                        + "but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlCall = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setTitle(title);
                book.setPrice(price);
                book.setId(id);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to thr DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlCall = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Long id = resultSet.getObject("id", Long.class);
                Book book = new Book();
                book.setTitle(title);
                book.setPrice(price);
                book.setId(id);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to thr DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlCall = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update data");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to thr DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlCall = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to delete at least one row, "
                        + "but deleted 0 rows.");
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book", e);
        }
    }
}
