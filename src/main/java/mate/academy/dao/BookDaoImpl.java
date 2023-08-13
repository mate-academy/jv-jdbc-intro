package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String createRequest = "INSERT INTO books(title, price) VALUES(?, ?);";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = dbConnection
                        .prepareStatement(createRequest, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a book " + book
                    + " in DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findRequest = "SELECT id, title, price FROM books WHERE id = ? AND is_deleted = false;";
        Book book = null;
        try (Connection dbConnection = ConnectionUtil.getConnection();
             PreparedStatement findStatement = dbConnection
                     .prepareStatement(findRequest)) {
            findStatement.setLong(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next()) {
                book = parseBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id " + id
                    + " in DB", e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String findRequest = "SELECT id, title, price FROM books WHERE is_deleted = false;";
        List<Book> books = new ArrayList<>();
        try (Connection dbConnection = ConnectionUtil.getConnection();
             PreparedStatement findStatement = dbConnection
                     .prepareStatement(findRequest)) {
            ResultSet resultSet = findStatement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books in DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books SET title = ?, price = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
             PreparedStatement updateStatement = dbConnection
                     .prepareStatement(updateRequest)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with id " + book.getId()
                    + " in DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRequest = "UPDATE books SET is_deleted = TRUE WHERE id = ?;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
             PreparedStatement deleteStatement = dbConnection
                     .prepareStatement(deleteRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id " + id
                    + " from DB", e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getObject("id", Long.class),
                resultSet.getString("title"), resultSet.getBigDecimal("price"));
    }
}
