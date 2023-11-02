package mate.academy.dao;

import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.utils.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String SQL_CREATE = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM books;";
    private static final String SQL_UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, actual: " + affectedRows);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add book " + book.getTitle() + "to the DB", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id " + id, e);
        }

        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet allBooks = statement.executeQuery();

            while (allBooks.next()) {
                Book book = new Book();
                book.setId(allBooks.getLong("id"));
                book.setTitle(allBooks.getString("title"));
                book.setPrice(allBooks.getBigDecimal("price"));

                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("DB is empty", e);
        }
    }

    @Override
    public Book update(Book book) {
        Long id = book.getId();
        String title = book.getTitle();
        BigDecimal price = book.getPrice();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            Optional<Book> oldBook = findById(id);

            statement.setString(1, title);
            statement.setBigDecimal(2, price);
            statement.setLong(3, id);

            statement.executeUpdate();

            return oldBook.orElseThrow(()
                    -> new RuntimeException("Book is not exist"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + title, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book with id: " + id, e);
        }
    }
}
