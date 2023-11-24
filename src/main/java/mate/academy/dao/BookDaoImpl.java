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
import mate.academy.model.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.ConnectionUtil;
import mate.academy.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insertQuery = "INSERT INTO books(title, price) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't insert to DB book: "
                    + book, throwable);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getQuery = "SELECT * FROM books WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = retrieveFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get book from DB by id: "
                    + id, throwable);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books WHERE is_deleted = false";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    books.add(retrieveFromResultSet(resultSet));
                }
                return books;
            }
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't get all books from DB", throwable);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            if (statement.executeUpdate() > 0) {
                return book;
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            throw new RuntimeException("Can't find book " + book + " in the DB ");
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't update to DB book: "
                    + book, throwable);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "UPDATE books SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't delete book by id: " + id, throwable);
        }
    }

    private Book retrieveFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        book.setId(resultSet.getObject("id", Long.class));
        return book;
    }
}
