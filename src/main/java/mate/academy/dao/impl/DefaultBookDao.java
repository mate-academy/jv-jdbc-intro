package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class DefaultBookDao implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) "
                + "VALUES (?, ?)";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * "
                + "FROM books "
                + "WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setObject(1, id, JDBCType.BIGINT);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next()
                    ? Optional.of(extractBook(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find book by id " + id, e);
        }
    }

    private Book extractBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * "
                + "FROM books";

        List<Book> allBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allBooks.add(extractBook(resultSet));
            }
            return allBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books "
                + "SET title = ?, price = ? "
                + "WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setObject(3, book.getId(), JDBCType.BIGINT);
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books "
                + "WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setObject(1, id, JDBCType.BIGINT);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book by id " + id, e);
        }
    }
}
