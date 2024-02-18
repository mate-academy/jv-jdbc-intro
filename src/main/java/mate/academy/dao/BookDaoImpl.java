package mate.academy.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.utils.ConnectorUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sqlRequest = "INSERT INTO books(title, price) VALUES (?, ?)";
        try (PreparedStatement statement = ConnectorUtil.getConnection().prepareStatement(
                sqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected at least 1 affected row, but was 0");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a book: " + book.toString());
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlRequest = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = ConnectorUtil
                .getConnection().prepareStatement(sqlRequest)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(id);
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not connect to DB");
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sqlRequest = "SELECT * FROM books";
        try (PreparedStatement statement = ConnectorUtil
                .getConnection().prepareStatement(sqlRequest)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not connect to DB");
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlRequest = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = ConnectorUtil
                .getConnection().prepareStatement(sqlRequest)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Failed tu update book " + book.toString());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to the DB");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlRequest = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = ConnectorUtil
                .getConnection().prepareStatement(sqlRequest)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id: " + id);
        }
    }
}
