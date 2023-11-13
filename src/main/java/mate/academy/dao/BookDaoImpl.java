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
import mate.academy.util.ConnectionUtil;

public class BookDaoImpl implements BookDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String EXPECTED_MESSAGE =
            "Expected to insert at leas one row, but inserted 0 rows";
    private static final String DB_CONNECTION_ERROR_MESSAGE =
            "Can't create a connection to the DB";
    private static final String UPDATE_ERROR_MESSAGE = "Can't update book by id: ";
    private static final String DELETE_ERROR_MESSAGE = "Can't delete book by id: ";
    private static final String NULL_ID_ERROR_MESSAGE = "Id can't be null";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(EXPECTED_MESSAGE);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            Book book = null;
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getBigDecimal(PRICE);

                book = new Book(id, title, price);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            List<Book> bookList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getObject(ID, Long.class);
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getBigDecimal(PRICE);
                bookList.add(new Book(id, title, price));
            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            if (book.getId() == null) {
                throw new RuntimeException(NULL_ID_ERROR_MESSAGE);
            }
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 0) {
                throw new RuntimeException(UPDATE_ERROR_MESSAGE + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 0) {
                throw new RuntimeException(DELETE_ERROR_MESSAGE + id);
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(DB_CONNECTION_ERROR_MESSAGE, e);
        }
    }
}
