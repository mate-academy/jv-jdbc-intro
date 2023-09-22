package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title,price) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                         query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Creating book failed, no rows affected.");
            }
            book.setId(getCreatedId(statement));
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            ResultSet generatedKeys = statement.executeQuery();
            if (generatedKeys.next()) {
                book = mapToBook(generatedKeys);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to the DB");
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setLong(3, book.getId());

            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException(
                        String.format("Book with id=%d wasn't updated, no rows affected.",
                                book.getId()));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        int updatedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id " + id);
        }
    }

    private static long getCreatedId(PreparedStatement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new DataProcessingException("Creating book failed, no ID obtained.");
        }
        return generatedKeys.getLong(1);
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }
}
