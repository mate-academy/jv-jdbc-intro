package mate.academy.dao;

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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImp implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            if (preparedStatement.executeUpdate() < 1) {
                throw new SQLException("Failed to create book, zero rows inserted");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long rowId = resultSet.getObject(1, Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find book, book id: "
                    + id + " doesnt exist in DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject(1, Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateSql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            if (preparedStatement.executeUpdate() < 1) {
                throw new RuntimeException("Failed to update book, book id: "
                        + book.getId() + " doesnt exist in DB");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Update book failed", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteSql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete book with id: " + id + " failed", e);
        }
    }
}
