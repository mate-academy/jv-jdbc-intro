package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.db.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.model.Book;

@Dao
public class SimpleBookDao implements BookDao {
    private static final String SELECT_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM books";
    private static final String INSERT_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String UPDATE_QUERY
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(INSERT_QUERY,
                        Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Failed to insert into `books` values: "
                            + book);
            }

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to insert into `books` values: "
                    + book, e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(SELECT_QUERY);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")
                        );
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to fetch data from `books` for id = "
                    + id, e);
        }

        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(SELECT_ALL_QUERY);) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                books.add(
                        new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")
                        ));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to fetch data from `books`", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(UPDATE_QUERY);) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Failed to update `books` with values: "
                        + book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update `books` with values: "
                    + book, e);
        }

        return findById(book.getId()).get();
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(DELETE_QUERY);) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete from `books` for id = "
                    + id, e);
        }
    }
}
