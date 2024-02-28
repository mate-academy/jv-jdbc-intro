package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BooksDaoImpl implements BooksDao {

    private static final String BOOK_ID = "id";
    private static final String BOOK_TITLE = "title";
    private static final String BOOK_PRICE = "price";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(id,title,price) VALUES(?,?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setBigDecimal(3, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, "
                        + "but was inserted " + affectedRows + " rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant add new bool: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE books.id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book();
                    book.setId(resultSet.getLong(BOOK_ID));
                    book.setTitle(resultSet.getString(BOOK_TITLE));
                    book.setPrice(resultSet.getBigDecimal(BOOK_PRICE));

                    return Optional.of(book);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant find book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(BOOK_ID));
                book.setTitle(resultSet.getString(BOOK_TITLE));
                book.setPrice(resultSet.getBigDecimal(BOOK_PRICE));

                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant find books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books "
                + "SET books.title = ?, "
                + "books.price = ? "
                + "WHERE books.id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException("Fail to update book. "
                        + "Count of updated rows is 0");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update books with id: "
                    + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                           Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Cant delete book by id: " + id, e);
        }
    }
}
