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
import mate.academy.model.Book;
import mate.academy.service.ConnectorUtil;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sqlQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectorUtil.getConnector()) {
            PreparedStatement statement = connection.prepareStatement(
                    sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected update at least 1 field, but wes "
                        + affectedRows);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't cerate a Book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection connection = ConnectorUtil.getConnector()) {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = getBookFromSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book", e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String sqlQuery = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectorUtil.getConnector()) {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                books.add(getBookFromSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectorUtil.getConnector()) {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlQuery = "DELETE FROM books WHERE id = ?";
        int result = 0;
        try (Connection connection = ConnectorUtil.getConnector()) {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book", e);
        }
        return result > 0;
    }

    private Book getBookFromSet(ResultSet set) throws SQLException {
        long resultId = set.getObject("id", Long.class);
        String title = set.getObject("title", String.class);
        BigDecimal price = set.getObject("price", BigDecimal.class);
        return new Book(resultId, title, price);
    }
}
