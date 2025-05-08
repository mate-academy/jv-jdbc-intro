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
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(title, price) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                         connection.prepareStatement(query,
                             Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query =
                "SELECT * FROM books WHERE id = ? AND is_deleted = FALSE;";
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = getBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by id " + id, e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement
                    .executeQuery();
            while (resultSet.next()) {
                books.add(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query =
                "UPDATE books "
                        + "SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        throw new RuntimeException("Can't update deleted book " + book);
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getObject("title", String.class);
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
