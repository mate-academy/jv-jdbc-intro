package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.dao.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String createQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, but inserted 0 rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to save book", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Book book = getDataFromResultSet(rs);

                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find book with id " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String findAllQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllQuery)) {
            List<Book> books = new ArrayList<>();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Book book = getDataFromResultSet(rs);
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to retrieve all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("No book found with the specified ID: " + book.getId());
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book by ID: " + id, e);
        }
    }

    private Book getDataFromResultSet (ResultSet rs) throws SQLException {
        String title = rs.getString("title");
        BigDecimal price = rs.getObject("price", BigDecimal.class);
        long id = rs.getLong("id");

        Book book = new Book();
        book.setPrice(price);
        book.setTitle(title);
        book.setId(id);

        return book;
    }
}
