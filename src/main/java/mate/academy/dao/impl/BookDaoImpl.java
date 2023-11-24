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
import mate.academy.dao.ConnectionUtil;
import mate.academy.exeption.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    public BookDaoImpl() {
    }

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at leas one row, but inserted 0 rows");
            }
            ResultSet generatedKey = statement.getGeneratedKeys();

            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                book.setId(id);

            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add new book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = new Book();
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Book with id " + id + " not found", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(new Book(
                        resultSet.getObject("id", Long.class),
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Books not found", e);
        }
        return list;
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
            if (affectedRows < 1) {
                throw new DataProcessingException("Failed to update the book");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int deletedRows = statement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book with id " + id, e);
        }
    }
}
