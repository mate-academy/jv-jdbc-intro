package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM books";
    private static final String FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final int PARAMETER_OR_COLUMN_ONE = 1;
    private static final int PARAMETER_OR_COLUMN_TWO = 2;
    private static final int PARAMETER_OR_COLUMN_THREE = 3;

    @Override
    public Book create(Book book) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(PARAMETER_OR_COLUMN_ONE, book.getTitle());
            statement.setBigDecimal(PARAMETER_OR_COLUMN_TWO, book.getPrice());
            int executed = statement.executeUpdate();
            if (executed < PARAMETER_OR_COLUMN_ONE) {
                throw new RuntimeException(
                        "Expected to insert at leas one row, but inserted 0 rows.");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(PARAMETER_OR_COLUMN_ONE, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t add new book : " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(PARAMETER_OR_COLUMN_ONE, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new LinkedList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't find all books : ", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(PARAMETER_OR_COLUMN_ONE, book.getTitle());
            statement.setBigDecimal(PARAMETER_OR_COLUMN_TWO, book.getPrice());
            statement.setLong(PARAMETER_OR_COLUMN_THREE, book.getId());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate < PARAMETER_OR_COLUMN_ONE) {
                throw new RuntimeException(
                        "Expected to update at least one row, but updated 0 rows");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(PARAMETER_OR_COLUMN_ONE, id);
            int executed = preparedStatement.executeUpdate();
            if (executed < PARAMETER_OR_COLUMN_ONE) {
                throw new RuntimeException(
                        "Expected to delete at least one row, but deleted 0 rows.");
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book: " + id, e);
        }
    }
}
