package mate.academy.dao;

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
import mate.academy.util.UtilConnection;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String ID_LABEL = "id";
    private static final String TITLE_LABEL = "title";
    private static final String PRICE_LABEL = "price";

    @Override
    public Book create(Book book) {
        try (PreparedStatement preparedStatement = UtilConnection.getConnection()
                .prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            checkInsertedRows(preparedStatement.executeUpdate());
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book  " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement preparedStatement = UtilConnection.getConnection()
                .prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createBook(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (PreparedStatement preparedStatement = UtilConnection.getConnection()
                .prepareStatement(FIND_ALL_QUERY)) {
            List<Book> books = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(createBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement preparedStatement = UtilConnection.getConnection()
                .prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int insertedRows = preparedStatement.executeUpdate();
            checkInsertedRows(insertedRows);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement preparedStatement = UtilConnection.getConnection()
                .prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);

            int deletedRows = preparedStatement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private void checkInsertedRows(int insertedRows) {
        if (insertedRows < 1) {
            throw new RuntimeException("Expected at least one row to insert");
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong(ID_LABEL),
                resultSet.getString(TITLE_LABEL),
                resultSet.getBigDecimal(PRICE_LABEL));
    }
}
