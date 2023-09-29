package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String SQL_CREATE_NEW_ROW
            = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SQL_FIND_ROW_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_UPDATE_ROW
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE_ROW_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String SQL_GET_ALL_ROWS = "SELECT * FROM books";

    @Override
    public Book create(Book book) {
        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection().prepareStatement(SQL_CREATE_NEW_ROW,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row, but was: "
                        + affectedRows);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book" + book.getTitle(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement statement = ConnectionUtil
                .getConnection().prepareStatement(SQL_FIND_ROW_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book bookFromDb = mapToBook(resultSet);
                return Optional.of(bookFromDb);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement statement = ConnectionUtil
                .getConnection().prepareStatement(SQL_GET_ALL_ROWS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book bookFromDb = mapToBook(resultSet);
                bookList.add(bookFromDb);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection().prepareStatement(SQL_UPDATE_ROW)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update at least 1 row, but was: "
                        + affectedRows);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this book value: "
                    + book.getTitle(), e);
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try (PreparedStatement statement = ConnectionUtil
                .getConnection().prepareStatement(SQL_DELETE_ROW_BY_ID)) {
            statement.setLong(1, id);
            int resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with this id:" + id, e);
        }
    }

    private Book mapToBook(ResultSet resultSet) {
        try {
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            Long id = resultSet.getLong("id");
            Book book = new Book();
            book.setTitle(title);
            book.setPrice(price);
            book.setId(id);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book from DB");
        }
    }
}
