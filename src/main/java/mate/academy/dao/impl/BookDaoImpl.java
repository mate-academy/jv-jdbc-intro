package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.lib.Dao;
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
        String sql = "INSERT INTO books(title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            checkForValidAffectedRows(affectedRows);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book: " + book, e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            ResultSet readBook = statement.executeQuery();
            if (readBook.next()) {
                String title = readBook.getString("title");
                BigDecimal price = readBook.getObject("price", BigDecimal.class);

                book = new Book(id, title, price);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read a book, with id: " + id, e);
        }

        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet readBook = statement.executeQuery();
            while (readBook.next()) {
                Long id = readBook.getObject("id", Long.class);
                String title = readBook.getString("title");
                BigDecimal price = readBook.getObject("price", BigDecimal.class);

                Book book = new Book(id, title, price);

                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read a books", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            checkForValidAffectedRows(affectedRows);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book: " + book, e);
        }

        return book;
    }

    private void checkForValidAffectedRows(int affectedRows) {
        if (affectedRows < 1) {
            throw new RuntimeException("Expected at least 1 row but got " + affectedRows);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        boolean isDeleted;

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book with id: " + id, e);
        }

        return isDeleted;
    }
}
