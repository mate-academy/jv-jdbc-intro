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
        int k = 0;
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(++k, book.getTitle());
            statement.setBigDecimal(++k, book.getPrice());
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
        Optional<Book> optionalBook = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = createBook(resultSet);
                optionalBook = Optional.ofNullable(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to find the book by id = " + id, e);
        }
        return optionalBook;
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> booksList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = createBook(resultSet);
                booksList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to find any book in DB", e);
        }
        return booksList;
    }

    @Override
    public Book update(Book book) {
        int k = 0;
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(++k, book.getTitle());
            statement.setObject(++k, book.getPrice());
            statement.setLong(++k, book.getId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow != 0) {
                return book;
            } else {
                throw new DataProcessingException("Expected updated the book"
                        + book + " But it's not happened");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean isDeleted = false;
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int deletedRow = statement.executeUpdate();
            if (deletedRow != 0) {
                isDeleted = true;
            } else {
                throw new DataProcessingException("Expected delete the book by id = "
                        + id + " But it's not happened");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to delete the book by id = " + id, e);
        }
        return isDeleted;
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getObject("title", String.class);
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
