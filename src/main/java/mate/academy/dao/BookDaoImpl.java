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
        String insertBookRequest =
                "INSERT INTO books (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createBookStatement =
                        connection.prepareStatement(insertBookRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book to DB"
                    + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> get(Long id) {
        String getSingleBookRequest =
                "SELECT * FROM books WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getIdBookStatement = connection
                        .prepareStatement(getSingleBookRequest)) {
            getIdBookStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = getIdBookStatement.executeQuery();
            if (resultSet.next()) {
                Book book = createBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book from DB with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        List<Book> booksList = new ArrayList<>();
        String getAllRequest = "SELECT * FROM books WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllBooksStatement = connection
                        .prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllBooksStatement.executeQuery();
            while (resultSet.next()) {
                Book book = createBook(resultSet);
                booksList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
        return booksList;
    }

    @Override
    public Book update(Book book) {
        String updateBookRequest =
                "UPDATE books SET title = ?, price = ?"
                        + " WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateBookStatement = connection
                        .prepareStatement(updateBookRequest)) {
            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setBigDecimal(2, book.getPrice());
            updateBookStatement.setLong(3, book.getId());
            updateBookStatement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book from DB with id: "
                    + book.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteBookRequest =
                "UPDATE books SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteBookStatement = connection
                        .prepareStatement(deleteBookRequest)) {
            deleteBookStatement.setString(1, String.valueOf(id));
            return deleteBookStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from DB with id: "
                    + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book from DB", e);
        }
    }
}
