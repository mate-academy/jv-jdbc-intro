package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sqlSaveRequest = "INSERT INTO books (title, price) Values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlSaveRequest,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Сan not created row with book title:"
                        + book.getTitle(), new RuntimeException());
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot save the new book", e);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String sqlSaveRequest = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlSaveRequest)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Update Book failed.", new RuntimeException());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot save the book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlSaveRequest = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlSaveRequest)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete the book with id: " + id, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlSaveRequest = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlSaveRequest)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractBookFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find the book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sqlSaveRequest = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlSaveRequest)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(extractBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find the books", e);
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
