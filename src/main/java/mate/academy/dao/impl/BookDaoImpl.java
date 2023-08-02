package mate.academy.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insertRequest
                = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createStatement = connection
                     .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book data to DB. Book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String request = "SELECT id, title, price " +
                "FROM books b " +
                "WHERE b.id = ? AND b.is_deleted = FALSE;";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(request)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement
                    .executeQuery();
            if (resultSet.next()) {
                book = parseBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get book from DB. Book Id: " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String getAllRequest = "SELECT * FROM books b " +
                "WHERE b.is_deleted = FALSE;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllStatement = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all books from DB.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books SET title = ?, price = ? " +
                "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setLong(3, book.getId());
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB. Book:  " + book,e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteBookRequest = "UPDATE books SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteBookRequest)) {
            deleteStatement.setLong(1, id);
            int numberOfDeletedRows = deleteStatement.executeUpdate();
            return numberOfDeletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setId(resultSet.getObject("id", Long.class));
        return book;
    }
}
