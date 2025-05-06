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
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice().doubleValue());
            int addedRows = statement.executeUpdate();
            if (addedRows != 1) {
                throw new DataProcessingException("Expected to insert one row, but inserted "
                        + addedRows + " rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to insert data to DB: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Book result = null;
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = bookByResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get data from DB with index " + id, e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List<Book> result = new ArrayList<>();
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(bookByResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get all data from DB", e);
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice().doubleValue());
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Expected to update one row, but updated "
                        + updatedRows + " rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update data in DB: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete data from DB, id: " + id, e);
        }
    }

    private Book bookByResultSet(ResultSet resultSet) {
        try {
            return new Book(resultSet.getObject(1, Long.class),
                    resultSet.getObject(2, String.class),
                    BigDecimal.valueOf(resultSet.getObject(3, Long.class)));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create book object from result set: "
                    + resultSet, e);
        }
    }
}
