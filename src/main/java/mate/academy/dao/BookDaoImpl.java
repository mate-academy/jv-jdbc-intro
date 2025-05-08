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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int TITLE_COLUM_NUMBER = 1;
    private static final int PRICE_COLUM_NUMBER = 2;
    private static final int ID_COLUM_NUMBER = 3;

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String selectQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        selectQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_COLUM_NUMBER, book.getTitle());
            statement.setBigDecimal(PRICE_COLUM_NUMBER, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 0) {
                throw new DataProcessingException("Failed to insert book into database");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = parceBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                Book book = parceBook(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String selectQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        selectQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_COLUM_NUMBER, book.getTitle());
            statement.setBigDecimal(PRICE_COLUM_NUMBER, book.getPrice());
            statement.setLong(ID_COLUM_NUMBER, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 0) {
                throw new DataProcessingException("Failed to update book in the database");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String selectQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book", e);
        }
    }

    private Book parceBook(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getObject(ID, Long.class);
        String title = resultSet.getString(TITLE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        return new Book(bookId, title, price);
    }
}
