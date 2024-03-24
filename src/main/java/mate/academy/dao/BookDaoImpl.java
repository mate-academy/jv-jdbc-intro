package mate.academy.dao;

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
    private static final String CREATE_SQL_OPERATION =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String GET_SQL_OPERATION =
            "SELECT * FROM books WHERE id = (?)";
    private static final String UPDATE_SQL_OPERATION =
            "UPDATE books SET title = (?), price = (?) WHERE id = (?)";
    private static final String DELETE_SQL_OPERATION = "DELETE FROM books WHERE id = (?)";
    private static final String GET_ALL_SQL_OPERATION = "SELECT * FROM books";

    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new DataProcessingException("Error: This book is null!");
        }
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(CREATE_SQL_OPERATION,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row,"
                        + " but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null) {
            throw new DataProcessingException("Error: This id is null!");
        }
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(GET_SQL_OPERATION)) {
            statement.setLong(1, id);
            ResultSet generatedKeys = statement.executeQuery();
            return generatedKeys.next() ? Optional.of(new Book(id,
                    generatedKeys.getString(2),
                    generatedKeys.getBigDecimal(3))) : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(GET_ALL_SQL_OPERATION)) {
            ResultSet generatedKeys = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (generatedKeys.next()) {
                books.add(new Book(generatedKeys.getObject(1, Long.class),
                        generatedKeys.getString(2),
                        generatedKeys.getBigDecimal(3)));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all books!", e);
        }
    }

    @Override
    public Book update(Book book) {
        if (book == null) {
            throw new DataProcessingException("Error: This book is null!");
        }
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(UPDATE_SQL_OPERATION)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update one row, but updated 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new DataProcessingException("Error: This id is null!");
        }
        try (Connection connection = ConnectionUtil.connect();
                PreparedStatement statement
                        = connection.prepareStatement(DELETE_SQL_OPERATION)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id: " + id, e);
        }
    }
}
