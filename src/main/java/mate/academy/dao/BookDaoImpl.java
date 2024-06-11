package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int updatedRows = statement.executeUpdate();
            isUpdated(updatedRows, book);
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create book :", e);
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
                return Optional.of(initializeBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find book by id :", e);
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
                books.add(initializeBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find All books: ", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Failed to update book, no rows updated");
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update book :" + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book by id: " + id, e);
        }
    }

    private Book initializeBook(ResultSet resultSet) {
        try {
            return new Book(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getBigDecimal("price"));
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get data from database", e);
        }
    }

    private void isUpdated(int updatedRows, Book book) {
        if (updatedRows < 1) {
            throw new DataProcessingException("Affected rows less than 1. Failed update: "
                    + book);
        }
    }
}
