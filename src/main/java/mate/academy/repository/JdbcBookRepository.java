package mate.academy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.models.Book;

@Dao
public class JdbcBookRepository implements BookRepository {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";

    @Override
    public Book save(Book book) {
        String sqlInsert = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
                    PreparedStatement statement = conn.prepareStatement(sqlInsert,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            checkIfUpdateSuccessful(statement.executeUpdate());
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            } else {
                throw new RuntimeException("Creating book failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Saving failed ", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlSelectById = "SELECT id, title, price FROM books WHERE id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement statement = conn.prepareStatement(sqlSelectById)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(getBookFromResultSet(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sqlSelectAll = "SELECT id, title, price FROM books";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement statement = conn.prepareStatement(sqlSelectAll);
                ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                books.add(getBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all", e);
        }
        return books;
    }

    @Override
    public Book updateById(Book book) {
        String sqlUpdateById = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlUpdateById,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            checkIfUpdateSuccessful(statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException("Could not update book by id " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlDeleteById = "DELETE FROM books WHERE id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement statement = conn.prepareStatement(sqlDeleteById)) {
            statement.setLong(1, id);
            return checkIfUpdateSuccessful(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting by id " + id + " failed ", e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(COLUMN_ID, Long.class));
        book.setTitle(resultSet.getString(COLUMN_TITLE));
        book.setPrice(resultSet.getBigDecimal(COLUMN_PRICE));
        return book;
    }

    private boolean checkIfUpdateSuccessful(int affectedRows) {
        if (affectedRows <= 0) {
            throw new RuntimeException("No affected rows");
        }
        return true;
    }
}
