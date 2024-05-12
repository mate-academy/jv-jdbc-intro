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
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least 1 row, but 0 was inserted");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to insert new book record", e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    throw new DataProcessingException(
                            "Failed to insert new book record into the database", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DataProcessingException(
                            "Failed to close PreparedStatement after attempting "
                                    + "to insert new book", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DataProcessingException(
                            "Failed to close Connection after attempting "
                                    + "to insert new book", e);
                }
            }
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Optional<Book> result = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", BigDecimal.class));
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Failed to retrieve book from the database for ID: " + id, e
            );
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", BigDecimal.class));
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Failed to retrieve books from the database", e
            );
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Failed to update book record for ID: " + book.getId(), e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                            connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book record for ID: " + id, e);
        }
    }
}
