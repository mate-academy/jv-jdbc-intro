package mate.academy.dao.impl;

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
import mate.academy.model.Book;
import mate.academy.model.impl.BookImpl;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    book.setId(id);
                    return book;
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create an object with the title "
                    + book.getTitle()
                    + " and the price "
                    + book.getPrice(), e);
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
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");

                Book book = new BookImpl();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find a book with id " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Book> bookList = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Book book = new BookImpl();

                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);

                bookList.add(book);
            }

            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all rows", e);
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

            if (affectedRows > 0) {
                return book;
            } else {
                throw new DataProcessingException("Book update failed. No rows affected.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book", e);
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
            throw new RuntimeException("Error deleting book with ID: " + id, e);
        }
    }
}
