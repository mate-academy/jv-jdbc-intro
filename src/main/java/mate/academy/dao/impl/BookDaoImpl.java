package mate.academy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sqlQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            checkAffectedRows(affectedRows);
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    book.setId(resultSet.getObject(1, Long.class));
                    return book;
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseResulToObj(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id:" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sqlQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(parseResulToObj(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(3, book.getId());
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affordRows = statement.executeUpdate();
            checkAffectedRows(affordRows);
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    book.setId(resultSet.getObject("id", Long.class));
                    book.setTitle(resultSet.getString("title"));
                    book.setPrice(resultSet.getBigDecimal("price"));
                    return book;
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book parseResulToObj(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getObject(1, Long.class));
            book.setPrice(resultSet.getBigDecimal("price"));
            book.setTitle(resultSet.getString("title"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse result set to object" + resultSet, e);
        }
        return book;
    }

    private void checkAffectedRows(int affectedRows) {
        if (affectedRows < 1) {
            throw new DataProcessingException("Creating book failed, no rows affected");
        }
    }
}
