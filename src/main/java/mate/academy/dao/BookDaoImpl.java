package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Can`t add a new book " + book.getTitle());
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connect to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                return Optional.of(book);
            }
            throw new DataProcessingException("Can`t find book by id " + id);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connect to DB", e);
        }

    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(getBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get books from the DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setObject(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Updating book by id "
                        + book.getId() + " failed.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connect to DB", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connect to DB", e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString(TITLE));
        book.setPrice(resultSet.getBigDecimal(PRICE));
        book.setId(resultSet.getObject(ID, Long.class));
        return book;
    }

}
