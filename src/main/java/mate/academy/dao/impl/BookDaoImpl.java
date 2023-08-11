package mate.academy.dao.impl;

import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row, but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book = " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(parseDataToBook(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to find the book by id = " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> booksList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = parseDataToBook(resultSet);
                booksList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to find any book in DB", e);
        }
        return booksList;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new DataProcessingException("Expected updated book"
                        + book + " But it's not happened");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to delete the book by id = " + id, e);
        }
    }

    private Book parseDataToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getObject("title", String.class));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
