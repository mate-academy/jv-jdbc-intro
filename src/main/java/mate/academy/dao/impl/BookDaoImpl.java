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
        String insertRequest = "INSERT INTO books (title,price)"
                + " VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createBookStatement =
                        connection.prepareStatement(insertRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create insert format to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdRequest = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement findByIdStatement =
                        connection.prepareStatement(findByIdRequest)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String findAllRequest = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement findAllStatement = connection.prepareStatement(findAllRequest)) {
            ResultSet resultSet = findAllStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error finding all books", e);
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
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book with id: "
                    + book.getId(), e);
        }
        throw new DataProcessingException("Book with id: "
                + book.getId() + " not found for update");
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "UPDATE books SET is_deleted = 1 WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement softDelete = connection.prepareStatement(deleteQuery);
            softDelete.setLong(1, id);
            int numberOfDeletedRows = softDelete.executeUpdate();
            return numberOfDeletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Cant delete book by id = " + id, e);
        }
    }
}
