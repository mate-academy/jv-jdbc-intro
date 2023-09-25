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
        try {
            String sqlQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DataProcessingException("Creating book failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    book.setId(id);
                    return book;
                } else {
                    throw new DataProcessingException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error creating book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try {
            String sqlQuery = "SELECT * FROM books WHERE id = ?";
            PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sqlQuery);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = parseFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by id " + id, e);
        }

        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = parseFromResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try {
            String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return book;
            } else {
                throw new DataProcessingException("Updating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book" + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            String sqlQuery = "DELETE FROM books WHERE id = ?";
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book by id " + id, e);
        }
    }

    private Book parseFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getObject("title", String.class);
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);

        return book;
    }
}
