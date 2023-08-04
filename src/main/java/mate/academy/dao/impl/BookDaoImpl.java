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
        String insertQuery = "INSERT INTO books (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't add new book" + book, e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String selectQuery = "SELECT * FROM books WHERE is_deleted = FALSE;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't getAll books from DB", e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectQuery = "SELECT * FROM books WHERE id = ? AND is_deleted = FALSE;";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = getBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get book by id " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String softDeleteQuery = "UPDATE books SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(softDeleteQuery)) {
            statement.setLong(1, id);
            int numberOfDeletedRows = statement.executeUpdate();
            return numberOfDeletedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete book with id" + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}
