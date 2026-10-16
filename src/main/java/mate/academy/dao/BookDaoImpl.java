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
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        query,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            ResultSet cursor = statement.executeQuery();

            if (cursor.next()) {
                return Optional.of(mapToBook(cursor));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            ResultSet cursor = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (cursor.next()) {
                books.add(mapToBook(cursor));
            }

            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int rowCount = statement.executeUpdate();

            if (rowCount < 1) {
                throw new DataProcessingException(
                    "There is no such book with given id: " + book,
                    null
                );
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book: " + id, e);
        }
    }

    private Book mapToBook(ResultSet cursor) throws SQLException {
        Long id = cursor.getObject("id", Long.class);
        BigDecimal price = cursor.getBigDecimal("price");
        String title = cursor.getString("title");

        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setTitle(title);

        return book;
    }
}
