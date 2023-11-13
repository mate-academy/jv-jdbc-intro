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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.models.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImp implements BookDao {
    private static final String INSERT_BY_TITLE_AND_PRICE =
            "INSERT INTO books (title, price) VALUES(?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM books";
    private static final String UPDATE_TITLE_AND_PRICE_BY_ID =
            "UPDATE books SET price = ?, title = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(INSERT_BY_TITLE_AND_PRICE,
                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            checkExistingChanges(affectedRows);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException | RuntimeException e) {
            throw new DataProcessingException("Can't add new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return list;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(UPDATE_TITLE_AND_PRICE_BY_ID)) {
            preparedStatement.setBigDecimal(1, book.getPrice());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            checkExistingChanges(affectedRows);
        } catch (SQLException | RuntimeException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id" + id, e);
        }
    }

    private void checkExistingChanges(int affectedRows) {
        if (affectedRows < 1) {
            throw new RuntimeException("No row has changed.");
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        long id = resultSet.getObject("id", long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        return new Book(id, title, price);
    }
}
