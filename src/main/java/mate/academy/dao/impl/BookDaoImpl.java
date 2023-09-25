package mate.academy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sqlInsert = "INSERT INTO books(title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.connect();
                 PreparedStatement statement = connection.prepareStatement(sqlInsert,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }
            return new Book(book.getId(), book.getTitle(), book.getPrice());
        } catch (SQLException e) {
            throw new DataProcessingException("Failed insert book: " + book.getTitle(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlFindById = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement = connection.prepareStatement(sqlFindById)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sqlFindAll = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement = connection.prepareStatement(sqlFindAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = mapResultToBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed select all book from db", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlUpdateByName = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement = connection.prepareStatement(sqlUpdateByName)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                return book;
            } else {
                throw new DataProcessingException("Failed to update the"
                        + " book with ID: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Operation updade failed with book title: "
                    + book.getTitle(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlDelete = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.connect();
                 PreparedStatement statement = connection.prepareStatement(sqlDelete)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete book failed: id = " + id, e);
        }
    }

    private static Book mapResultToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
