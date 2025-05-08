package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String createQuery = "INSERT INTO books (title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection
                        .prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet resultSet = createStatement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book in DB " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> getById(Long id) {
        String getQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getQuery)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = resultSetToBooks(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String getAllQuery = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                books.add(resultSetToBooks(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Books in DB ", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            updateStatement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "DELETE FROM books WHERE id = ?";
        int rows = 0;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            rows = deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't deleted book" + id, e);
        }
        return rows > 0;
    }

    private Book resultSetToBooks(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}
