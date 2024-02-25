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
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException(
                        "After insertion in database of book = " + book.toString() + " "
                                + "expecting affectedRows > 0, but = " + affectedRows);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't insert in database book = " + book.toString(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't find in database 'books' record with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        Book book;
        String query = "SELECT * FROM books;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all records in database 'books'", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            Long id = book.getId();
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException(
                        "After updation in database of book = " + book.toString() + " "
                                + "expecting affectedRows > 0, but = " + affectedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update in database of book = " + book.toString(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return (affectedRows > 0);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't delete in database of book with id = " + id, e);
        }
    }

    private static Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long idFromDb = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book(idFromDb, title, price);
        return book;
    }
}
