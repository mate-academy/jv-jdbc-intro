package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.entity.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_SQL = "INSERT INTO book (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM book";
    private static final String UPDATE_BY_ID_SQL = "UPDATE book SET title = ?, "
            + "price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM book WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                throw new DataProcessingException("At least one row was expected to be affected, "
                        + "but 0 rows were affected");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("An error occurred while creating the books", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(FIND_BY_ID_SQL)) {
            statement.setObject(1, id, Types.BIGINT);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = mapResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not find books with id: " + id, e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Book> books = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = mapResultSet(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setObject(1, book.getTitle(), Types.VARCHAR);
            statement.setObject(2, book.getPrice(), Types.DECIMAL);
            statement.setObject(3, book.getId(), Types.BIGINT);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                throw new DataProcessingException("At least one row was expected to be affected, "
                        + "but 0 rows were affected");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("An error occurred while updating the books", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setObject(1, id, Types.BIGINT);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("An error occurred while deleting the books", e);
        }
    }

    private Book mapResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
