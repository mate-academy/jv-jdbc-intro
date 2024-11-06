package mate.academy.lib.dao;

import mate.academy.lib.config.ConnectionUtil;
import mate.academy.lib.errorhandle.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_BOOK_SQL = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM books";
    private static final String UPDATE_BOOK_SQL = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_BOOK_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setBigDecimal(2, book.getPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Failed to insert the book into database: " + book);
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getObject(1, Long.class));
                }
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? Optional.of(createBookFromResultSet(resultSet)) : Optional.empty();

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                books.add(createBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to retrieve all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK_SQL)) {

            ps.setString(1, book.getTitle());
            ps.setBigDecimal(2, book.getPrice());
            ps.setLong(3, book.getId());

            if (ps.executeUpdate() < 1) {
                throw new DataProcessingException("Failed to update book: " + book);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_SQL)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete book with id: " + id, e);
        }
    }

    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
