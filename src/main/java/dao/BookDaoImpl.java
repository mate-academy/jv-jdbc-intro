package dao;

import exception.DataProcessingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = getPrepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return Optional.of(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (ResultSet resultSet = getResultSet(query)) {
            while (resultSet != null && resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                books.add(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get List of books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = getPrepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException();
            }
        } catch (RuntimeException | SQLException e) {
            throw new DataProcessingException("Can't update a book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(long id) {
        int updatedRows = 0;
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = getPrepareStatement(query)) {
            statement.setLong(1, id);
            updatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book by id " + id, e);
        }
        return updatedRows > 0;
    }

    private ResultSet getResultSet(String query) throws SQLException {
        return getPrepareStatement(query).executeQuery();
    }

    private PreparedStatement getPrepareStatement(String query) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        return connection.prepareStatement(query);
    }
}
