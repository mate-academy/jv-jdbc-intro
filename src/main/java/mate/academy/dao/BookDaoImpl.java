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
import mate.academy.model.DataProcessingException;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT = "INSERT INTO books(title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM books";
    private static final String UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Book wasn't added. 0 rows were affected");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while adding book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred wile finding by id", e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> res = new ArrayList<>();
            while (resultSet.next()) {
                res.add(getBookFromResultSet(resultSet));
            }
            return res;
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while getting all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Book wasn't changed. 0 rows were affected");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while updating book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while deleting book", e);
        }
    }

    private static Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(bookId, title, price);
    }
}
