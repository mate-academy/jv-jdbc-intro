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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.services.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY = "INSERT INTO books (title , price) VALUES (?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String ERROR = "Can not complete ";
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int ID_INDEX_UPDATE = 3;
    private static final int ID_INDEX_COMMON = 1;
    private static final int MINIMAL_ROWS_TO_CHANGE = 1;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection
                        .prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MINIMAL_ROWS_TO_CHANGE) {
                throw new DataProcessingException("Failed to create book "
                        + book + " no rows were affected");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX_COMMON, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " creation - " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> response = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                response.add(getBook(resultSet));
            }
            return response;
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " finding all books", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(ID_INDEX_COMMON, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " finding by id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.setLong(ID_INDEX_UPDATE, book.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated < MINIMAL_ROWS_TO_CHANGE) {
                throw new DataProcessingException("Failed to update book, no rows were affected");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " updating - " + book, e);
        }
    }

    @Override
    public boolean delete(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(ID_INDEX_COMMON, book.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ERROR + " deleting - " + book, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID, Long.class);
        String title = resultSet.getString(TITLE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        return new Book(id, title, price);
    }
}
