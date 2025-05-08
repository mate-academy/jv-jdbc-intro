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
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final int TITLE_INDEX = 1;
    private static final int ID_INDEX = 1;

    private static final int PRICE_INDEX = 2;
    private static final int MIN_AFFECTED_ROWS = 1;

    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            validateAffectedRows(affectedRows);

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create this book: " + book, e);
        }
        return book;
    }

    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.ofNullable(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
    }

    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> listOfBooks = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listOfBooks.add(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
        return listOfBooks;
    }

    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.setLong(3, book.getId());

            validateAffectedRows(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book data: " + book, e);
        }
        return book;
    }

    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private static void validateAffectedRows(int affectedRows) {
        if (affectedRows < MIN_AFFECTED_ROWS) {
            throw new RuntimeException(
                    "Expected that at least 1 row will be changed, now: " + affectedRows);
        }
    }

    private static Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_INDEX, Long.class);
        String title = resultSet.getString(TITLE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);

        return new Book(id, title, price);
    }
}
