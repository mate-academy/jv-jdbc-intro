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
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at less one row, but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                BigDecimal id = generatedKeys.getObject(1, BigDecimal.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create a connection for saving to the DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, BigDecimal.valueOf(id));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getBigDecimal(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getInt(3));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cannot create a connection for getting by id from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getBigDecimal(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getInt(3));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cannot create a connection for getting all from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ? price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(3, book.getId());
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update one row, but updated 0 rows");
            }
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Book bookFromDB = new Book();
                bookFromDB.setId(resultSet.getBigDecimal(1));
                bookFromDB.setTitle(resultSet.getString(2));
                bookFromDB.setPrice(resultSet.getInt(3));
                return bookFromDB;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create a connection for updating DB data", e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, BigDecimal.valueOf(id));

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to delete one row, but deleted 0 rows");
            } else {
                return true;
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create a connection for updating DB data", e);
        }
    }
}
