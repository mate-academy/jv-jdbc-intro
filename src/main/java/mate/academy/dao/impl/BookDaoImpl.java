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
import mate.academy.exeption.DataProcessingException;
import mate.academy.model.Book;

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
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + " but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add new car: " + book, e);
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
                Optional<Book> book = Optional.of(parseResultSet(resultSet));
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create connection to the DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(parseResultSet(resultSet));
            }
            return books;

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find books", e);
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
            checkAffectedRows(affectedRows);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            checkAffectedRows(affectedRows);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book with ID: " + id, e);
        }
    }

    private Book parseResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Result set is empty", e);
        }
    }

    private static void checkAffectedRows(int affectedRows) {
        if (affectedRows < 1) {
            throw new RuntimeException("Expected to update at least one row");
        }
    }
}
