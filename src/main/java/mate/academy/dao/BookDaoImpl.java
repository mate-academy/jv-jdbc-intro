package mate.academy.dao;

import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private final static int FIRST_PARAMETER = 1;
    private final static int SECOND_PARAMETER = 2;
    private final static int THIRD_PARAMETER = 3;
    private final static int AFFECTED_ROW = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < AFFECTED_ROW) {
                throw new RuntimeException("Expected to insert at least one row, "
                        + "but inserted zero rows ");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book to DB " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_PARAMETER, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = createBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by index " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = createBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("There are no books in DB!", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(FIRST_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            statement.setLong(THIRD_PARAMETER, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < AFFECTED_ROW) {
                throw new RuntimeException("Expected to update at least one row,"
                        + " but updated zero rows ");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update DB with book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        int affectedRows;
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_PARAMETER, id);
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from DB by " + id, e);
        }
        return affectedRows > 0;
    }

    private static Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
