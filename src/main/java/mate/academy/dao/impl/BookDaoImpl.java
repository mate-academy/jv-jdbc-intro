package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private String request;

    @Override
    public Book create(Book book) {
        request = "INSERT INTO books (title, price) VALUES(?, ?)";
        Book savedBook = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     request, PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            statement.setObject(SECOND_PARAMETER_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at leas one row, but inserted 0 rows.");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(FIRST_PARAMETER_INDEX, Long.class);
                savedBook = findById(id).orElseThrow();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book " + book, e);
        }
        return savedBook;
    }

    @Override
    public Optional<Book> findById(Long id) {
        request = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(returnItitalizedBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id= " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        request = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(returnItitalizedBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        request = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            statement.setObject(SECOND_PARAMETER_INDEX, book.getPrice());
            statement.setObject(THIRD_PARAMETER_INDEX, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new RuntimeException(
                        "Expected to insert at least one row, but inserted 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        request = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setObject(FIRST_PARAMETER_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id= " + id, e);
        }
    }

    private Book returnItitalizedBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        return new Book(id, title, price);
    }
}
