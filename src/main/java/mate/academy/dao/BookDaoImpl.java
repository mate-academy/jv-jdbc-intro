package mate.academy.dao;

import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.services.ConnectionUtil;
import java.sql.*;
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
            checkInsertedRowsAmount(affectedRows);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
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
                return Optional.of(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id. ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(createBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books.", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            checkInsertedRowsAmount(affectedRows);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id. ID = " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    private void checkInsertedRowsAmount(int affectedRows) {
        if (affectedRows < 1) {
            throw new RuntimeException("Expected to insert at least 1 row, but inserted 0 rows.");
        }
    }
}
