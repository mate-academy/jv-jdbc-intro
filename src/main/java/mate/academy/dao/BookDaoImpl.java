package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.models.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao{
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(tittle, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createBookStatement = connection
                     .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert book "
                    + book + " to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllBookStatement = connection
                     .prepareStatement(query)) {
            ResultSet resultSet = getAllBookStatement.executeQuery();
            while (resultSet.next()) {
                Long identifier = resultSet.getObject("id", Long.class);
                if (Objects.equals(id, identifier)) {
                    Book book = getBook(resultSet);
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get book with id = "
                    + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> fingAll() {
        String query = "SELECT * FROM books WHERE is_deleted = FALSE";
        List<Book> allBook = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllBookStatement = connection
                     .prepareStatement(query)) {
            ResultSet resultSet = getAllBookStatement.executeQuery();
            while (resultSet.next()) {
                Book book = getBook(resultSet);
                allBook.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return allBook;    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET tittle = ?, price = ?  "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createBookStatement = connection
                     .prepareStatement(query)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            createBookStatement.setLong(3, book.getId());
            createBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book "
                    + book + " to DB", e);
        }
        return book;    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE books SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteBookStatement = connection
                     .prepareStatement(query)) {
            deleteBookStatement.setLong(1, id);
            int updateRows = deleteBookStatement.executeUpdate();
            return updateRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book with id "
                    + id + " from DB", e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        String tittle = resultSet.getString("tittle");
        long price = resultSet.getLong("price");
        Long id = resultSet.getObject("id", Long.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(tittle);
        book.setPrice(BigDecimal.valueOf(price));
        return book;
    }
}
