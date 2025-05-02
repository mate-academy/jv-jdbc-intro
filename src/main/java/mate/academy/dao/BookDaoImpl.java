package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";
    private static final String INSERT_BOOK_SQL = "INSERT INTO book (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM book";
    private static final String UPDATE_BOOK_SQL =
            "UPDATE book SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_SQL = "DELETE FROM book WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_SQL,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Can't add a new book " + book);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(FIND_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new DataProcessingException("No book with id: " + id);
            }
            return Optional.of(fetchBook(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(FIND_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookList.add(fetchBook(resultSet));
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_BOOK_SQL);
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());

            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("No book for update in database: " + book);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_BOOK_SQL);
            statement.setObject(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private Book fetchBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString(TITLE_COLUMN));
        book.setPrice(resultSet.getObject(PRICE_COLUMN, BigDecimal.class));
        book.setId(resultSet.getObject(1, Long.class));
        return book;
    }
}
