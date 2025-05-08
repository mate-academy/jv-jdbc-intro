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
        String insertRequest = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createObjectStatement
                        = connection.prepareStatement(insertRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createObjectStatement.setString(1, book.getTitle());
            createObjectStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = createObjectStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, "
                        + "but inserted 0 rows");
            }

            ResultSet generatedKeys = createObjectStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert book "
                    + book + " to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getRequest = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getBookStatement
                        = connection.prepareStatement(getRequest)) {
            getBookStatement.setLong(1, id);
            ResultSet resultSet = getBookStatement.executeQuery();

            if (resultSet.next()) {
                Book book = createBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get book by id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String getAllRequest = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllBooksStatement
                        = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllBooksStatement.executeQuery();

            while (resultSet.next()) {
                Book book = createBook(resultSet);
                books.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateBookStatement
                        = connection.prepareStatement(updateRequest)) {
            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setBigDecimal(2, book.getPrice());
            updateBookStatement.setString(3, String.valueOf(book.getId()));
            updateBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book "
                    + book + " from DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRequest = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletedStatement
                        = connection.prepareStatement(deleteRequest)) {
            deletedStatement.setLong(1, id);
            int rowsAffected = deletedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book by id "
                    + id + " from DB", e);
        }
    }

    private static Book createBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
