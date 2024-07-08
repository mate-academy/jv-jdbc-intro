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
import mate.academy.models.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUE (?, ?)";
        try (PreparedStatement prepareStatement = ConnectionUtilImpl.getConnection()
                     .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = prepareStatement.executeUpdate();
            if (affectedRows > 1) {
                book.setId(getIdFromResultSet(prepareStatement.getGeneratedKeys()));
                return book;
            }
            throw new DataProcessingException("Can't save the new book: " + book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement prepareStatement = ConnectionUtilImpl.getConnection()
                .prepareStatement(sql)) {

            prepareStatement.setLong(1, id);

            return getBookFromResultSet(prepareStatement.executeQuery());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (PreparedStatement prepareStatement = ConnectionUtilImpl.getConnection()
                .prepareStatement(sql)) {

            return getAllFromResultSet(prepareStatement.executeQuery());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get any books from the database", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ?) WHERE id = ?";
        try (PreparedStatement prepareStatement = ConnectionUtilImpl.getConnection()
                .prepareStatement(sql)) {

            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setBigDecimal(2, book.getPrice());
            prepareStatement.setLong(3, book.getId());

            int updatedRows = prepareStatement.executeUpdate();
            if (updatedRows > 1) {
                return book;
            }
            throw new DataProcessingException("Can't update the book: " + book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement prepareStatement = ConnectionUtilImpl.getConnection()
                .prepareStatement(sql)) {

            prepareStatement.setLong(1, id);

            int updatedRows = prepareStatement.executeUpdate();
            return updatedRows > 1;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book by id: " + id, e);
        }
    }

    private Long getIdFromResultSet(ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            return generatedKeys.getObject(1, Long.class);
        }
        throw new DataProcessingException("Can't writing the book to the database, missing ID");
    }

    private Optional<Book> getBookFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);

            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return Optional.of(book);
        }
        return Optional.empty();
    }

    private List<Book> getAllFromResultSet(ResultSet resultSet) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        do {
            getBookFromResultSet(resultSet).ifPresent(bookList::add);
        } while (resultSet.next());
        return bookList;
    }
}
