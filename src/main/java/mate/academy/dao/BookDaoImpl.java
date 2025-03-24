package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sqlQuery = "INSERT INTO books (title, price) values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int result = statement.executeUpdate();
            if (result < 1) {
                throw new DataProcessingException("No rows were affected executing query. "
                        + "Something went wrong.", new SQLException());
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            }
        } catch (DataProcessingException | SQLException de) {
            throw new RuntimeException("Can't create connection to DB.", de);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getBigDecimal(3));
                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (DataProcessingException | SQLException de) {
            throw new RuntimeException("Can't create connection to DB.", de);
        }
    }

    @Override
    public List<Book> findAll() {
        String sqlQuery = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(BigDecimal.valueOf(resultSet.getDouble("price")));
                books.add(book);
            }
        } catch (DataProcessingException | SQLException de) {
            throw new RuntimeException("Can't create connection to DB.", de);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int result = statement.executeUpdate();
            if (result >= 1) {
                return book;
                }
        } catch (DataProcessingException | SQLException de) {
            throw new RuntimeException("Can't create connection to DB.", de);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result >= 1;
        } catch (DataProcessingException | SQLException de) {
            throw new RuntimeException("Can't create connection to DB.", de);
        }
    }
}
