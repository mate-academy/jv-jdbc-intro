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
import mate.academy.exeption.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int executed = statement.executeUpdate();
            if (executed < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not add a new book: ", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> result = Optional.empty();
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(handleResult(resultSet, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant not find book by ID:  ", e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                result.add(handleResult(resultSet, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not table books", e);
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int executed = statement.executeUpdate();
            if (executed < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row: ");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book by id", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id", e);
        }
    }

    private Book handleResult(ResultSet res, Long id) {
        try {
            String title = res.getString("title");
            BigDecimal price = res.getBigDecimal("price");
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get result from ResultSet: ", e);
        }
    }
}
