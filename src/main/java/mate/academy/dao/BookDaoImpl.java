package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_SQL = "INSERT INTO books (title, price) VALUES(?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM books";
    private static final String UPDATE_BY_ID_SQL = "UPDATE books SET title = ?, "
            + "price = ? WHERE id = ?";
    private static final String DELETE_BY_INDEX_SQL = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at list one row"
                        + "but were inserted zero rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setObject(1, id, Types.BIGINT);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = collectBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find book with id = " + id, e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(collectBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find all books from DB " + e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setObject(1, book.getTitle(), Types.VARCHAR);
            statement.setObject(2, book.getPrice(), Types.DECIMAL);
            statement.setObject(3, book.getId(), Types.BIGINT);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("At least one parameter must be update,"
                        + "but anyone was`nt updated");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update data in DB " + e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_INDEX_SQL)) {
            statement.setObject(1, id, Types.BIGINT);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book with id = " + id, e);
        }
    }

    private Book collectBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getBigDecimal("price"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t collect data from DB");
        }
        return book;
    }
}
