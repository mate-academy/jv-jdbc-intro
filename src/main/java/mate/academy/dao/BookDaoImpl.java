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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.DaoImpl;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@DaoImpl
public class BookDaoImpl implements Dao<Book> {
    private static final String CREATE_SQL = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM books";
    private static final String UPDATE_SQL = "UPDATE books SET title = ?,  price = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(CREATE_SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least one row, " + "but inserted 0 rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return Optional.of(new Book(id, title, price));
            } else {
                throw new DataProcessingException("Can't find a book by id " + id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish connection with the DB", e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Book> booksList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                booksList.add(new Book(id, title, price));
            }
            if (booksList.isEmpty()) {
                throw new DataProcessingException(
                        "The DB table \"books\" is empty. Add some elements first...");
            } else {
                return booksList;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish connection with the DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return new Book(id, title, price);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book with id " + book.getId(), e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        int updatedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            updatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book with id " + id, e);
        }
        return updatedRows > 0;
    }
}
