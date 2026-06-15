package mate.academy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String TABLE_NAME = "books";

    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book can not be null");
        }

        if (book.getTitle() == null) {
            throw new IllegalArgumentException("Book title can not be null");
        }

        String slqQuery = String.format("INSERT INTO %s (title, price) VALUES (?, ?)", TABLE_NAME);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(slqQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "At least one line was expected to be inserted, but 0 lines were inserted");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Exception while creating the book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can not be null");
        }

        String slqQuery = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(slqQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(Book.getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Exception while searching book by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String slqQuery = String.format("SELECT * FROM %s", TABLE_NAME);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(slqQuery)) {
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(Book.getBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Exception while getting all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book can not be null");
        }

        if (book.getId() == null) {
            throw new IllegalArgumentException("Book ID can not be null");
        }

        if (book.getTitle() == null) {
            throw new IllegalArgumentException("Book title can not be null");
        }

        String slqQuery =
                String.format("UPDATE %s SET title = ?, price = ? WHERE id = ? ", TABLE_NAME);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(slqQuery)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "At least one line was expected to be updated, but 0 lines were updated");
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Exception while updating the book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can not be null");
        }

        String slqQuery = String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(slqQuery)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Exception while deleting the book by ID: " + id, e);
        }
    }
}
